/** 
 * Project: test 
 * Package Name:cn.lomen.rabbitmq.rpc 
 * 
 * File Created at 2018年3月10日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.rabbitmq.rpc;

import java.io.IOException;
import java.util.UUID;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/** 
 * ClassName:RpcProducer <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月10日 下午10:39:36 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class RpcProducer {
    private Connection connection;  
    private Channel channel;  
    private String requestQueueName = "rpc_queue";  
    private String replyQueueName;  
      
      
    public static void main(String[] args) throws Exception {  
          
        RpcProducer producer = new RpcProducer();  
        producer.call("时间");  
        producer.call("日期");  
        producer.call("什么都不是~");  
          
          
        //producer.close();  
          
    }  
  
    public RpcProducer() throws Exception {  
        ConnectionFactory factory = new ConnectionFactory();  
        //rabbitmq监听IP  
        factory.setHost("localhost");  
        //rabbitmq监听默认端口  
        factory.setPort(5672);  
        //设置访问的用户  
        //factory.setUsername("test");  
        //factory.setPassword("test");  
  
        connection = factory.newConnection();  
        channel = connection.createChannel();  
  
        //生成一个临时的接受队列名  
        replyQueueName = channel.queueDeclare().getQueue();  
    }  
  
    public void call(String message) throws Exception {  
          
        //生成一个唯一的字符串  
        final String corrId = UUID.randomUUID().toString();  
  
        //将corrId、replyQueueName打包发送给consumer  
        AMQP.BasicProperties props = new AMQP.BasicProperties  
                .Builder()  
                .correlationId(corrId)  
                .replyTo(replyQueueName)  
                .build();  
  
        System.out.println("producer 发送的消息是 :" + message);
        //使用默认的交换机进行发布消息
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));  
  
        //同时消费来自客户端回复队列的信息
        channel.basicConsume(replyQueueName, true, new DefaultConsumer(channel) {  
            @Override  
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {  
                //获取corrId相同的回复消息  
                if (properties.getCorrelationId().equals(corrId)) {  
                    System.out.println("producer 接收的消息是 :" + new String(body, "UTF-8"));  
                }  
            }  
        });  
  
    }  
  
    public void close() throws Exception {  
        channel.close();  
        connection.close();  
    } 
}
