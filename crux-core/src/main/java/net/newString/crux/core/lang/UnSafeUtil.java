package net.newString.crux.core.lang;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by aaron on 7/9/2015.
 * 不安全的操作工具，主要是使用了Java自身的UnSafe类以及方法 实现一些可能不安全的特殊操作，所有的操作均有info级别的日志
 * <p>
 * 一般不建议使用此类中的方法，但是对于某些特殊需要，UnSafe方法确实可以提升很多的效率
 */
@Deprecated
public class UnSafeUtil {
    Log log = LogFactory.getLog(UnSafeUtil.class);
}
