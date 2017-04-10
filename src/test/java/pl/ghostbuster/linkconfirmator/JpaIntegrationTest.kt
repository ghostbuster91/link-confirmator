package pl.ghostbuster.linkconfirmator

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import pl.ghostbuster.linkconfirmator.conference.ConferenceRepository
import pl.ghostbuster.linkconfirmator.conference.model.ConferenceEntity
import pl.ghostbuster.linkconfirmator.conference.model.Participant

@RunWith(SpringJUnit4ClassRunner::class)
@ContextConfiguration(classes = arrayOf(JpaIntegrationTest.TestConfiguration::class))
class JpaIntegrationTest {

    @Autowired
    lateinit var conferenceRepository: ConferenceRepository

    @Test
    fun `should create repository`() {
        assertThat(conferenceRepository).isNotNull()
    }

    @Test
    fun `should save conference entity to repository`() {
        conferenceRepository.save(ConferenceEntity(participants = emptyList()))
        assertThat(conferenceRepository.count()).isEqualTo(1)
    }

    @Test
    fun `should save conference entity with participant`() {
        conferenceRepository.save(ConferenceEntity(participants = listOf(Participant(email = "test@email"))))
        assertThat(conferenceRepository.count()).isEqualTo(1)
        val participant = conferenceRepository.findAll().first().participants.first()
        assertThat(participant.email).isEqualTo("test@email")
    }

    @Test
    fun `should save two conferences entity`() {
        conferenceRepository.save(ConferenceEntity(participants = listOf(Participant(email = "first@email"))))
        conferenceRepository.save(ConferenceEntity(participants = listOf(Participant(email = "second@email"))))

        val participants = conferenceRepository.findAll().flatMap { it.participants }
        assertThat(participants.map { it.email }).contains("first@email", "second@email")
    }

    @Configuration
    @EnableAutoConfiguration
    open class TestConfiguration

    @After
    fun tearDown() {
        conferenceRepository.deleteAll()
    }
}


