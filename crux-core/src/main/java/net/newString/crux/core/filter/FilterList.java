package net.newString.crux.core.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 2016/8/3 15:47.
 *
 * @author lic
 */
public class FilterList extends BaseFilter {
    private static final long serialVersionUID = -2148652881633905377L;

    List<Filter> filters;
    Operator operator = Operator.MUST_PASS_ALL;

    /**
     * 执行过滤 对于空的过滤器组 视为存在一个可以通过全部数据的过滤器
     * <br>即，空的过滤器组执行{@link net.newString.crux.core.filter.Filter.Operator#MUST_PASS_NONE}时恒返回fase，其他情况恒返回true
     *
     * @return 过滤器执行情况
     */
    @Override
    public boolean doFilter(Object object) throws CruxFilterException{
        if (operator.equals(Operator.MUST_PASS_ALL)) {
            if (filters == null || filters.size() == 0) {
                return true;
            }
            boolean noEnable = true;
            for (Filter filter : filters) {
                if (filter.isEnable()) {
                    noEnable = false;
                }
                if (filter.isEnable() && !filter.doFilter(object)) {
                    return false;
                }
            }
            if (noEnable) { //如果所有Filter都是不可用，则 MUST_PASS_ALL 视为通过
                return true;
            }
        } else if (operator.equals(Operator.MUST_PASS_NONE)) {
            if (filters == null || filters.size() == 0) {
                return false;
            }
            boolean noEnable = true;
            for (Filter filter : filters) {
                if (filter.isEnable()) {
                    noEnable = false;
                }
                if (filter.isEnable() && filter.doFilter(object)) {
                    return false;
                }
            }
            if (noEnable) { //如果所有Filter都是不可用，则 MUST_PASS_NONE 视为不通过
                return false;
            }
        } else if (operator.equals(Operator.SHOULD_PASS_ONE)) {
            if (filters == null || filters.size() == 0) {
                return true;
            }
            boolean noEnable = true;
            for (Filter filter : filters) {
                if (filter.isEnable()) {
                    noEnable = false;
                }
                if (filter.isEnable() && filter.doFilter(object)) {
                    return true;
                }
            }
            if (noEnable) { //如果所有Filter都是不可用，则SHOULD_PASS_ONE视为通过
                return true;
            }
        }
        return false;
    }

    /**
     * 添加过滤器 如果过滤器已经存在，则直接设置可用(无论之前是否可用)
     * @param filter 待添加过滤器
     * @return 添加结果
     */
    public boolean addFilter(Filter filter) {
        if (filters == null) {
            filters = new ArrayList<>();
        }
        if (filters.contains(filter)) {
            filter.setEnable();
        }
        filters.add(filter);
        return true;
    }

    /**
     * 移除过滤器 实际上是设置不可用标记
     * <br>在对存在大量过滤器的过滤器组的操作下，建议使用{@linkplain FilterList#cloneEnableFilters() 可用过滤器拷贝}新建一个过滤器组而非
     * @param filter 待移除过滤器
     * @return 移除结果
     */
    public boolean removeFilter(Filter filter) {
        filter.setDisable();
        return true;
    }

    /**
     * 可用过滤器拷贝
     * <br>从当前的过滤器组中拷贝出一个可用的过滤器组，只获取其中可用的过滤器
     * @return
     */
    public FilterList cloneEnableFilters(){
        FilterList filterList = new FilterList();
        if(filters==null || filters.size()==0){
            return filterList;
        }
        for(Filter filter : filters){
            if(filter.isEnable()){
                filterList.addFilter(filter.copyOne());
            }
        }
        return filterList;
    }


    @Override
    public Filter copyOne() {
        FilterList filterList = new FilterList();
        if(filters==null || filters.size()==0){
            return filterList;
        }
        for(Filter filter : filters){
            filterList.addFilter(filter.copyOne());
        }
        return filterList;
    }

    public FilterList setOperator(Operator operator) {
        this.operator = operator;
        return this;
    }
}
