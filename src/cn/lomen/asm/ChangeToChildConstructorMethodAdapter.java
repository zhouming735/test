package cn.lomen.asm;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class ChangeToChildConstructorMethodAdapter extends MethodVisitor {
    private String superClassName;

    public ChangeToChildConstructorMethodAdapter(MethodVisitor mv,
                                          String superClassName) {
        super(Opcodes.ASM6, mv);
        this.superClassName = superClassName;
    }



    public void visitMethodInsn(int opcode, String owner, String name,
                                String desc,boolean ffl) {
        // 调用父类的构造函数时
        if (opcode == Opcodes.INVOKESPECIAL && name.equals("<init>")) {
            owner = superClassName;
        }
        super.visitMethodInsn(opcode, owner, name, desc,false);// 改写父类为 superClassName
    }

}
