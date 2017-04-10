package pl.ghostbuster.linkconfirmator.conference.model

import pl.ghostbuster.linkconfirmator.DEFAULT_UUID
import pl.ghostbuster.linkconfirmator.participant.Participant
import java.util.*
import javax.persistence.*

@Entity
data class ConferenceEntity(
        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        var participants: List<Participant> = mutableListOf(),
        @Id @GeneratedValue
        var id: UUID = DEFAULT_UUID
)