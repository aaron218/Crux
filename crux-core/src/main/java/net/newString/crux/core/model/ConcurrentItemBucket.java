package net.newString.crux.core.model;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created on 2016/7/22 10:52.
 *
 * @author lic
 */
public class ConcurrentItemBucket extends ConcurrentHashMap<String, Object> implements Serializable {
    private static final long serialVersionUID = -1655165845615003943L;

    private String BucketID;


    @Override
    public Object put(String key, Object value) {
        if (value instanceof ConcurrentItemBucket || value instanceof CruxItemBucket) {
            try {
                throw new Exception("net.newString.crux.core.model.ConcurrentItemBucket can't nested , put action ignored");
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
            if (entry.getValue() instanceof ConcurrentItemBucket || entry.getValue() instanceof CruxItemBucket) {
                try {
                    throw new Exception("net.newString.crux.core.model.ConcurrentItemBucket can't nested, putAll action ignored..");
                } catch (Exception e) {
                    //e.toString();
                }
            }
        }
        super.putAll(m);
    }
}
