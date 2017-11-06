package cryptoquoteapp;

/**
 * Created by johnterzis on 11/5/17.
 */
import websocketclient.WebSocketClient;
import java.net.URI;
import java.net.URISyntaxException;


public class CryptoQuoteApp {

    public static void main(String[] args) {
        try {
            // open websocket
            final WebSocketClient clientEndPoint = new WebSocketClient(new URI("wss://api.poloniex.com"));

            // add listener
            clientEndPoint.addMessageHandler(new WebSocketClient.MessageHandler() {
                public void handleMessage(String message) {
                    System.out.println(message);
                }
            });

            // send message to websocket
            clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");
            System.out.println("Starting web socket connection to server..");
            // wait 5 seconds for messages from websocket
            Thread.sleep(5000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        }
    }
}