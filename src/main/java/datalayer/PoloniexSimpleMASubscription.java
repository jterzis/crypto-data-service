package datalayer;

import com.cf.data.handler.poloniex.*;
import ws.wamp.jawampa.PubSubData;
/**
 * Created by johnterzis on 11/5/17.
 */
public class PoloniexSimpleMASubscription extends PoloniexSubscription {
        public PoloniexSimpleMASubscription(String feedName) {super(feedName);}

        public void call(PubSubData event) {
                try {
                        LOG.trace(event.arguments());
                } catch (Exception var3) {
                        LOG.warn("Exception processing event data - " + var3.getMessage());
                }

        }

}
