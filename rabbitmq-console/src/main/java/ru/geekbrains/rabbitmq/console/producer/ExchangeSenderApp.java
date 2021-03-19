package ru.geekbrains.rabbitmq.console.producer;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.InputStreamReader;
import java.util.Scanner;


public class ExchangeSenderApp {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
            while (true) {
                String inputString = scanner.nextLine();
                String message = inputString.split(" ", 2)[1];
                if (!message.equals("exit")) {
                    String routingKey = inputString.split(" ", 2)[0];
                    channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
                    System.out.println(" [x] Sent '" + routingKey + ":" + message + "'");
                } else {
                    scanner.close();
                    return;
                }
            }
        }
    }
}