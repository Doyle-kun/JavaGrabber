package pl.example.grabber.domain.grabber

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.web.client.RestTemplate
import pl.example.grabber.common.model.Post
import java.io.File
import java.util

import pl.example.grabber.common.ApplicationConstants.BASE_PATH
import pl.example.grabber.common.ApplicationConstants.TARGET_URL
import pl.example.grabber.common.ApplicationConstants.USER_PATH_PREFIX
import pl.example.grabber.common.ApplicationConstants.USER_POST_NUMBER_PREFIX
import pl.example.grabber.common.ApplicationConstants.SCALA

class ScalaGrabber() {
  def getStorage: File =
  {
    this.storage
  }
  private[grabber] val mapper = new ObjectMapper
  final private val storage = new File(BASE_PATH + SCALA)

  private def grabPosts = {
    val restTemplate = new RestTemplate
    restTemplate.exchange(TARGET_URL, HttpMethod.GET, null, new ParameterizedTypeReference[util.List[Post]]() {}).getBody
  }

  private def createFiles(posts: util.List[Post]): Unit = {
    posts.stream.forEach((p: Post) => createFile(p))
  }

  private def createFile(post: Post): Unit = {
    createDirectory()
    mapper.writeValue(new File(BASE_PATH + SCALA + USER_PATH_PREFIX + post.getUserId + USER_POST_NUMBER_PREFIX + post.getId), post)
  }

  private def createDirectory(): Unit = {
    if (!storage.exists) storage.mkdirs
  }

  def getPosts(): Unit = {
    val posts = grabPosts
    createFiles(posts)
  }
}
