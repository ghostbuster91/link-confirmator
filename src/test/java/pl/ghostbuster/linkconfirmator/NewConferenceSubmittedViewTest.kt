package pl.ghostbuster.linkconfirmator

import org.junit.Assert
import org.junit.Test
import org.thymeleaf.testing.templateengine.engine.TestExecutor

class NewConferenceSubmittedViewTest{
    @Test
    fun renderWithoutErrors() {
        val executor = TestExecutor()
        executor.execute("classpath:new_conference_submitted.thtest")
        Assert.assertTrue(executor.isAllOK)
    }
}
