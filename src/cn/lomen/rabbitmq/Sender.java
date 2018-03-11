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

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

/** 
 * ClassName:Sender <br/> 
 * RabbitMQ 生产者 生产消息 涉及jar包：amqp-client-xxx.jar  log4j-xxx.jar slf4j-api-xx.jar slf4j-log4j12-xx.jar
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class Sender {

    private final static String QUEUE_NAME = "MyQueue1"; 
    
    public static void main(String[] args) {
       
        send(); 
    }

    private static void send() {
        ConnectionFactory factory = null;  
        Connection connection = null;  
        Channel channel = null;  
        try{
            //创建连接工厂
            factory=new ConnectionFactory();
            //设置RabbitMQ相关信息
            factory.setHost("localhost");
            factory.setPort(5672);
            factory.setUsername("admin");
            factory.setPassword("admin");
            
            // 关键所在，指定线程池  
            ExecutorService service = Executors.newFixedThreadPool(10); 
            factory.setSharedExecutor(service);
            // 设置自动恢复  
            factory.setAutomaticRecoveryEnabled(true);  
            factory.setNetworkRecoveryInterval(2);// 设置 没10s ，重试一次  
            factory.setTopologyRecoveryEnabled(false);// 设置不重新声明交换器，队列等信息
            
            
            connection=factory.newConnection();
            channel=connection.createChannel();
            /**
             *指定队列持久化
             *注1：queueDeclare第一个参数表示队列名称、第二个参数为是否持久化（true表示是，队列将在服务器重启时生存）、
             *第三个参数为是否是独占队列（创建者可以使用的私有队列，断开后自动删除）、第四个参数为当所有消费者客户端连接断开时是否自动删除队列、
             *第五个参数为队列的其他参数
             */
            channel.queueDeclare(QUEUE_NAME, true, false, false, null);
            //String message = "my first message .....";
            /**
             * 注2：basicPublish第一个参数为交换机名称、第二个参数为队列映射的路由key、第三个参数为消息的其他属性、第四个参数为发送信息的主体
             */
          //分发信息
            for (int i=0;i<10;i++){
                String message="Hello RabbitMQ"+i;
                channel.basicPublish("",QUEUE_NAME,
                        MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
                System.out.println("NewTask send '"+message+"'");
            }
            //channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));  
            //System.out.println("已经发送消息....."+message);  
        }
        catch (Exception e) {
            e.printStackTrace();  
        }
        finally {
            try {  
                //关闭资源  
                channel.close();  
                connection.close();  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }
    }

}
