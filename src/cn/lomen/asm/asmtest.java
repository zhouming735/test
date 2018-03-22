package cn.lomen.asm;

import java.io.IOException;

public class asmtest {
   public static void main(String[] args) throws IllegalAccessException, IOException, InstantiationException {

       //Account acount=new Account();
       //acount.operation();
       SecureAccountGenerator s=new SecureAccountGenerator();
       Account account=s.generateSecureAccount();
       account.operation();
    }
}
