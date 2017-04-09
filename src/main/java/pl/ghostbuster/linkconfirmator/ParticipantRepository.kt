package pl.ghostbuster.linkconfirmator

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ParticipantRepository : CrudRepository<Participant, UUID>