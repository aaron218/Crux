package net.newString.crux.extend.Files;

import java.io.File;

/**
 */
public class MavenCleaner {

    public static void main(String[] args) {
        File dir=new File("C:\\Users\\Aaron\\.m2\\repository");
        System.out.println("使用list方法获取所有文件名");

        clean(dir);

    }


    /**
     * 文件递归删除方法 返回 操作之后是否为空目录
     * @param file
     * @return
     */
    private static boolean clean(File file){
        if(file.isDirectory()){
            File[] inner = file.listFiles();
            if(inner==null){
                System.out.println("null dir deleted " + file.getPath()+"  "+file.getName());
                file.deleteOnExit();

                return false;

            }
            if(inner.length==0){
                System.out.println("empty dir deleted " + file.getPath()+"  "+file.getName());
                file.deleteOnExit();
                return false;
            }
            for(File in:inner){
                clean(in);
            }
            if(file.listFiles().length==0){
                System.out.println(file.getPath() + "Path  is empty after clean ...");
                file.deleteOnExit();
                return true;
            }

        }
        if(file.isFile()){
            if(file.getName().contains("astUpdated")){
                System.out.println("deleted LastUpdated file "+ file.getPath() +"  "+ file.getName());
                file.deleteOnExit();
            }
        }

        return true;
    }


    /**
     * 判断是否是最后一层目录
     * @param file 文件对象(目录)
     * @return
     */
    public static boolean isLeafDirectory(final File file){
        if(file ==null || !file.isDirectory()){
            return false;
        }

        if(file.listFiles()==null || file.listFiles().length==0){
            return true;
        }

        for(File file1:file.listFiles()){
            if(file1.isDirectory()){
                return false;
            }
        }
        return true;
    }


    /**
     * 是否是空的maven目录 空的maven目录：不包括pom jar 文件的目录
     * @param file
     * @return
     */
    public static boolean isEmptyMavenDirectory(File file){
        if(file==null || !file.isDirectory()){
            return false;
        }
        return false;
    }

    private static  void showAll(File file){


    }
}
