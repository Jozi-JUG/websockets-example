package demo.websocket.client;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;

/**
 *
 * @author Michael.Williams
 */
@ClientEndpoint 
public class EchoClient {
    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }
    
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Session opened " + session.getId());
    }
 
    @OnError
    public void onError(Throwable t) {
        t.printStackTrace();
    }
}
