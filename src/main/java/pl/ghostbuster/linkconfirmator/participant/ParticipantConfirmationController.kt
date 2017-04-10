package pl.ghostbuster.linkconfirmator.participant

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.ghostbuster.linkconfirmator.ResourceNotFound
import pl.ghostbuster.linkconfirmator.parseUuid

@Controller
class ParticipantConfirmationController(private val participantRepository: ParticipantRepository) {

    @GetMapping("/confirm")
    fun confereneceForm(@RequestParam(name = "id", required = true) participantId: String): String {
        if (!participantRepository.exists(parseUuid(participantId))) {
            throw ResourceNotFound()
        }
        return "participant_confirmation"
    }
}

