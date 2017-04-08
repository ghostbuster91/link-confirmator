package pl.ghostbuster.linkconfirmator

import com.nhaarman.mockito_kotlin.argThat
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class NewConferenceControllerTest {

    private val conferenceRepository: ConferenceRepository = mock()
    private val controller = NewConferenceController(conferenceRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return new_conference_submission page on conference endpoint`() {
        mockMvc.perform(get("/new_conference"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("new_conference_submission"))
                .andExpect(model().attribute("conference", ConferenceForm()))
    }

    @Test
    fun `should save conference with one participant to repository`() {
        submitConference("test@test.github.pl")
                .andDo {
                    verify(conferenceRepository).save(argThat<ConferenceEntity> {
                        participants[0].email == "test@test.github.pl"
                                && participants[0].confirmationLink == "http://wp.pl"
                    })
                }
    }

    @Test
    fun `should save conference with two participants to repository`() {
        submitConference("first@email.pl, second@email.pl")
                .andDo {
                    verify(conferenceRepository).save(argThat<ConferenceEntity> {
                        participants[0].email == "first@email.pl"
                                && participants[0].confirmationLink == "http://wp.pl"
                                && participants[1].email == "second@email.pl"
                                && participants[1].confirmationLink == "http://wp.pl"
                    })
                }
    }

    @Test
    fun `should render new_conference_submitted page after submitting conference`() {
        submitConference("email@test.pl")
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("new_conference_submitted"))
    }

    @Test
    fun `should return participant with its confirmation link after submitting conference`() {
        submitConference("email@test.pl")
                .andExpect(model().attribute("linkedParticipants", listOf("email@test.pl" to "http://wp.pl")))
    }

    @Test
    fun `should return two participants with their confirmation links after submitting conference`() {
        submitConference("email@test.pl, other@email.pl")
                .andExpect(model()
                        .attribute("linkedParticipants",
                                listOf("email@test.pl" to "http://wp.pl",
                                        "other@email.pl" to "http://wp.pl")
                        ))
    }

    private fun submitConference(emails: String): ResultActions {
        return mockMvc.perform(post("/new_conference")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("participantsEmails", emails)
        )
    }
}
