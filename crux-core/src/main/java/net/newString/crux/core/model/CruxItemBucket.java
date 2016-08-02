package net.newString.crux.core.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created on 2016/7/22 11:06.
 *
 * @author lic
 */
public class CruxItemBucket extends HashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = -1655165845615003943L;


    @Override
    public Object put(String key, Object value) {
        if (value instanceof CruxItemBucket) {
            try {
                throw new Exception("net.newString.crux.core.model.CruxItemBucket can't nested , put action ignored");
            } catch (Exception e) {
                //e.toString();
            }
        }
        return super.put(key, value);
    }


    @Override
    public void putAll(Map<? extends String, ?> m) {
        if (m == null) {
            return;
        }
        for (Map.Entry entry : m.entrySet()) {
            if (entry.getValue() instanceof CruxItemBucket) {
                try {
                    throw new Exception("net.newString.crux.core.model.CruxItemBucket can't nested, putAll action ignored..");
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }
        super.putAll(m);
    }
}
