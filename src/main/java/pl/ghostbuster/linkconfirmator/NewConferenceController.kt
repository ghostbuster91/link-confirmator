package pl.ghostbuster.linkconfirmator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class NewConferenceController {

    @GetMapping("/conference")
    fun confereneceForm(model: Model): String {
        model.addAttribute("conference", Conference())
        return "new_conference"
    }

    @PostMapping("/conference")
    fun conferenceSubmit(@ModelAttribute conference: Conference): String {
        return "result"
    }
}

