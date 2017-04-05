package pl.ghostbuster.linkconfirmator

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@RestController
class NewsController {

    @RequestMapping(value = "/news", method = arrayOf(RequestMethod.GET))
    fun repositoryDetails(): List<News> {
        return listOf(News("http://blog.cleancoder.com/uncle-bob/2017/03/03/TDD-Harms-Architecture.html"))
    }
}
