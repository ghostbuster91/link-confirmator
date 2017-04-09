package pl.ghostbuster.linkconfirmator

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ConferenceRepository : CrudRepository<ConferenceEntity, UUID>