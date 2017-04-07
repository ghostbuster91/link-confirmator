package pl.ghostbuster.linkconfirmator

import java.io.File

fun stringFromFile(fileName: String): String {
    return fileFromResource(fileName).readLines().joinToString("\n")
}

fun fileFromResource(fileName: String): File {
    return File(Thread.currentThread().contextClassLoader.getResource(fileName).toURI())
}