package net.newstring.crux.study.spider;

import java.util.List;

/**
 * ProductList
 *
 * @author lic
 * @date 2018/3/6
 */
public interface ProductList {
    /**
     * 爬取商品列表
     * @return
     */
    public List<ProductInfo> getProductList();
}
