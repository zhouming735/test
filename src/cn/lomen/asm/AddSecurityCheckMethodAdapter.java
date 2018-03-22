package cn.lomen.asm;


import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

public class AddSecurityCheckMethodAdapter extends MethodVisitor {
    public AddSecurityCheckMethodAdapter(MethodVisitor mv) {
        super(Opcodes.ASM6,mv);
    }

    public void visitCode() {
        //路径要注意，要跟Account的包路径一致
        visitMethodInsn(Opcodes.INVOKESTATIC, "cn/lomen/asm/SecurityChecker",
                "checkSecurity", "()V",false);


    }


}
