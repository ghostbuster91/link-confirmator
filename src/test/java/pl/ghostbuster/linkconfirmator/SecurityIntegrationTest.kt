package pl.ghostbuster.linkconfirmator

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import pl.ghostbuster.linkconfirmator.conference.create.ConferenceForm
import pl.ghostbuster.linkconfirmator.conference.model.Participant
import pl.ghostbuster.linkconfirmator.participant.ParticipantRepository

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(SecurityIntegrationTest.TestConfiguration::class))
class SecurityIntegrationTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    @Autowired
    private lateinit var participantRepository: ParticipantRepository
    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply<DefaultMockMvcBuilder>(springSecurity())
                .build()
    }

    @Test
    fun `should not require authentication on confirmation endpoint`() {
        val participant = participantRepository.save(Participant())
        mockMvc.perform(get("/confirm?id=${participant.id}"))
                .andExpect(status().isOk)
    }

    @Test
    fun `should require authentication on new_conference endpoint`() {
        mockMvc.perform(get("/new_conference"))
                .andExpect(status().isFound)
                .andExpect(redirectedUrlPattern("**/login"))
    }

    @Test
    fun `should require authentication on post on new_conference endpoint`() {
        mockMvc.perform(post("/new_conference", ConferenceForm())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("participantsEmails", "email@test.pl"))
                .andExpect(status().isForbidden)
    }

    @Test
    fun `should not require authentication on login endpoint`() {
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk)
    }

    @Test
    fun `should require authentication on conference endpoint`() {
        mockMvc.perform(get("/conference"))
                .andExpect(status().isFound)
                .andExpect(redirectedUrlPattern("**/login"))
    }

    @Configuration
    @ComponentScan(basePackageClasses = arrayOf(Application::class))
    open class TestConfiguration
}