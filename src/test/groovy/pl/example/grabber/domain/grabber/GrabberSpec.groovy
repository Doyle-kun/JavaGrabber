package pl.example.grabber.domain.grabber

import groovy.util.logging.Slf4j
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import pl.example.grabber.common.Grabber
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import static pl.example.grabber.common.ApplicationConstants.*

@ExtendWith(SpringExtension.class)
@Slf4j
class GrabberSpec extends Specification {

    private static final String REGEX_PATTERN = USER_PATH_PREFIX +"(\\\\d+)"+ USER_POST_NUMBER_PREFIX + "(\\\\d+)"
    @Shared
    def javaGrabber
    @Shared
    def kotlinGrabber

    def setupSpec() {
        javaGrabber = new JavaGrabber()
        kotlinGrabber = new KotlinGrabber()
    }

    @Unroll
    def "GrabberShouldGrabAllPosts"(Grabber grabber, String directoryTypePath) {
        def baseDir = new File(BASE_PATH + directoryTypePath)
        when:
        grabber.getPosts()
        then:
        baseDir.exists()
        File[] files = baseDir.listFiles().sort()
        files.size() == 100
        100.times {
            files[it].getName().matches(BASE_PATH + directoryTypePath + REGEX_PATTERN)
        }
        and:
        baseDir.deleteDir()

        where:
        grabber       | directoryTypePath
        javaGrabber   | JAVA
        kotlinGrabber | KOTLIN
//        scalaGrabber  | SCALA //FIXME repair scala support in build.gradle


    }

}
