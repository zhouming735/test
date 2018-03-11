/** 
 * Project: test 
 * Package Name:cn.lomen.rabbitmq 
 * 
 * File Created at 2018年3月7日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.rabbitmq;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/** 
 * ClassName:Receiver 
 * 消费方需要长连接
 * Date: 2018年3月7日 下午11:35:40 
 * @version       
 */
public class Receiver {
    private final static String QUEUE_NAME = "MyQueue1";  
    
    public static void main(String[] args) {  
        receive();  
    }  
    
    public static void receive()  
    {  
        ConnectionFactory factory = null;  
        Connection connection = null;  
        Channel channel = null;  
          
        try {  
            factory = new ConnectionFactory();  
            factory.setHost("localhost");
            factory.setPort(5672);
            
            //factory.setUsername("admin");
            //factory.setPassword("admin");
            //创建一个新的连接
            connection = factory.newConnection();
            //创建一个通道
            channel = connection.createChannel(); 
            //声明要关注的队列
            //指定队列持久化
            channel.queueDeclare(QUEUE_NAME, true, false, false, null); 
            System.out.println("Customer Waiting Received messages");
            
            //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
            // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body)
                        throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Customer Received '" + message + "'");
                }
            };
            //自动回复队列应答 -- RabbitMQ中的消息确认机制
            //指定消费队列 ,第二个参数false表示不自动应答，如果为true的话，每次生产者只要发送信息就会从内存中删除
            channel.basicConsume(QUEUE_NAME, true, consumer);
            
        } catch (Exception e) {  
            e.printStackTrace();  
        } 
        
    }
 
    

}
