package pl.ghostbuster.linkconfirmator.conference.details

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.model
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
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
        mockMvc.perform(get("/conference?id=${conference.id}"))
                .andExpect(status().isOk)
                .andExpect(model().attribute("conferenceDetails",
                        ConferenceDetails(participants = listOf(
                                ParticipantDetails(email = "test@email", confirmed = false)))))
    }
}

