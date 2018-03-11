/** 
 * Project: test 
 * Package Name:cn.lomen.rabbitmq.rpc 
 * 
 * File Created at 2018年3月9日
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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/** 
 * ClassName:RPCServer <br/> 
 * rpc工作方式如下：
1：当客户端启动时，它创建一个匿名的独占回调队列。
2：对于rpc请求，客户端发送2个属性，一个是replyTo设置回调队列，另一是correlationId为每个队列设置唯一值
3：请求被发送到一个rpc_queue队列中
4：rpc服务器是等待队列的请求，当收到一个请求的时候，他就把消息返回的结果返回给客户端，使请求结束。
5：客户端等待回调队列上的数据，当消息出现的时候，他检查correlationId，如果它和从请求返回的值匹配，就进行响应。
 * Date:     2018年3月9日 下午6:01:24 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class RpcConsumer {
    private static final String RPC_QUEUE_NAME = "rpc_queue";  
    
    public static void main(String[] argv) throws IOException{  
        ConnectionFactory factory = new ConnectionFactory();  
        //rabbitmq监听IP  
        factory.setHost("localhost");  
        //rabbitmq监听默认端口  
        factory.setPort(5672);  
        //设置访问的用户  
        //factory.setUsername("test");  
        //factory.setPassword("test");  
  
        Connection connection = null;  
        try {  
            connection = factory.newConnection();  
            final Channel channel = connection.createChannel();  
             //
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);  
            System.out.println("等待接受producer消息....");  
  
            Consumer consumer = new DefaultConsumer(channel) {  
                //对消费消息进一步处理
                @Override  
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {  
                    //实例化一个回复队列的基本配置
                    AMQP.BasicProperties replyProps = new AMQP.BasicProperties  
                            .Builder()  
                            .correlationId(properties.getCorrelationId())  
                            .build();  
  
                      
                    String response = "";  
                    try {  
                        String message = new String(body,"UTF-8");  
                        System.out.println("consumer 接收的消息是：" + message);  
                        //  
                        response = handleMsg(message);  
                          
                        System.out.println("consumer 发送的消息是：" + response);  
                    }  
                    catch (RuntimeException e){  
                        e.printStackTrace();  
                    }finally {
                        //发布回复信息
                        channel.basicPublish( "", properties.getReplyTo(), replyProps, response.getBytes("UTF-8"));  
                        //告知
                        channel.basicAck(envelope.getDeliveryTag(), false);  
                    }  
                }  
            }; 
            //消费
            channel.basicConsume(RPC_QUEUE_NAME, false, consumer);  
        } catch (Exception e) {  
              
            connection.close();  
            e.printStackTrace();  
        }   
    }  
    private static String handleMsg(String msg) {  
          
        Date date = new Date();  
        String response = "";  
        switch (msg) {  
        case "时间":  
            response = new SimpleDateFormat("HH:mm").format(date);  
            break;  
        case "日期":  
            response = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒").format(date);  
            break;  
        default:  
            response = "未知信息";  
            break;  
        }  
        return response;  
    } 
}
