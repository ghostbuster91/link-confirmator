package pl.ghostbuster.linkconfirmator.participant

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

class ParticipantConfirmationControllerTest {
    private val participantRepository = mock<ParticipantRepository>()
    private val controller = ParticipantConfirmationController(participantRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return not found when there is no participant on confirm`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm?id=${UUID.randomUUID()}"))
                .andExpect(MockMvcResultMatchers.status().isNotFound)
    }

    @Test
    fun `should return status 200 when confirming exiting participant`() {
        whenever(participantRepository.exists(any())).thenReturn(true)
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm?id=${UUID.randomUUID()}"))
                .andExpect(MockMvcResultMatchers.status().isOk)
    }

    @Test
    fun `should return bad request if the id is not valid uuid`() {
        mockMvc.perform(MockMvcRequestBuilders.get("/confirm?id=123"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}