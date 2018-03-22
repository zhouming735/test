package cn.lomen.classloader;

public class Human {
    //该类写在记事本里，在用javac命令行编译成class文件，放在d盘根目录下
    private String name;

    public Human() {}

    public Human(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "I am a Human, my name is " + name;
    }
}
