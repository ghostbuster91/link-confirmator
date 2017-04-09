package pl.ghostbuster.linkconfirmator

import com.nhaarman.mockito_kotlin.mock
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class ConferencesControllerTest {

    private val conferenceRepository: ConferenceRepository = mock()
    private val controller = ConferenceController(conferenceRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return conference list on conference endpoint`() {
        mockMvc.perform(get("/conference"))
                .andExpect(status().isOk)
                .andExpect(view().name("conference_list"))
    }
}