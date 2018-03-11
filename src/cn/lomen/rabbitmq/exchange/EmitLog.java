/** 
 * Project: test 
 * Package Name:cn.lomen.rabbitmq.exchange 
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
package cn.lomen.rabbitmq.exchange;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/** 
 * ClassName:EmitLog <br/> 
 * 广播模式发送信息
 * Date:     2018年3月9日 下午4:10:14 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class EmitLog {

    private static final String EXCHANGE_NAME = "logs";
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory=new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection=factory.newConnection();
        Channel channel=connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//fanout表示分发，所有的消费者得到同样的队列信息
        //分发信息
        for (int i=0;i<5;i++){
            String message="Hello World"+i;
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes());
            System.out.println("EmitLog Sent '" + message + "'");
        }
        channel.close();
        connection.close();
    }

}
