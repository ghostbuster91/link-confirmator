package pl.ghostbuster.linkconfirmator.conference.details

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.ghostbuster.linkconfirmator.DEFAULT_UUID
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import pl.ghostbuster.linkconfirmator.conference.model.ConferenceEntity
import pl.ghostbuster.linkconfirmator.participant.Participant

class ConferenceDetailsControllerTest {

    private val conferenceRepository = mock<ConferenceRepository>()
    private val controller = ConferenceDetailsController(conferenceRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return conference details through model`() {
        val conference = ConferenceEntity(participants = listOf(Participant(email = "test@email", confirmed = false)))
        whenever(conferenceRepository.findOne(any())).thenReturn(conference)
        mockMvc.perform(get("/conferences/${conference.id}"))
                .andExpect(status().isOk)
                .andExpect(view().name("conference_details"))
                .andExpect(model().attribute("conferenceDetails",
                        ConferenceDetails(participants = listOf(
                                ParticipantDetails(email = "test@email", confirmed = false)))))
    }

    @Test
    fun `should return bad request if the id is malformed`() {
        mockMvc.perform(get("/conferences/123"))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun `should return not found if there is no conference for given id`() {
        mockMvc.perform(get("/conferences/$DEFAULT_UUID"))
                .andExpect(status().isNotFound)
    }
}

