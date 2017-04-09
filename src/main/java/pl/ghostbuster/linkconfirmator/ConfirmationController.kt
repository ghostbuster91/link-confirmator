package pl.ghostbuster.linkconfirmator

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import java.util.*

@Controller
class ConfirmationController(private val participantRepository: ParticipantRepository) {

    @GetMapping("/confirm")
    fun confereneceForm(@RequestParam(name = "id", required = true) participantId: String): String {
        val participant = participantRepository.findOne(UUID.fromString(participantId))
        if (participant == null) {
            throw ResourceNotFound()
        } else {
            return "participation_confirmed"
        }
    }
}

@ResponseStatus(value = HttpStatus.NOT_FOUND)
class ResourceNotFound : RuntimeException()