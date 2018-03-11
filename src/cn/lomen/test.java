package cn.lomen;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;


import com.google.gson.JsonObject;


/** 
 * ClassName:test <br/> 
 * Function: TODO ADD FUNCTION. <br/> 
 * Reason:   TODO ADD REASON. <br/> 
 * Date:     2017年9月28日 上午11:00:21 <br/> 
 * @author   <a href="mailto:hujian@eastcom-sw.com">zhoum</a><br>
 * @version        
 */
public class test {

    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        
        int i = 128;  
        Integer i2 = 128;  
        Integer i3 = new Integer(128);  
        System.out.println(i == i2); //true, Integer会自动拆箱为int，所以为true  
        System.out.println(i == i3); //true，理由同上  
        Integer i4 = 127;//编译时被翻译成：Integer i4 = Integer.valueOf(127);  
        Integer i5 = 127;  
        System.out.println(i4 == i5);//true  
        Integer i6 = 128;  
        Integer i7 = 128;  
        System.out.println(i6 == i7);//false  
        Integer i8 = new Integer(127);  
        System.out.println(i5 == i8); //false, i8是new出来的新对象  
        Integer i9 = new Integer(128);  
        Integer i10 = new Integer(123);  
        System.out.println(i9 == i10);  //false， new出来的对象，引用地址都不一样 
        
        Map<String,String> map=new HashMap<>();
        map.put("total", "0");
        map.put("url", "www.baidu.com");
        System.out.println(map);
        
        
        /*String s="dongxin2017-09-25NGCCgetinfo";
        String dd=MD5.MD5_32bit(s);
        boolean isOK=dd.toUpperCase().equals("A5917FA31E6CD0784D8176FA15A9CB8B");
        
        System.out.println(dd);
        System.out.println(isOK);*/
        
        /*System.out.println("开始请求SF...");
        WriteLog("开始请求SF...\n");
        String result2=getFaultBySf("gzadmin");
        System.out.println(result2);
        System.out.println("SF...END");
        WriteLog(result2+"\n");
        WriteLog("SF...END\n");
        
        System.out.println("开始请求EOMS...");
        WriteLog("开始请求EOMS...\n");
        String result=getFaultsByEoms("linshunfu","2");
        System.out.println(result);
        System.out.println("EOMS...END");
        WriteLog(result+"\n");
        WriteLog("EOMS...END\n");*/
        
        System.out.println("开始请求ZY...");
        WriteLog("开始请求ZY...\n");
        String loginID="linshunfu";
        if(args.length>0)
            loginID=args[0];
        String result3=getFaultsByZy(loginID);
        System.out.println(result3);
        System.out.println("ZY...END");
        WriteLog(result3+"\n");
        WriteLog("ZY...END\n");
        
        /*for(int i=0;i<5;i++){
            getFaultsByEoms("linshunfu",String.valueOf(i));
            if(i>5)
                i=0;
        }*/
        
    }
    
    @SuppressWarnings("finally")
    public static String test() throws IOException{
        String cc="1234";
        try{
            String s="1ss";
            
            cc=s;
            int a=Integer.parseInt(s);
            //return "zhengchang";
        }
        catch(Exception ex){
            WriteLog(ex.getMessage());
            //ex.printStackTrace();
            cc="yich";
            return "yichangle";
        }
        finally {
            return cc;
        }
    }
    
    public static void WriteLog(String Content) throws IOException{  
        //先创建目录  
        String path = System.getProperty("user.dir"); 
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        String filename =df.format(new Date())+".log" ;  
        File dir = new File(path);  
        if(!dir.exists()){  
            dir.mkdir();  
        }  
          
        //创建文件输出流  
        File file = new File(path,filename);  
        if(!file.exists())
            file.createNewFile();
        try {  
            FileOutputStream fos = new FileOutputStream(file,true);  
            fos.write(Content.getBytes());  
            fos.flush();  
            fos.close();  
        } catch (Exception e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
    }  
    
    
    public static String getFaultsByZy(String loginId) throws IOException {
        StringBuffer resultBuff = new StringBuffer();
        try {
            String endPoint = "http://super100.gmcc.net:8006/CaseBoardWebApi/province/getZdAndYzCaseCount";
               
            URL apiUrl = new URL(endPoint);
            HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
            conn.setUseCaches(false);
            
            String authString =  "eastcom_mastercom:eastmaster123#@!";
            String authStringEnc =  DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            
          
            // 设置请求头
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            //conn.setRequestProperty("SOAPAction", "");
            //conn.setRequestProperty("loginUid", "dwyangziwen");
 
            
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            
            //conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            //String params = "loginUid=" + URLEncoder.encode(loginId, "utf-8"); 
            JsonObject json=new JsonObject();
            json.addProperty("loginUid", loginId);
            
            os.write(json.toString().getBytes());
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                resultBuff.append(new String(b, 0, len, "UTF-8"));
            }
            is.close();
            os.close();
        } catch (Exception ex) {
            WriteLog("EOMS接口请求异常:" + ex.getMessage());
            ex.printStackTrace();
        }

        return resultBuff.toString();
    }

    public static String getFaultsByEoms(String loginId,String type) throws IOException {
        StringBuffer resultBuff = new StringBuffer();
        try {
            String endPoint = "http://10.201.37.11:7601/adapter/services/OuterAdapter?wsdl";
            //String endPoint ="";
            String operation="4OYD_PENDING_EOMS";
            String systemName="4OYD";
            String xmlData=" <![CDATA[<?xml version=\"1.0\" encoding=\"GB2312\"?>"
                          + "<xmlData>"                       
                          + "  <eomsId>"+loginId+"</eomsId>"
                          + "  <processName>"+type+"</processName>"
                          + "</xmlData>]]>";
            
            URL wsUrl = new URL(endPoint);
            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setUseCaches(false);
            
            String authString =  "emip:12345678";
            String authStringEnc =  DatatypeConverter.printBase64Binary(authString.getBytes("UTF-8"));
            conn.setRequestProperty("Authorization", "Basic " + authStringEnc);
            
          
            // 设置请求头
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setRequestProperty("SOAPAction", "");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            // 请求体
            String soap = "<soapenv:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:adap=\"http://adapter.nsm.huawei.com\">"
                          +" <soapenv:Header/>"
                          +"  <soapenv:Body>"
                          +"     <adap:process soapenv:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
                          +"        <operation xsi:type=\"xsd:string\">"+operation+"</operation>"
                          +"        <systemName xsi:type=\"xsd:string\">"+systemName+"</systemName>"
                          +"        <xmlData xsi:type=\"xsd:string\">"+xmlData+"</xmlData>"
                          +"     </adap:process>"
                          +"  </soapenv:Body>"
                          +"</soapenv:Envelope>";
            os.write(soap.getBytes());
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                resultBuff.append(new String(b, 0, len, "UTF-8"));
            }
            is.close();
            os.close();
        } catch (Exception ex) {
            WriteLog("EOMS接口请求异常:" + ex.getMessage());
            ex.printStackTrace();
        }

        return resultBuff.toString();
    }

    public static String getFaultBySf(String loginId) throws IOException {
       
        StringBuffer resultBuff = new StringBuffer();
        try {
            String endPoint = "http://10.201.39.187:7001/mobilesg/services/GetWorkListService?wsdl";
            URL wsUrl = new URL(endPoint);
            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
            conn.setUseCaches(false);
            // 设置请求头
            conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
            conn.setRequestProperty("SOAPAction", "");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setConnectTimeout(10000);
            conn.setRequestMethod("POST");
            conn.connect();
            OutputStream os = conn.getOutputStream();
            // 请求体
            String soap = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.rc.infores.gpdi.com\">"
                    + "<soapenv:Body><ser:getWorkList><ser:loginName>" + loginId
                    + "</ser:loginName></ser:getWorkList></soapenv:Body></soapenv:Envelope>";
            
            os.write(soap.getBytes());
            InputStream is = conn.getInputStream();
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = is.read(b)) != -1) {
                resultBuff.append(new String(b, 0, len, "UTF-8"));
            }
            is.close();
            os.close();
        } catch (Exception ex) {
            WriteLog("Sf接口请求异常:" + ex.getMessage());
            ex.printStackTrace();
        }

        return resultBuff.toString();
       
    }

}
