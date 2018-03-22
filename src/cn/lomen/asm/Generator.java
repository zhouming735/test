package cn.lomen.asm;



import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;

import java.io.File;
import java.io.FileOutputStream;

public class Generator  {
    public static void main(String[] args) throws Exception {
        ClassReader cr = new ClassReader("cn/lomen/asm/Account");
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        ClassVisitor classAdapter = new AddSecurityCheckClassAdapter(cw);
        cr.accept(classAdapter, ClassReader.SKIP_DEBUG);
        byte[] data = cw.toByteArray();
        File file = new File("Account.class");
        FileOutputStream fout = new FileOutputStream(file);
        fout.write(data);
        fout.close();


    }
}
