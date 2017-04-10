package pl.ghostbuster.linkconfirmator.conference.details

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import java.util.*

@Controller
class ConferenceDetailsController(private val conferenceRepository: ConferenceRepository) {

    @GetMapping("/conference")
    fun getConference(@RequestParam(required = true, name = "id") id: String, model: Model): String {
        val conferenceEntity = conferenceRepository.findOne(UUID.fromString(id))
        val participants = conferenceEntity.participants.map {
            ParticipantDetails(email = it.email, confirmed = it.confirmed)
        }
        val viewModel = ConferenceDetails(participants = participants)
        model.addAttribute("conferenceDetails", viewModel)
        return "conferenceDetails"
    }
}