package demo.websocket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

/**
 *
 * @author Michael.Williams
 */
public class Main {
    private Session session;

    public static void main(String[] args) {
        final Main app = new Main();
        
        Runnable r = new Runnable() {
            public void run() {
                try {
                    WebSocketContainer container = ContainerProvider.getWebSocketContainer();
                    app.session = container.connectToServer(EchoClient.class, URI.create("ws://localhost:8080/jug-websockets-demo/echo"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
                    
        new Thread(r).start();
        
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        try {
            do{
                input = br.readLine();
                if(!input.equals("exit"))
                    app.session.getBasicRemote().sendText(input);
 
            }while(!input.equals("exit"));
 
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
}
