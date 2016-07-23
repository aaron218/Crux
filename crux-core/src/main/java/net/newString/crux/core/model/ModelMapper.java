package net.newString.crux.core.model;

import java.util.Map;

/**
 * Created on 2016/7/22 10:50.
 *
 * @author lic
 */
public class ModelMapper {

    private ModelMapper() {
    }


    public <D> D mapper(Object source, Class<D> destinationType) {
        return null;
    }

    /**
     * 将一个对象转化为Map，属性名称转换为key，属性值为value
     * @param source 源对象
     * @param sourcesType  源对象类型
     * @return 转化后的对象
     */
    public <S> Map<String, Object> toMap(Object source, Class<S> sourcesType) {
        return null;
    }


    /**
     *
     * @param source
     * @param destinationType
     * @return
     */
    public <D> D fromMap(Map<String,Object> source,Class<D> destinationType){
        return null;
    }

}
