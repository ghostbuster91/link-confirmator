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
        return "new_conference_submission"
    }

    @PostMapping("/new_conference")
    fun conferenceSubmit(@ModelAttribute conference: ConferenceForm, model: Model): String {
        val emails = conference.participantsEmails?.split(",")!!
        val saveConference = repository.save(ConferenceEntity(participants = emails.map { Participant(email = it.trim()) }))
        val linkedParticipants = saveConference.participants.map { it.email to createConfirmationLink(it) }
        model.addAttribute("linkedParticipants", linkedParticipants)
        return "new_conference_submitted"
    }

    private fun createConfirmationLink(participant: Participant): String {
        return "http://localhost:8080/confirm?id=" + participant.id
    }
}

