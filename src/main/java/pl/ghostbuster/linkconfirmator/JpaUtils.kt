package pl.ghostbuster.linkconfirmator

import java.util.*

val DEFAULT_UUID: UUID by lazy { UUID.randomUUID() }

fun parseUuid(id: String) = try {
    UUID.fromString(id)
} catch (ex: IllegalArgumentException) {
    throw MalformedUuid()
}
