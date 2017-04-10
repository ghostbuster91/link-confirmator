package pl.ghostbuster.linkconfirmator.participant

import pl.ghostbuster.linkconfirmator.DEFAULT_UUID
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class Participant(
        var email: String = "",
        var confirmed: Boolean = false,
        @Id @GeneratedValue
        var id: UUID = DEFAULT_UUID
)