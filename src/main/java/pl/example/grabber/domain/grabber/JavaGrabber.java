package pl.example.grabber.domain.grabber;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import pl.example.grabber.common.Grabber;
import pl.example.grabber.common.model.Post;

import java.io.File;
import java.util.List;

import static pl.example.grabber.common.ApplicationConstants.BASE_PATH;
import static pl.example.grabber.common.ApplicationConstants.JAVA;
import static pl.example.grabber.common.ApplicationConstants.TARGET_URL;

@Slf4j
@NoArgsConstructor
public class JavaGrabber implements Grabber {
    ObjectMapper mapper = new ObjectMapper();

    @Getter(lazy = true)
    private final File storage = new File(BASE_PATH + JAVA);

    private List<Post> grabPosts() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.exchange(
            TARGET_URL,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Post>>() {
            }).getBody();
    }

    private void createFiles(List<Post> posts) {
        posts.stream().forEach(p -> createFile(p));
    }

    @SneakyThrows
    private void createFile(Post post) {
        createDirectory();
        mapper.writeValue(new File(BASE_PATH+ JAVA + "/user_" + post.getUserId() + "_post_" + post.getId()), post);
    }

    private void createDirectory() {
        if (!getStorage().exists()) {
            getStorage().mkdirs();
        }
    }

    @Override
    public void getPosts() {
        List<Post> posts = grabPosts();
        createFiles(posts);
    }
}