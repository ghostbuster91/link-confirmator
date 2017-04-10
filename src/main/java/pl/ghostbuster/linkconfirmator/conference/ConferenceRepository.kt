package pl.ghostbuster.linkconfirmator.conference

import org.springframework.data.repository.CrudRepository
import pl.ghostbuster.linkconfirmator.conference.model.ConferenceEntity
import java.util.*

interface ConferenceRepository : CrudRepository<ConferenceEntity, UUID>