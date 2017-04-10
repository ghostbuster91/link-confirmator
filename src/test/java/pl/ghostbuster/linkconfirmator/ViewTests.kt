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

    @Test
    fun `should render login page`() {
        val executor = TestExecutor()
        executor.execute("classpath:login.thtest")
        assertTrue(executor.isAllOK)
    }

    @Test
    fun `should render login page with error`() {
        val executor = TestExecutor()
        executor.execute("classpath:login_invalid_credentials.thtest")
        assertTrue(executor.isAllOK)
    }

    @Test
    fun `should render login page with logout info`() {
        val executor = TestExecutor()
        executor.execute("classpath:login_logout.thtest")
        assertTrue(executor.isAllOK)
    }
}
