package controllers;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import play.mvc.*;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;
import views.html.index;

import javax.inject.Singleton;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinxi on 16/5/6.
 */
@Singleton
public class ChatController extends Controller {


    public LegacyWebSocket<String> socket1(String name) {
        System.out.println(name);
        return WebSocket.whenReady((in, out) -> {
            // For each event received on the socket,
            in.onMessage((m) -> {
                    System.out.println(m);
                    out.write("Hello!");
            });

            // When the socket is closed.
            in.onClose(() -> System.out.println("Disconnected"));

            // Send a single 'Hello!' message

        });
    }


}
