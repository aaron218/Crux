package net.newString.crux.core.lang;


import java.io.*;
import java.math.BigDecimal;
import java.nio.file.NotDirectoryException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 类说明: 文件操作类
 * <p>
 * 未验证
 */
@Deprecated
public class FileUtil {
    private final static byte[] UTF_8_BOM = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};

    /**
     * 删除指定文件或目录
     *
     * @param file 需要被删除的文件
     * @return 0成功, 非0失败
     */
    public static int deleteFile(File file) {
        if (file == null) {
            return -1;
        }
        if (file.exists()) {
            if (file.isFile()) {
                if (file.delete()) {
                    return 0;
                }
            }
            if (file.isDirectory()) {
                File[] subFiles = file.listFiles();
                if (subFiles != null) {
                    for (File subFile : subFiles) {
                        deleteFile(subFile);
                    }
                }
                if (file.delete()) {
                    return 0;
                }
            }
            return -1;
        } else {
            return -1;
        }
    }

    /**
     * 文件拷贝
     */
    public static void fileCopy(File srcFile, File tarFile) throws Exception {
        FileInputStream is = null;
        FileOutputStream os = null;
        try {
            is = new FileInputStream(srcFile);
            os = new FileOutputStream(tarFile);
            // write the file to the file specified
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = is.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } finally {
            if (is != null)
                is.close();
            if (os != null)
                os.close();
        }
    }

    /**
     * 将字符串写入到文件中，
     *
     * @param filePath    文件路径+文件名
     * @param fileContent 文件内容
     * @param encoding    字符串编码格式 默认系统编码
     */
    public static void writeFileByString(String filePath, String fileContent,
                                         String encoding) {
        FileUtil.writeFileByString(filePath, fileContent, encoding, false);
    }

    /**
     * 将字符串写入到文件中，
     *
     * @param filePath    文件路径+文件名
     * @param fileContent 文件内容
     * @param encoding    字符串编码格式 默认系统编码
     * @param append      如果为true表示将fileContent中的内容添加到文件file末尾处
     */
    public static void writeFileByString(String filePath, String fileContent,
                                         String encoding, boolean append) {
        PrintWriter out = null;
        try {
            if (filePath == null || fileContent == null
                    || fileContent.length() <= 0) {
                return;
            }

            if (append) {
                File tempFile = new File(filePath);
                if (!tempFile.exists()) {
                    if(tempFile.getParentFile().mkdirs()){
                        if (!tempFile.createNewFile()) {
                            return;
                        }
                    }
                }
            } else
                createNewFile(new File(filePath));

            if (encoding == null || encoding.trim().length() <= 0) {
                out = new PrintWriter(new FileWriter(filePath));
            } else {
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(new FileOutputStream(filePath,
                                append), encoding)), true);
            }
            out.print(fileContent);
            out.close();
        } catch (Exception ignored) {
        } finally {
            if (out != null)
                out.close();
        }
    }

    public static File createFile(String filepath) throws Exception {
        File file = new File(filepath);
        if (file.exists()) {// 判断文件目录的存在
            if (file.isDirectory()) {// 判断文件的存在性
            } else {
                if (!file.createNewFile()) {
                    throw new IOException("file with path:"+filepath+" can't created");
                }
            }
        } else {
            File file2 = new File(file.getParent());
            if (!file2.mkdirs()) {
                throw new NotDirectoryException("directory can't create");
            }
            if (file.isDirectory()) {
                throw new IOException("file is directory");
            } else {
                if (!file.createNewFile()) {
                    throw new IOException("file with path:"+filepath+" can't created");
                }
            }
        }
        return file;
    }

    public static String loadAFileToString(File f) {
        InputStream is = null;
        String ret = null;
        try {
            is = new BufferedInputStream(new FileInputStream(f));
            long contentLength = f.length();
            ByteArrayOutputStream outstream = new ByteArrayOutputStream(contentLength > 0 ? (int) contentLength : 1024);
            byte[] buffer = new byte[4096];
            int len;
            while ((len = is.read(buffer)) > 0) {
                outstream.write(buffer, 0, len);
            }
            outstream.close();
            ret = new String(outstream.toByteArray(), "utf-8");
        } catch (IOException e) {
            ret = "";
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                }
            }
        }
        return ret;
    }

    public static String loadAFileToString2(File f) {
        InputStream is = null;
        String ret = "";
        if (f.exists()) {
            BufferedReader br = null;
            try {
                String line;
                is = new FileInputStream(f);
                InputStreamReader read = new InputStreamReader(is, "utf-8");
                br = new BufferedReader(read);
                while ((line = br.readLine()) != null) {
                    ret += line + "\r\n";
                }
            } catch (Exception e) {
                ret = "";
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 创建文件，如果存在，删除后，新建
     *
     * @param f
     * @throws IOException
     */
    public static void createNewFile(File f) throws IOException {
        if (f.exists()) {
            f.delete();
        }
        f.getParentFile().mkdirs();
        f.createNewFile();
    }

    public static boolean checkFileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static void checkDirExists(String dirPath) {
        File dirFile = new File(dirPath);
        if (!dirFile.isDirectory()) {
            dirFile.mkdirs();
        }
    }

    /**
     * 取得文件大小单位M
     *
     * @param f
     * @throws IOException
     */
    public static double getFileSize(File f) throws IOException {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            BigDecimal b = new BigDecimal(Integer.toString(fis.available()));
            BigDecimal s = new BigDecimal("1048576");
            return b.divide(s).doubleValue();
        } catch (Exception e) {

        } finally {
            if (fis != null)
                fis.close();
        }
        return 0;
    }

    /**
     * 判断文件是否为UTF-8格式
     *
     * @param f
     * @return
     */
    public static boolean isUTF8(File f) {
        boolean isUtf8 = false;
        try {
            FileInputStream fis = new FileInputStream(f);
            byte[] bom = new byte[3];
            fis.read(bom);
            fis.close();
            if (null != bom && bom.length > 2
                    && bom[0] == UTF_8_BOM[0]
                    && bom[1] == UTF_8_BOM[1]
                    && bom[2] == UTF_8_BOM[2]) {
                isUtf8 = true;
            }
        } catch (Exception e) {
            return false;
        }
        return isUtf8;
    }

    /**
     * 把一个文件A追加到另一个文件B中，
     *
     * @param sorceFile 文件A
     * @param despFile  文件B
     */
    public static void writeFileByFileWithUtf(File sorceFile, File despFile) {
        long sourceLength = sorceFile.length();
        if (sourceLength == 0) {
            return;
        }

        InputStream is = null;
        FileOutputStream fos = null;

        byte[] bytes = new byte[1024 * 4];

        int numRead = 0;
        try {
            double fileSize = FileUtil.getFileSize(despFile);

            is = new FileInputStream(sorceFile);
            fos = new FileOutputStream(despFile, true);

            if (fileSize == 0) {
                byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
                fos.write(bom);
            }

            while ((numRead = is.read(bytes, 0, 1024 * 4)) >= 0) {
                if (numRead < 1024 * 4) {
                    String str = new String(bytes);
                    String pattern = "\\x00+$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(str);
                    str = m.replaceAll("");
                    bytes = str.getBytes();
                }

                fos.write(bytes, 0, bytes.length);
                bytes = new byte[1024 * 4];
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 将字符串content的内容追加到文件filePath的最后，如果文件不存在，先创建新文件
     * 追加文件：在构造FileOutputStream时，把第二个参数设为true
     * 编码：UTF-8
     *
     * @param filePath 文件路径+文件名
     * @param content  内容
     */
    public static boolean writeFileAppend(String filePath, String content) {
        boolean res = true;
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(filePath, true), "UTF-8"));
            out.write(content);
        } catch (Exception e) {
            res = false;
        }
        try {
            out.close();
        } catch (IOException e) {
        }
        return res;
    }

    /**
     * 判断参数是不是文件头标识
     *
     * @param content
     * @return
     */
    public static boolean isFileHead(String content) {
        byte[] b = content.trim().getBytes();
        if (b.length != 3) return false;
        if (b[0] != -17) return false;
        if (b[1] != -69) return false;
        if (b[2] != -65) return false;
        return true;
    }


    public static void appendTextToFile(String fileName, String text)
            throws IOException {
        if (text.length() == 0) {
            return;
        }
        File despFile = new File(fileName);
        if (!checkFileExists(fileName)) {
            File f = new File(fileName);
            createNewFile(f);
        }
        InputStream is = null;
        FileOutputStream fos = null;

        byte[] bytes = new byte[1024 * 4];

        int numRead = 0;
        try {
            double fileSize = FileUtil.getFileSize(despFile);

            is = new ByteArrayInputStream(text.getBytes());
            fos = new FileOutputStream(despFile, true);

            if (fileSize == 0) {
                byte[] bom = new byte[]{(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
                fos.write(bom);
            }

            while ((numRead = is.read(bytes, 0, 1024 * 4)) >= 0) {
                if (numRead < 1024 * 4) {
                    String str = new String(bytes);
                    String pattern = "\\x00+$";
                    Pattern p = Pattern.compile(pattern);
                    Matcher m = p.matcher(str);
                    str = m.replaceAll("");
                    bytes = str.getBytes();
                }

                fos.write(bytes, 0, bytes.length);
                bytes = new byte[1024 * 4];
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String loadFileToStr(File f) throws FileNotFoundException {
        InputStream is = null;
        String ret = "";
        if (f.exists()) {
            BufferedReader br = null;
            try {
                String line;
                is = new FileInputStream(f);
                InputStreamReader read = new InputStreamReader(is, "utf-8");
                br = new BufferedReader(read);
                while ((line = br.readLine()) != null) {
                    ret += line + "\r\n";
                }
            } catch (Exception e) {
                ret = "";
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return ret;
    }

    /**
     * 将字符串写入到文件中，
     *
     * @param filePath    文件路径+文件名
     * @param fileContent 文件内容
     * @param encoding    字符串编码格式 默认系统编码
     * @param append      如果为true表示将fileContent中的内容添加到文件file末尾处
     * @throws Exception
     * @throws Exception
     * @author duguocheng
     */
    public static void writeFileToString(String filePath, String fileContent, String encoding, boolean append) throws Exception {
        PrintWriter out = null;
        try {
            if (filePath == null || fileContent == null
                    || fileContent.length() <= 0) {
                return;
            }

            File tempFile = new File(filePath);
            if (!tempFile.exists()) {
                tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
            }

            if (encoding == null || encoding.trim().length() <= 0) {
                out = new PrintWriter(new FileWriter(filePath));
            } else {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, append), encoding)), true);
            }
            out.print(fileContent);
            out.close();
        } catch (Exception e) {
            throw e;
        } finally {
            if (out != null)
                out.close();
        }
    }

    public static boolean createFolder(String path) {
        try {
            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception e) {
            System.out.println("error occur when creating folder");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean newFile(String fullName) {
        try {
            File file = new File(fullName);
            if (!file.exists()) {
                if (!file.getParentFile().exists())
                    file.getParentFile().mkdirs();
                file.createNewFile();
            }
        } catch (Exception e) {
            System.out.println("error occur when creating folder");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public static boolean newFile(String fullName, String text) {

        try {
            newFile(fullName);
            FileWriter out = new FileWriter(fullName, false);
            out.write(text);
            out.close();
        } catch (Exception e) {
            System.out.println("error occur when creating file");
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static boolean appendFile(String fullName, String text) {

        try {
            newFile(fullName);
            FileWriter out = new FileWriter(fullName, true);
            out.write(text);
            out.close();
        } catch (Exception e) {
            System.out.println("error occur when creating file");
            e.printStackTrace();
            return false;
        }
        return true;

    }

    public static boolean delFile(String fullName) {
        try {
            File file = new File(fullName);
            if (file.exists()) {
                return file.delete();
            } else {
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void delFileOnExit(String fullName) {
        try {
            File file = new File(fullName);
            if (file.exists()) {
                file.deleteOnExit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean rename(String fileName, String distFile) {
        synchronized (fileName) {
            File oldFile = new File(fileName);
            if (oldFile.exists()) {
                try {
                    oldFile.renameTo(new File(distFile));
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } else
                return true;
        }
    }

    public static boolean removeFolder(String pathFolder,
                                       boolean recursiveRemove) {
        File folder = new File(pathFolder);
        if (folder.isDirectory()) {
            return removeFolder(folder, recursiveRemove);
        }
        return false;
    }

    public static boolean removeFolder(File folder, boolean removeRecursivly) {
        if (removeRecursivly) {
            for (File current : folder.listFiles()) {
                if (current.isDirectory()) {
                    removeFolder(current, true);
                } else {
                    current.delete();
                }
            }
        }
        return folder.delete();
    }

    public static boolean delFolder(String path) {
        return removeFolder(path, true);
    }

    public static boolean copyFile(String source, String target) {
        boolean isSuc = false;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(source);
            newFile(target);
            if (oldfile.exists()) {
                in = new FileInputStream(source);
                out = new FileOutputStream(target, false);// no append,
                // overwrite old.
                byte[] buffer = new byte[4096];
                while ((byteread = in.read(buffer)) != -1) {
                    bytesum += byteread;
                    out.write(buffer, 0, byteread);
                }
                isSuc = true;
            } else {
                System.err.println("File " + source + " not exists");
            }

        } catch (Exception e) {
            System.err.println("Exception occur when copying a file");
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
        return isSuc;
    }

    public static boolean copyFile(InputStream inputStream, String target) {
        boolean isSuc = false;
        FileOutputStream out = null;
        try {
            int bytesum = 0;
            int byteread = 0;
            newFile(target);
            out = new FileOutputStream(target, false);
            byte[] buffer = new byte[4096];
            while ((byteread = inputStream.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
            }
            isSuc = true;
        } catch (Exception e) {
            System.err.println("Exception occur when copying a file");
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
            }
            try {
                if (out != null)
                    out.close();
            } catch (IOException e) {
            }
        }
        return isSuc;
    }

    public static boolean copyFileNoOverWrite(String oldfile,
                                              String newfilepath, String newfile) {
        return copyFileNoOverWrite(oldfile, newfilepath + newfile);
    }

    public static boolean copyFileNoOverWrite(String oldfile, String newfile) {
        if (FileUtil.checkFileExists(oldfile)
                && !FileUtil.checkFileExists(newfile)) { // 文件存在时
            return copyFile(oldfile, newfile);
        }
        return true;
    }

    public static void copyFolder(String source, String target) {

        if (target.indexOf(source) != -1) {
            System.err.println("target is source's sub directory");
            return;
        }

        try {
            File dir = new File(source);
            File[] listFiles = dir.listFiles();
            File fileSource = null;
            for (int i = 0; i < listFiles.length; i++) {
                fileSource = listFiles[i];
                if (fileSource.isDirectory()) {
                    createFolder(target + "/" + fileSource.getName());
                    copyFolder(fileSource.getAbsolutePath() + "/", target
                            + "/" + fileSource.getName() + "/");
                } else {
                    copyFile(fileSource.getAbsolutePath(), target + "/"
                            + fileSource.getName());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean moveFile(String oldPath, String newFolderPath,
                                   String fileName) {
        return copyFile(oldPath, newFolderPath + fileName) && delFile(oldPath);
    }

    public static boolean moveFile(String oldPath, String newPath) {
        return copyFile(oldPath, newPath) && delFile(oldPath);
    }

    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    public static String getExtName(String filename) {
        int index = filename.lastIndexOf('.');
        if (index == -1) {
            return "";
        } else {
            return filename.substring(index + 1);
        }
    }


    class LineReader {
        public LineReader(InputStream inStream) {
            this.inStream = inStream;
            inByteBuf = new byte[8192];
        }

        public LineReader(Reader reader) {
            this.reader = reader;
            inCharBuf = new char[8192];
        }

        byte[] inByteBuf;
        char[] inCharBuf;
        char[] lineBuf = new char[1024];
        int inLimit = 0;
        int inOff = 0;
        InputStream inStream;
        Reader reader;

        int readLine() throws IOException {
            int len = 0;
            char c = 0;

            boolean skipWhiteSpace = true;
            boolean isCommentLine = false;
            boolean isNewLine = true;
            boolean appendedLineBegin = false;
            boolean precedingBackslash = false;
            boolean skipLF = false;

            while (true) {
                if (inOff >= inLimit) {
                    inLimit = (inStream==null)?reader.read(inCharBuf)
                            :inStream.read(inByteBuf);
                    inOff = 0;
                    if (inLimit <= 0) {
                        if (len == 0 || isCommentLine) {
                            return -1;
                        }
                        if (precedingBackslash) {
                            len--;
                        }
                        return len;
                    }
                }
                if (inStream != null) {
                    //The line below is equivalent to calling a
                    //ISO8859-1 decoder.
                    c = (char) (0xff & inByteBuf[inOff++]);
                } else {
                    c = inCharBuf[inOff++];
                }
                if (skipLF) {
                    skipLF = false;
                    if (c == '\n') {
                        continue;
                    }
                }
                if (skipWhiteSpace) {
                    if (c == ' ' || c == '\t' || c == '\f') {
                        continue;
                    }
                    if (!appendedLineBegin && (c == '\r' || c == '\n')) {
                        continue;
                    }
                    skipWhiteSpace = false;
                    appendedLineBegin = false;
                }
                if (isNewLine) {
                    isNewLine = false;
                    if (c == '#' || c == '!') {
                        isCommentLine = true;
                        continue;
                    }
                }

                if (c != '\n' && c != '\r') {
                    lineBuf[len++] = c;
                    if (len == lineBuf.length) {
                        int newLength = lineBuf.length * 2;
                        if (newLength < 0) {
                            newLength = Integer.MAX_VALUE;
                        }
                        char[] buf = new char[newLength];
                        System.arraycopy(lineBuf, 0, buf, 0, lineBuf.length);
                        lineBuf = buf;
                    }
                    //flip the preceding backslash flag
                    if (c == '\\') {
                        precedingBackslash = !precedingBackslash;
                    } else {
                        precedingBackslash = false;
                    }
                }
                else {
                    // reached EOL
                    if (isCommentLine || len == 0) {
                        isCommentLine = false;
                        isNewLine = true;
                        skipWhiteSpace = true;
                        len = 0;
                        continue;
                    }
                    if (inOff >= inLimit) {
                        inLimit = (inStream==null)
                                ?reader.read(inCharBuf)
                                :inStream.read(inByteBuf);
                        inOff = 0;
                        if (inLimit <= 0) {
                            if (precedingBackslash) {
                                len--;
                            }
                            return len;
                        }
                    }
                    if (precedingBackslash) {
                        len -= 1;
                        //skip the leading whitespace characters in following line
                        skipWhiteSpace = true;
                        appendedLineBegin = true;
                        precedingBackslash = false;
                        if (c == '\r') {
                            skipLF = true;
                        }
                    } else {
                        return len;
                    }
                }
            }
        }
    }

}
