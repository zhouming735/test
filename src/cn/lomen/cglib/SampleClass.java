/**
 * Project: test Package Name:cglib
 * 
 * File Created at 2018年3月5日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd All Rights Reserved.
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd. Copying or
 * reproduction without prior written approval is prohibited.
 * 
 */
package cn.lomen.cglib;

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 * ClassName:SampleClass <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月5日 上午11:52:42 <br/>
 * 
 * @author <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version
 */
public class SampleClass {

    public void test(){
        System.out.println("hello world");
    }
    
    public String test(String name){
        return "hello "+name;
    }

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(SampleClass.class);
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                System.out.println("before method run...");
                Object result = proxy.invokeSuper(obj, args);
                System.out.println("after method run...");
                return result;
            }
        });
        SampleClass sample = (SampleClass) enhancer.create();
        sample.test();
        
        //System.out.println("end");
    }

}
