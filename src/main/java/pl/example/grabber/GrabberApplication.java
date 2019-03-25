package pl.example.grabber;

import lombok.extern.slf4j.Slf4j;
import pl.example.grabber.domain.grabber.JavaGrabber;
import pl.example.grabber.domain.grabber.KotlinGrabber;

@Slf4j
public class GrabberApplication {

    //FIXME repair scala support in build gradle then uncomment ScalaGrabber
    public static void main(String[] args) {

        JavaGrabber javaGrabber = new JavaGrabber();
        KotlinGrabber kotlinGrabber = new KotlinGrabber();
//        ScalaGrabber scalaGrabber = new ScalaGrabber();
        javaGrabber.getPosts();
        kotlinGrabber.getPosts();
//        scalaGrabber.getPosts();
    }

}
