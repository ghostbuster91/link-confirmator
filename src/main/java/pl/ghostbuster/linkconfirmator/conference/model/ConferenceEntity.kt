package pl.ghostbuster.linkconfirmator.conference.model

import java.util.*
import javax.persistence.*

private val DEFAULT_UUID by lazy { UUID.randomUUID() }

@Entity
data class ConferenceEntity(
        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.EAGER)
        var participants: List<Participant> = mutableListOf(),
        @Id @GeneratedValue
        var id: UUID = DEFAULT_UUID
)

@Entity
data class Participant(
        var email: String = "",
        var confirmed: Boolean = false,
        @Id @GeneratedValue
        var id: UUID = DEFAULT_UUID
)
