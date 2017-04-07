package pl.ghostbuster.linkconfirmator

import org.springframework.data.repository.CrudRepository

interface ConferenceRepository : CrudRepository<ConferenceEntity, Long>