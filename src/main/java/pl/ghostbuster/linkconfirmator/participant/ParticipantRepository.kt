package pl.ghostbuster.linkconfirmator.participant

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ParticipantRepository : CrudRepository<Participant, UUID>