package datalayer;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.cf.data.handler.poloniex.*;
import ws.wamp.jawampa.PubSubData;
import datalayer.DynamoClient;
import datalayer.DataTuple;
/**
 * Created by johnterzis on 11/5/17.
 */
public class PoloniexSimpleMASubscription extends PoloniexSubscription {
        private DynamoClient dynamoClient = null;
        private List<String> tickers = null;

        public PoloniexSimpleMASubscription(String feedName, DynamoClient dynamoclient, List<String> tickerSymbols)
        {
                super(feedName);
                dynamoClient = dynamoclient;
                tickers = tickerSymbols;

        }

        //public static final PoloniexSubscription TICKER_1_PAIR = new PoloniexSimpleMASubscription("ticker");

        public void call(PubSubData event) {
                try {
                        ArrayNode json_array = event.arguments();
                        String pair = json_array.get(0).asText();
                        String last = json_array.get(1).asText();
                        for (String oneTicker : tickers) {
                                if (pair.equalsIgnoreCase(oneTicker)) {
                                        Long date = new Date().getTime();
                                        DataTuple item = new DataTuple<String, String, String>(
                                                pair, last, Long.toString(date+100));
                                        dynamoClient.insertItemQuoteTable(item);
                                        // TODO: Convert to bulk insert within 1 minute intervals
                                        //LOG.trace(event.arguments());
                                        //LOG.trace(item);
                                }
                        }
                } catch (Exception var3) {
                        LOG.warn("Exception processing event data - " + var3.getMessage());
                        LOG.warn(var3.getStackTrace());
                }

        }

}
