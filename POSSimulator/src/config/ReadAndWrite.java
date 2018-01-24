/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import static java.lang.ClassLoader.getSystemResource;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 *
 * @author vip
 */
public class ReadAndWrite {

    private static Properties props = new Properties();

    private static String mid = null;
    private static String tid = null;
    private static String url = null;

    private static String batchNo;
    private static String systrace;

    public Map<String, String> read() {
        
        InputStream in = null;
//        InputStream ips = ClassLoader.getSystemResourceAsStream("DBConfig.properties");
        Map<String, String> map = new HashMap();
        try {
            in = new FileInputStream("DBConfig.properties");
            props.load(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        mid = props.getProperty("MID");
        tid = props.getProperty("TID");
        url = props.getProperty("URL");
        
        batchNo = props.getProperty("BATCHNO");
        systrace = props.getProperty("SYSTRACE");

        map.put("orgUrl", props.getProperty("ORGURL"));
         map.put("orgId", props.getProperty("ORGID"));
        
        map.put("pay", props.getProperty("PAY"));
        map.put("query", props.getProperty("QUERY"));
        map.put("refund", props.getProperty("REFUND"));
        map.put("version", props.getProperty("VERSION"));
        map.put("shiftNo", props.getProperty("SHIFTNO"));
        map.put("currency", props.getProperty("CURRENCY"));
        map.put("mid", mid);
        map.put("tid", tid);
        map.put("url", url);
        map.put("batchNo", batchNo);
        map.put("systrace", systrace);
        try {
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public void write(String batchNo, String systrace) throws IOException {
        props = new Properties();
  
        File file = new File("DBConfig.properties");
        InputStream in = null;
        try {
            in = new FileInputStream(file);
            props.load(in);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        OutputStream out = new FileOutputStream(file);

        
        props.put("BATCHNO", batchNo);
        props.put("SYSTRACE", systrace);
        

        try {
            props.store(out, "");
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            in.close();
            out.close();
        }

    }

    
    public static  boolean print(String filePath, String code) {  
        try {  
            File tofile = new File(filePath);  
            FileWriter fw = new FileWriter(tofile, true);  
            BufferedWriter bw = new BufferedWriter(fw);  
            PrintWriter pw = new PrintWriter(bw);  
//            GetNowDate getnowdata=new GetNowDate();//Java-取得服务器当前的各种具体时间  
//            System.out.println(getnowdata.getDate()+":"+code);  
//            pw.println(getnowdata.getDate()+":"+code);  
            pw.println(code);  
            pw.close();  
            bw.close();  
            fw.close();  
            return true;  
        } catch (IOException e) {  
            return false;  
        }  
    }  
}
