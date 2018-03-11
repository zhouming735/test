/** 
 * Project: test 
 * Package Name:atomictest 
 * 
 * File Created at 2018年3月3日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.atomictest;

import java.util.concurrent.atomic.AtomicInteger;

/** 
 * ClassName:AtomicIntegerDemo <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月3日 上午12:09:21 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class AtomicIntegerDemo {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        AtomicInteger ai=new AtomicInteger(0);
        int i1=ai.get();
        v(i1);
        int i2=ai.getAndSet(5);
        v(i2);
        int i3=ai.get();
        v(i3);
        int i4=ai.getAndIncrement();
        v(i4);
        v(ai.get());
        
       }
       static void v(int i)
       {
        System.out.println("i : "+i);
       }

}
