/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

}
