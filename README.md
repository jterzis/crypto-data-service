# crypto-data-service
Cryptocurrency Market Data Service for producing Simple Moving Average

## Dependencies

Maven
Java 8
DynamoDB

## When run with a currency pair, writes raw trade data served over web sockets from Poloniex to append only Dynamodb table

java -jar ./target/websocket-client-1.1.jar "USDT_BTC"
