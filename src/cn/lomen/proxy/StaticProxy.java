/** 
 * Project: test 
 * Package Name:proxy 
 * 
 * File Created at 2018年3月5日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.proxy;  
/** 
 * ClassName:StaticProxy <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月5日 上午11:36:09 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class StaticProxy{

     HelloWorld hello=new HelloWorldImpl();
    
    public  void proxyHello(String name) {
       System.out.println("静态代理开始");
        hello.sayHello(name);
        System.out.println("静态代理结束");
        
    }

}
