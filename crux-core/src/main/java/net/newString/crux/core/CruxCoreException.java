package net.newString.crux.core;

/**
 * CruxCore包中联合处理的Exception
 * <br>只用于封装其他的Exception 只可用于简化过多且非重要的异常（少量异常或者异常类型对处理方式很重要的，不建议使用此封装）。
 *
 * @author lic
 */
@stable
public class CruxCoreException extends Exception {
    private static final long serialVersionUID = 8621134864784737570L;

    @stable
    private CruxCoreException() {
    }

    @stable
    private CruxCoreException(String message) {
        super(message);
    }

    @stable
    public CruxCoreException(String message, Throwable cause) {
        super("CruxCoreException Packaged From :" + cause.getClass().getCanonicalName() + "\n\t"
                        + message,
                cause);
    }

    @stable
    public CruxCoreException(Throwable cause) {
        super("CruxCoreException Packaged From :" + cause.getClass().getCanonicalName() + "\n\t"
                        + cause.getMessage(),
                cause);
    }
}
