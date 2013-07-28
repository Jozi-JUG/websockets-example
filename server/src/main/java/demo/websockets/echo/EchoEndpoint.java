package demo.websockets.echo;

import java.io.IOException;
import javax.websocket.CloseReason;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author Michael Williams
 */
@ServerEndpoint("/echo")
public class EchoEndpoint {

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        System.out.println("Session opened - " + session.getId());
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("Session closed - " + session.getId() + " with reason - " + reason.getCloseCode() + " - " + reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable error) {
        System.out.println("Error on session - " + session.getId() + " - " + error.getMessage());
        error.printStackTrace();;
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {
            System.out.println("Session - " + session.getId() + " with message - " + msg);

            for (Session connectedSession : session.getOpenSessions()) {
                if (connectedSession.isOpen()) {
                    connectedSession.getBasicRemote().sendText("Echo - " + msg);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
