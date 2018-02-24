
package tech.claudioed.msa.goodbye;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

import java.text.SimpleDateFormat;
import java.util.Date;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ReactiveServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(ReactiveServerApplication.class, args);
  }

  @Bean
  public RouterFunction<ServerResponse> routes() {
    return route(GET("/api/goodbye"), serverRequest -> ok().body(fromPublisher(goodbye(),String.class)))
        .andRoute(GET("/api/nap"), request -> ok().body(fromPublisher(nap(),String.class)));
  }

  public Mono<String> nap(){
    return Mono.create(monoSink -> {
      System.out.println("Received request on Thread: " + Thread.currentThread().getName());
      Pi.computePi(20000);
      System.out.println("Back from the nap with " + Thread.currentThread().getName());
      monoSink.success("Nap from " + new Date().toString());
    });
  }

  public Mono<String> goodbye(){
    String msg = "Hello with Spring Boot 2 (Reactive) on " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS").format(new Date());
    return Mono.just(msg);
  }


}
