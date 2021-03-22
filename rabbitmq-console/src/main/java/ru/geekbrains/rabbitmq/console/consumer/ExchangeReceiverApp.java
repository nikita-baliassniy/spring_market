package ru.geekbrains.rabbitmq.console.consumer;

import com.rabbitmq.client.*;

import java.io.InputStreamReader;
import java.util.Scanner;

public class ExchangeReceiverApp {
    private static final String EXCHANGE_NAME = "topic_exchange";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();
        System.out.println("My queue name: " + queueName);

        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        String routingKey = scanner.nextLine();
        scanner.close();
        channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        System.out.println(" [*] Waiting for messages with routing key (" + routingKey + "):");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };

        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}
