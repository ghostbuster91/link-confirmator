package pl.ghostbuster.linkconfirmator

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class MalformedUuid : RuntimeException()