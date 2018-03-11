/**
 * Project: test Package Name:unsafe
 * 
 * File Created at 2018年3月6日
 *
 * Copyright (c) 2018, Eastcom Technologies Co. Ltd All Rights Reserved.
 *
 * This file contains proprietary information of Eastcom Technologies Co. Ltd. Copying or
 * reproduction without prior written approval is prohibited.
 * 
 */
package cn.lomen.unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import sun.misc.Unsafe;

/**
 * ClassName:UnsafeDemo <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2018年3月6日 上午9:33:14 <br/>
 * 
 * @author <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version
 */
public class UnsafeDemo {

    public static void main(String[] args) throws NoSuchFieldException, SecurityException,
            IllegalArgumentException, IllegalAccessException, InstantiationException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe"); // Internal reference
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);

        
        // This creates an instance of player class without any initialization
        //通过allocateInstance()方法，你可以创建一个类的实例，但是却不需要调用它的构造函数、初使化代码、各种JVM安全检查以及其它的一些底层的东西。
        //即使构造函数是私有，我们也可以通过这个方法创建它的实例
        Player p = (Player) unsafe.allocateInstance(Player.class);
        System.out.println(p.getAge()); // Print 0

        //p.setAge(45); // Let's now set age 45 to un-initialized object
        //System.out.println(p.getAge()); // Print 45
        
        //Player p=new Player();
        //System.out.println(p.getAge());
        
    }
}

/*
class Player {
    static{
        System.out.println("初始化静态代码");
    }
    
    private int age = 12;

    private Player() {
        this.age = 50;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
*/
