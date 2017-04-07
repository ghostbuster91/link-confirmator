package pl.ghostbuster.linkconfirmator

import javax.persistence.*

@Entity
data class ConferenceEntity(
        @OneToMany(cascade = arrayOf(CascadeType.ALL))
        var participants: List<Participant> = emptyList(),
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
)

@Entity
data class Participant(
        var email: String = "",
        var confirmationLink: String = "",
        var confirmed: Boolean = false,
        @Id @GeneratedValue(strategy = GenerationType.AUTO)
        var id: Long = 0
)
