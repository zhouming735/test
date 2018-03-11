/** 
 * Project: test 
 * Package Name:volatiletest 
 * 
 * File Created at 2018年3月2日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd 
 * All Rights Reserved. 
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd.
 * Copying or reproduction without prior written approval is prohibited.
 * 
*/  
package cn.lomen.volatiletest;

import org.omg.Messaging.SyncScopeHelper;

/** 
 * ClassName:Singleton <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月2日 下午10:41:43 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class Singleton {
    //volatile修饰对象，防止操作系统对实例化过程中出现指令重排
    public static volatile Singleton singleton;
    /**
     * 构造函数私有，禁止外部函数实例化
     * Creates a new instance of Singleton. 
     *
     */
    private Singleton (){}
    
    public static Singleton getInstance(){
        if(singleton==null){
            synchronized (singleton) {
                if(singleton==null){
                    singleton=new Singleton(); 
                }
            }   
        }
        return singleton;
    }
}
