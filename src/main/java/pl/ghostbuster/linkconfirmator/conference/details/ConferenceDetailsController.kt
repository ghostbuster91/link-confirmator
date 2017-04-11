package pl.ghostbuster.linkconfirmator.conference.details

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import pl.ghostbuster.linkconfirmator.ResourceNotFound
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import pl.ghostbuster.linkconfirmator.parseUuid

@Controller
class ConferenceDetailsController(private val conferenceRepository: ConferenceRepository) {

    @GetMapping("/conferences/{id}")
    fun getConference(@PathVariable(name = "id") id: String, model: Model): String {
        val conferenceEntity = conferenceRepository.findOne(parseUuid(id)) ?: throw ResourceNotFound()
        val participants = conferenceEntity.participants.map {
            ParticipantDetails(email = it.email, confirmed = it.confirmed)
        }
        val viewModel = ConferenceDetails(participants = participants)
        model.addAttribute("conferenceDetails", viewModel)
        return "conference_details"
    }
}