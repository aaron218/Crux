package net.newString.crux.core.lang;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * 针对{@link Logger}的一些操作 用于提供简易的Log日志输出
 * @author lic
 */
@Deprecated
public class LoggingUtil {
    Logger logger = Logger.getLogger("");

    public void test(){
        logger.info("");
    }




    public static class Log4jLikedFormatter extends Formatter{

        @Override
        public String format(LogRecord record) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(record.getLevel()).append(":")
                    .append(record.getThreadID());
            return stringBuilder.toString();
        }
    }
}
