package pl.ghostbuster.linkconfirmator

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
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
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
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
    fun `should return new_conference page on conference endpoint`() {
        mockMvc.perform(get("/new_conference"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("new_conference"))
                .andExpect(model().attribute("conference", ConferenceForm()))
    }

    @Test
    fun `should save conference with one participant to repository`() {
        submitConference("test@test.github.pl")
                .andExpectConference {
                    assertThat(participants).hasSize(1)
                    participants.first().run {
                        assertThat(email).isEqualTo("test@test.github.pl")
                        assertThat(confirmationLink).isEqualTo("http://wp.pl")
                    }
                }
    }

    @Test
    fun `should save conference with two participants to repository`() {
        submitConference("first@email.pl, second@email.pl")
                .andExpectConference {
                    assertThat(participants).hasSize(2)
                    participants[0].run {
                        assertThat(email).isEqualTo("first@email.pl")
                        assertThat(confirmationLink).isEqualTo("http://wp.pl")
                    }
                    participants[1].run {
                        assertThat(email).isEqualTo("second@email.pl")
                        assertThat(confirmationLink).isEqualTo("http://wp.pl")
                    }
                }
    }

    @Test
    fun `should render result page after submitting conference`() {
        submitConference("email@test.pl")
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("result"))
    }

    @Test
    fun `should return participant with its confirmation link after submitting conference`() {
        submitConference("email@test.pl")
                .andExpect(model().attribute("linkedParticipants", listOf("email@test.pl" to "http://wp.pl")))
    }

    private fun submitConference(emails: String): ResultActions {
        return mockMvc.perform(post("/new_conference")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("participantsEmails", emails)
        )
    }

    @EnableAutoConfiguration
    @ComponentScan
    @Configuration
    open class TestConfiguration

    @After
    fun tearDown() {
        conferenceRepository.deleteAll()
    }

    private fun ResultActions.andExpectConference(function: ConferenceEntity.() -> Unit) {
        andDo {
            val conferences = conferenceRepository.findAll()
            assertThat(conferences).hasSize(1)
            function(conferences.first())
        }
    }
}
