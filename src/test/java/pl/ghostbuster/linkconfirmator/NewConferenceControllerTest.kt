package pl.ghostbuster.linkconfirmator

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@RunWith(SpringJUnit4ClassRunner::class)
@WebAppConfiguration
@ContextConfiguration(classes = arrayOf(NewConferenceControllerTest.TestConfiguration::class))
class NewConferenceControllerTest {

    @Autowired
    private lateinit var webApplicationContext: WebApplicationContext
    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

    @Test
    fun shouldReturnOkStatusOnHttpGet() {
        mockMvc.perform(get("/conference"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(view().name("new_conference"))
                .andExpect(content().string(equalTo(stringFromFile("new_conference_rendered.html"))))
    }

    @EnableAutoConfiguration
    @ComponentScan
    @Configuration
    open class TestConfiguration
}
