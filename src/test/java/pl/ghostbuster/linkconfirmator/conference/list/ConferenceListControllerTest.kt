package pl.ghostbuster.linkconfirmator.conference.list

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import org.junit.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import pl.ghostbuster.linkconfirmator.conference.model.ConferenceEntity

class ConferenceListControllerTest {

    private val conferenceRepository: ConferenceRepository = mock()
    private val controller = ConferenceListController(conferenceRepository)
    private val mockMvc = MockMvcBuilders.standaloneSetup(controller).build()

    @Test
    fun `should return conference list view on conference endpoint`() {
        mockMvc.perform(get("/conferences"))
                .andExpect(status().isOk)
                .andExpect(view().name("conference_list"))
    }

    @Test
    fun `should return conference list model on conference endpoint`() {
        val conferenceEntity = ConferenceEntity()
        whenever(conferenceRepository.findAll()).thenReturn(listOf(conferenceEntity))
        mockMvc.perform(get("/conferences"))
                .andExpect(model().attribute("conferenceList", listOf(ConferenceViewModel(conferenceEntity.id.toString()))))
    }
}