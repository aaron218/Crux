package net.newString.crux.core.filter;

/**
 * Created on 2016/8/3 17:43.
 *
 * @author lic
 */
public abstract class BaseFilter implements Filter {
    private static final long serialVersionUID = 1856895903300673609L;
    private boolean enable = true;


    @Override
    public void setEnable() {
        enable = true;
    }

    @Override
    public void setDisable() {
        enable = false;
    }

    @Override
    public boolean isEnable() {
        return enable;
    }

}
