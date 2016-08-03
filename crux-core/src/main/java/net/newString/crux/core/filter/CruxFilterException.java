package net.newString.crux.core.filter;

/**
 * Created on 2016/8/3 18:29.
 *
 * @author lic
 */
public class CruxFilterException extends Exception {
    private static final long serialVersionUID = 7634127061544923375L;

    private CruxFilterException() {
        super();
    }

    public CruxFilterException(String message) {
        super(message);
    }

    public CruxFilterException(String message, Throwable cause) {
        super(message, cause);
    }

    public CruxFilterException(Throwable cause) {
        super(cause);
    }
}
