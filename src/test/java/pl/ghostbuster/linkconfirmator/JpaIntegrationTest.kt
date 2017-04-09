package pl.ghostbuster.linkconfirmator

import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner

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
    }

    @Test
    fun `should save conference entity with participant`() {
        conferenceRepository.save(ConferenceEntity(participants = listOf(Participant(email = "test@email"))))
    }

    @EnableAutoConfiguration
    @ComponentScan
    @Configuration
    open class TestConfiguration

    @After
    fun tearDown() {
        conferenceRepository.deleteAll()
    }
}


