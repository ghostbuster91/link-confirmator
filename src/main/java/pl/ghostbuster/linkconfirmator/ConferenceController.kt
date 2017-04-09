package pl.ghostbuster.linkconfirmator

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ConferenceController(private val conferenceRepository: ConferenceRepository) {

    @GetMapping("/conference")
    fun conference(): String {
        return "conference_list"
    }
}