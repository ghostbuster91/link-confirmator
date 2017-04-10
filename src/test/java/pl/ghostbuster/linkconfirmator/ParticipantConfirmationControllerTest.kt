package pl.ghostbuster.linkconfirmator

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.ghostbuster.linkconfirmator.participant.ConfirmationController
import pl.ghostbuster.linkconfirmator.participant.Participant
import pl.ghostbuster.linkconfirmator.participant.ParticipantRepository
import java.util.*

class ParticipantConfirmationControllerTest {
    private val participantRepository = mock<ParticipantRepository>()
    private val controller = ConfirmationController(participantRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return status 404 when there is no participant on confirm`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm?id=${UUID.randomUUID()}"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError)
    }

    @Test
    fun `should return status 200 when confirming exiting participant`() {
        whenever(participantRepository.findOne(any())).thenReturn(Participant())
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm?id=${UUID.randomUUID()}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }
}