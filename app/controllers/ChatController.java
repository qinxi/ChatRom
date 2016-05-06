package controllers;

import akka.actor.ActorSystem;
import com.google.inject.Inject;
import play.mvc.*;
import scala.concurrent.ExecutionContextExecutor;
import scala.concurrent.duration.Duration;
import views.html.index;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinxi on 16/5/6.
 */
@Singleton
public class ChatController extends Controller {

    private static Map<String,WebSocket.Out> outMap = new HashMap<>();

    private static Map<String,String> users = new HashMap<>();

    public LegacyWebSocket<String> socket1(String name) {
        String uuid = UUID.randomUUID().toString();

        System.out.println(name);
        return WebSocket.whenReady((in, out) -> {

            outMap.forEach((id,out1)->{
                out1.write(String.format("%s is online",name));
            });


            users.put(uuid,name);
            outMap.put(uuid,out);

            // For each event received on the socket,
            in.onMessage((m) -> {
                outMap.forEach((id,out1)->{
                    if (out==out1) return;
                    out1.write(String.format("%s:%s",name,m));
                });
            });

            // When the socket is closed.
            in.onClose(() ->{
                users.remove(uuid);
                outMap.remove(uuid);
                outMap.forEach((id,out1)->{
                    out1.write(String.format("%s is logout",name));
                });
            });
        });
    }


}
