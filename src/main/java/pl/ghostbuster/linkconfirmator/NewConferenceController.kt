package pl.ghostbuster.linkconfirmator

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PostMapping

@Controller
class NewConferenceController(val repository: ConferenceRepository) {

    @GetMapping("/new_conference")
    fun confereneceForm(model: Model): String {
        model.addAttribute("conference", ConferenceForm())
        return "new_conference"
    }

    @PostMapping("/new_conference")
    fun conferenceSubmit(@ModelAttribute conference: ConferenceForm, model: Model): String {
        val emails = conference.participantsEmails?.split(",")!!
        val participants = emails.map { Participant(email = it.trim(), confirmationLink = "http://wp.pl") }
        repository.save(ConferenceEntity(participants = participants))
        val linkedParticipants = participants.map { it.email to it.confirmationLink }
        model.addAttribute("linkedParticipants", linkedParticipants)
        return "result"
    }
}

