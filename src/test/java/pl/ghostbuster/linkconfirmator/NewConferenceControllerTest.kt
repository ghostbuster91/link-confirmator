package pl.ghostbuster.linkconfirmator

import org.assertj.core.api.Assertions.assertThat
import org.jsoup.Jsoup
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(NewConferenceControllerTest.TestConfiguration::class))
class NewConferenceControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    private lateinit var mockMvc: MockMvc
    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun shouldReturnOkStatusOnHttpGet() {
        mockMvc.perform(get("/conference"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("new_conference"))
                .andDo {
                    val actual = Jsoup.parse(it.response.contentAsString).toString()
                    val expected = Jsoup.parse(stringFromFile("new_conference_rendered.html")).toString()
                    assertThat(actual).isEqualTo(expected)
                }
    }

    @Test
    fun shouldSaveConferenceWithParticipantsToRepository() {
        mockMvc.perform(post("/conference")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("participantsEmails", "test@test.github.pl")
        )
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andDo {
                    val conferences = conferenceRepository.findAll()
                    assertThat(conferences).hasSize(1)
                    val participants = conferences.first().participants
                    assertThat(participants).hasSize(1)
                    assertThat(participants.first().email).isEqualTo("test@test.github.pl")
                }
    }

    @EnableAutoConfiguration
    @ComponentScan
    @Configuration
    open class TestConfiguration
}
