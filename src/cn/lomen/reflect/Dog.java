/** 
 * Project: test 
 * Package Name:reflect 
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
package cn.lomen.reflect;  
/** 
 * ClassName:Dog <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月2日 下午3:07:28 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class Dog {
    private String name;
    private int age;
    public String color;
    static {
        System.out.println("Dog is load");
    }
    
    public  String getDogColor(String color){
        return "My dog is "+color;
    }
    
    
    public Dog(){
        
    }
    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
