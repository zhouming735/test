/** 
 * Project: test 
 * Package Name:proxy 
 * 
 * File Created at 2018年2月28日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.proxy;

import java.lang.reflect.Proxy;

/** 
 * ClassName:ProxyTest <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年2月28日 上午10:20:07 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class ProxyTest {

    public static void main(String[] args) {
        //静态代理
        StaticProxy staticProxy=new StaticProxy();
        staticProxy.proxyHello("Anny");
        
        // 动态代理
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", "true"); 
        CustomInvocationHandler handler =new CustomInvocationHandler(new HelloWorldImpl());
        //
        HelloWorld proxy=(HelloWorld) Proxy.newProxyInstance(ProxyTest.class.getClassLoader(),new Class[]{HelloWorld.class}, handler);
        proxy.sayHello("Mikan"); 
        
        
    }

}
