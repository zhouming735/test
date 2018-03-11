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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import sun.misc.Unsafe;



/** 
 * ClassName:test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2018年3月2日 下午3:08:16 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        // TODO Auto-generated method stub
        //Dog dog = new Dog();
        //Class clazz = dog.getClass();
        //Class clazz = Dog.class;
        try {
            Class clazz = Class.forName("reflect.Dog",false,Dog.class.getClassLoader());
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                System.out.println(field.getName());
            }
          
            /*
          Object invokeTest = clazz.newInstance();
          //动态构造InvokeTest类的add(int num1, int num2)方法，标记为addMethod的Method对象
          Method s = clazz.getMethod("getDogColor", new Class[]{String.class});
          //动态构造的Method对象invoke委托动态构造的InvokeTest对象，执行对应形参的add方法
          Object result = s.invoke(invokeTest, new Object[]{"green"});
          System.out.println(result);
         */
          
          Class class_dog = Dog.class;
          Constructor constructor = class_dog.getConstructor(String.class, int.class);
          constructor.newInstance("Tom", 10);
            
            Method[] methods=clazz.getMethods();
            for (Method method : methods) {
                System.out.println(method);
                if(method.getName().equals("getDogColor")){
                   Object o= method.invoke(clazz.newInstance(), "red");
                   System.out.println(o);
                    break;
                }
            }
            
            /*黑魔法类**/
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            Unsafe unsafe = (Unsafe) f.get(null);
           
            
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

}
