package net.newstring.crux.study.plugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * TestMain
 *
 * @author lic
 * @date 2018/3/22
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        PluginManager manager = new PluginManager();;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String cmd = br.readLine();

        while(!cmd.equals("bye")){
            if(cmd.startsWith("do")){
                String pluginName = cmd.split(" ")[1];
                manager.doSome(pluginName);
            }
            if(cmd.startsWith("load")){
                String pluginName = cmd.split(" ")[1];
                manager.loadPlugin(pluginName);
            }
            if(cmd.startsWith("unload")){
                String pluginName = cmd.split(" ")[1];
                manager.unloadPlugin(pluginName);
            }
            cmd = br.readLine();
        }
    }

}
