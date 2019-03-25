package pl.example.grabber.domain.grabber

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import pl.example.grabber.common.ApplicationConstants.*
import pl.example.grabber.common.Grabber
import pl.example.grabber.common.model.Post
import java.io.File

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "NOTHING_TO_INLINE")
class KotlinGrabber : Grabber {
    private var mapper = ObjectMapper()

    val storage = File(BASE_PATH + KOTLIN)

    private fun grabPosts(): List<Post> {
        val restTemplate = RestTemplate()
        return restTemplate.exchange(
                TARGET_URL,
                HttpMethod.GET,
                null,
                object : ParameterizedTypeReference<List<Post>>() {

                }).body
    }

    private fun createFiles(posts: List<Post>) {
        posts.stream().forEach { p -> createFile(p) }
    }

    private fun createFile(post: Post) {
        createDirectory()
        mapper.writeValue(File(BASE_PATH + KOTLIN + USER_PATH_PREFIX + post.accessPrivateField("userId") + USER_POST_NUMBER_PREFIX + post.accessPrivateField("id")), post)
    }

    private fun createDirectory() {
        if (!storage.exists()) {
            storage.mkdirs()
        }
    }

    override fun getPosts() {
        val posts = grabPosts()
        createFiles(posts)
    }

    private inline fun Post.accessPrivateField(fieldName: String): Int {
        return javaClass.getDeclaredField(fieldName).let {
            it.isAccessible = true
            return@let it.getInt(this)
        }
    }
}