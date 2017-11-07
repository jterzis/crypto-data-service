package cryptoquoteapp;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import datalayer.PoloniexSimpleMASubscription;
import datalayer.DynamoClient;
import datalayer.DataTuple;
import ws.wamp.jawampa.ApplicationError;
import com.cf.client.WSSClient;
import com.cf.data.handler.poloniex.*;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;
import software.amazon.awssdk.services.dynamodb.model.ListTablesResponse;

/**
 * Created by johnterzis on 11/5/17.
 * Implements WSSClient from https://github.com/TheCookieLab/poloniex-api-java
 */
public class PoloniexQuoteApp {
    private final static Logger LOG = LogManager.getLogger();

    public static void main(String[] args) {
        try (WSSClient poloniexWSSClient = new WSSClient("wss://api.poloniex.com", "realm1"))
        {
            List<String> tickers = new ArrayList<String>();
            if (args.length > 0)
            {
                for (String ticker : args) {tickers.add(ticker);}
            }
            else {tickers.add("USDT_BTC");}

            // Create DynamoDB client
            DynamoClient dynamoClient = new DynamoClient();
            String table_creation_status = dynamoClient.CheckCreateQuoteTable();

            PoloniexSimpleMASubscription poloTickerSubscribe = new PoloniexSimpleMASubscription(
                    "ticker", dynamoClient, tickers);
            poloniexWSSClient.subscribe(poloTickerSubscribe);
            /*
            Long date = new Date().getTime();
            Date date_str = new Date(date);
            DataTuple dataTuple = new DataTuple<String, String, String>("john", "1000.00", Long.toString(date));
            dynamoClient.insertItem(dataTuple);
            */
            /*
            for (String ticker : tickers) {
                LOG.trace("Adding market data for " + ticker);
                poloniexWSSClient.subscribe(new PoloniexSimpleMASubscription(ticker));
            }
            */
            LOG.trace("About to run client....");
            poloniexWSSClient.run(1200000000);

        } catch (InterruptedException ex) {
            System.err.println("InterruptedException exception: " + ex.getMessage());
        } catch (URISyntaxException ex) {
            System.err.println("URISyntaxException exception: " + ex.getMessage());
        } catch (ApplicationError ex){
            System.err.println("Other exception: " + ex.getMessage());
        }
        catch (Exception ex){
        System.err.println("Other exception: " + ex.getMessage());
    }
    }
}
