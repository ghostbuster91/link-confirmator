package pl.ghostbuster.linkconfirmator.participant

import org.springframework.data.repository.CrudRepository
import pl.ghostbuster.linkconfirmator.conference.model.Participant
import java.util.*

interface ParticipantRepository : CrudRepository<Participant, UUID>