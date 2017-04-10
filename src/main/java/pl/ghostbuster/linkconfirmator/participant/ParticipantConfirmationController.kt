package pl.ghostbuster.linkconfirmator.participant

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.ghostbuster.linkconfirmator.ResourceNotFound
import java.util.*

@Controller
class ParticipantConfirmationController(private val participantRepository: ParticipantRepository) {

    @GetMapping("/confirm")
    fun confereneceForm(@RequestParam(name = "id", required = true) participantId: String): String {
        val participant = participantRepository.findOne(UUID.fromString(participantId))
        if (participant == null) {
            throw ResourceNotFound()
        } else {
            return "participant_confirmation"
        }
    }
}

