package cn.lomen.classloader;

public class test {
   public static void main(String[] args) throws IllegalAccessException, InstantiationException {
       MyClassLoader mcl = new MyClassLoader();
       Class<?> clazz = null;
       try {
           clazz = Class.forName("cn.lomen.classloader.Human", true, mcl);
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       Object obj = clazz.newInstance();

       System.out.println(obj);
       System.out.println(obj.getClass().getClassLoader());//打印出我们的自定义类加载器
    }

}
