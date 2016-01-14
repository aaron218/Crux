package net.newString.crux.CDH.hbase.CURDTest;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.junit.Test;

import java.io.IOException;


/**
 * Created by aaron on 1/11/2016.
 * 连接测试
 */
public class ConnTest {

    public Configuration hbaseConf  =HBaseConfiguration.create();
    private static String tableName = "lic_test";
    @Test
    public void testConn() throws IOException {
        boolean isTableExists = false;
        if (hbaseConf != null) {
            HBaseAdmin hAdmin = new HBaseAdmin(hbaseConf);
            isTableExists = hAdmin.tableExists(tableName);
            if (isTableExists){
                System.out.println("连接成功，数据表"+tableName+"已存在");
            }else{
                // 新建一个students表的描述
                String[] columnFamilys = { "info", "course" };
                HTableDescriptor tableDesc = new HTableDescriptor(tableName);
                // 在描述里添加列族
                for (String columnFamily : columnFamilys) {
                    tableDesc.addFamily(new HColumnDescriptor(columnFamily));
                }
                // 根据配置好的描述建表
                hAdmin.createTable(tableDesc);
                System.out.println("连接成功，创建表 "+tableName+" 成功!");
            }
        }
    }

    @Test
    //新版（1.0）驱动连接方式链接
    public void testNewConn(){}
}
