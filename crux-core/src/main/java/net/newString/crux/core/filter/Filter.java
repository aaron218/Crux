package net.newString.crux.core.filter;

import java.io.Serializable;

/**
 * 仿照HBase Filter结构设计的过滤器。
 * @author lic
 */
public interface Filter extends Serializable{
    /**
     * 执行过滤 实现类中应该是让符合条件的数据经过此方法后返回true
     * @param object 待检测数据
     * @return 过滤结果
     */
    boolean doFilter(Object object) throws CruxFilterException;

    /**
     * 设置Filter可用
     */
    void setEnable();

    /**
     * 设置Filter不可用
     */
    void setDisable();

    /**
     * 判断Filter是否可用
     * @return Filter可用性
     */
    boolean isEnable();

    /**
     * 创见一个自身的副本 方法实现必须是返回新对象而不能返回this
     * @return 新副本
     */
    Filter copyOne();

    enum Operator{
        MUST_PASS_ALL,
        MUST_PASS_NONE,
        SHOULD_PASS_ONE
    }


}
