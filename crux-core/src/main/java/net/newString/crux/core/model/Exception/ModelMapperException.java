package net.newString.crux.core.model.Exception;

/**
 *
 * Created by aaron on 7/30/2016.
 */
public class ModelMapperException extends Exception {
    private static final long serialVersionUID = -8886629192802874689L;

    private ModelMapperException() {
        super();
    }

    public ModelMapperException(String message) {
        super(message);
    }

    public ModelMapperException(String message, Throwable cause) {
        super(message, cause);
    }

    public ModelMapperException(Throwable cause) {
        super(cause);
    }
}
