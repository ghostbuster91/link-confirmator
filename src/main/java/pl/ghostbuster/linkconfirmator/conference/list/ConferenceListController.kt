package pl.ghostbuster.linkconfirmator.conference.list

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository

@Controller
class ConferenceListController(private val conferenceRepository: ConferenceRepository) {

    @GetMapping("/conference")
    fun conference(model: Model): String {
        val conferences = conferenceRepository.findAll().toList()
        model.addAttribute("conferenceList", conferences.map { ConferenceViewModel(id = it.id.toString()) })
        return "conference_list"
    }
}