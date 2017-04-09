package pl.ghostbuster.linkconfirmator

import org.junit.Assert.assertTrue
import org.junit.Test
import org.thymeleaf.testing.templateengine.engine.TestExecutor

class ViewTests {

    @Test
    fun `should render new_conference_submitted`() {
        val executor = TestExecutor()
        executor.execute("classpath:new_conference_submitted.thtest")
        assertTrue(executor.isAllOK)
    }

    @Test
    fun `should render new_conference_submission`() {
        val executor = TestExecutor()
        executor.execute("classpath:new_conference_submission.thtest")
        assertTrue(executor.isAllOK)
    }

    @Test
    fun `should render conference_list`() {
        val executor = TestExecutor()
        executor.execute("classpath:conference_list.thtest")
        assertTrue(executor.isAllOK)
    }
}
