package pl.ghostbuster.linkconfirmator.conference.create

import com.nhaarman.mockito_kotlin.*
import org.junit.Before
import org.junit.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import pl.ghostbuster.linkconfirmator.conference.model.ConferenceEntity
import pl.ghostbuster.linkconfirmator.participant.Participant

class CreateConferenceControllerTest {

    private val conferenceRepository: ConferenceRepository = mock()
    private val controller = CreateConferenceController(conferenceRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Before
    fun setUp() {
        whenever(conferenceRepository.save(any<ConferenceEntity>())).thenReturn(ConferenceEntity())
    }

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
                    })
                }
    }

    @Test
    fun `should save conference with two participants to repository`() {
        submitConference("first@email.pl, second@email.pl")
                .andDo {
                    verify(conferenceRepository).save(argThat<ConferenceEntity> {
                        participants[0].email == "first@email.pl"
                                && participants[1].email == "second@email.pl"
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
        val participant = Participant(email = "email@test.pl")
        stubRepositoryToReturnOnSave(participant)
        submitConference("email@test.pl")
                .andExpect(model().attribute("linkedParticipants",
                        listOf("email@test.pl" to "http://localhost:8080/confirm?id=${participant.id}")))
    }

    @Test
    fun `should return two participants with their confirmation links after submitting conference`() {
        val first = Participant(email = "email@test.pl")
        val second = Participant(email = "other@test.pl")
        stubRepositoryToReturnOnSave(first, second)
        submitConference("${first.email}, ${second.email}")
                .andExpect(model()
                        .attribute("linkedParticipants",
                                listOf(first.email to "http://localhost:8080/confirm?id=${first.id}",
                                        second.email to "http://localhost:8080/confirm?id=${second.id}")
                        ))
    }

    private fun stubRepositoryToReturnOnSave(vararg participant: Participant) {
        whenever(conferenceRepository.save(any<ConferenceEntity>()))
                .thenReturn(ConferenceEntity(participants = participant.toList()))
    }

    private fun submitConference(emails: String): ResultActions {
        return mockMvc.perform(post("/new_conference")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("participantsEmails", emails)
        )
    }
}
