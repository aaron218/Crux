package net.newString.crux.core.tools.model.Exception;

/**
 * 封装Model 包中的数据产生的异常，包括序列化、反序列化、数据载入、数据转换等，
 * 都可能会抛出此异常
 * Created by aaron on 8/1/2016.
 */
public class ModelConvertException extends Exception {
    private static final long serialVersionUID = 6761788403243876665L;


    public ModelConvertException() {
    }

    public ModelConvertException(String message) {
        super(message);
    }

    public ModelConvertException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelConvertException(Throwable cause) {
        super(cause);
    }
}
