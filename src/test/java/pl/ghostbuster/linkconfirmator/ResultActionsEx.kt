package pl.ghostbuster.linkconfirmator

import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import java.io.File

fun ResultActions.andExpectJson(fileName: String): ResultActions {
    return andExpect(content().json(stringFromFile(fileName)))
}

private fun stringFromFile(fileName: String): String {
    return fileFromResource(fileName).readLines().joinToString("\n")
}

private fun fileFromResource(fileName: String): File {
    return File(Thread.currentThread().contextClassLoader.getResource(fileName).toURI())
}