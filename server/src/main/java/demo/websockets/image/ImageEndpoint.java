package demo.websockets.image;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
@ServerEndpoint("/imageChooser")
public class ImageEndpoint {

    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        System.out.println("Image session opened - " + session.getId());
    }

    @OnClose
    public void close(Session session, CloseReason reason) {
        System.out.println("Image session closed - " + session.getId() + " with reason - " + reason.getCloseCode() + " - " + reason.getReasonPhrase());
    }

    @OnError
    public void error(Session session, Throwable error) {
        System.out.println("Error on image session - " + session.getId() + " - " + error.getMessage());
        error.printStackTrace();
    }

    @OnMessage
    public void onMessage(Session session, String msg) {
        try {

            System.out.println("Image session - " + session.getId() + " with message - " + msg);
            
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            //classLoader.getResourceAsStream("../../WEB-INF/images/duke.jpeg")

            for (Session connectedSession : session.getOpenSessions()) {
                if (connectedSession.isOpen()) {
                    //TODO Move out of loop and do asynch???
                    try (InputStream is = classLoader.getResourceAsStream("../../WEB-INF/images/duke.jpeg"); OutputStream os = connectedSession.getBasicRemote().getSendStream()) {
                        byte[] b = new byte[1024];
                        while(is.read(b) > -1){
                            os.write(b);
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
