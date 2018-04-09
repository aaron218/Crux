package net.newstring.crux.study;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * ProcessorTest
 *
 * @author lic
 * @date 2018/3/19
 */
public class ProcessorTest {

    public static void main(String[] args) {
        ProcessBuilder processBuilder = new ProcessBuilder("ping","-n","100","127.0.0.1");
        try {
            Process process = processBuilder.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(),"GBK"));
            String readLine;
            while (null != (readLine = br.readLine())) {
                System.out.println(readLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
