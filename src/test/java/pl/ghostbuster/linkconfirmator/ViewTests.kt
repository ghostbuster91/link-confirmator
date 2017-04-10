package pl.ghostbuster.linkconfirmator

import org.junit.Assert.assertTrue
import org.junit.Test
import org.thymeleaf.testing.templateengine.engine.TestExecutor

class ViewTests {

    @Test
    fun `should render new_conference_submitted`() {
        runTest("new_conference_submitted")
    }

    @Test
    fun `should render new_conference_submission`() {
        runTest("new_conference_submission")
    }

    @Test
    fun `should render conference_list`() {
        runTest("conference_list")
    }

    @Test
    fun `should render login page`() {
        runTest("login")
    }

    @Test
    fun `should render login page with error`() {
        runTest("login_invalid_credentials")
    }

    @Test
    fun `should render login page with logout info`() {
        runTest("login_logout")
    }

    private fun runTest(name: String) {
        val executor = TestExecutor()
        executor.execute("classpath:$name.thtest")
        assertTrue(executor.isAllOK)
    }
}
