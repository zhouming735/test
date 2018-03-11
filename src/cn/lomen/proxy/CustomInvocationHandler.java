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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/** 
 * ClassName:CustomInvocationHandler <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年2月28日 上午10:17:21 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version 
 * 动态代理类       
 */
public class CustomInvocationHandler implements InvocationHandler {

    private Object target; 
    public CustomInvocationHandler(Object target) {  
        this.target = target;  
    }
    
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // TODO Auto-generated method stub
        System.out.println("Before invocation"); 
        Object retVal = method.invoke(target, args);
        System.out.println("After invocation");  
        return retVal;
    }

}
