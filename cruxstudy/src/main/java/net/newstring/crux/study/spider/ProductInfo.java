package net.newstring.crux.study.spider;

import java.io.Serializable;
import java.util.Date;

/**
 * ProductInfo
 *
 * @author lic
 * @date 2018/3/6
 */
public class ProductInfo  implements Serializable{

    private static final long serialVersionUID = -2499314213412574564L;
    /**
     * 商品ID
     */
    private String productid;
    /**
     * 商品名称
     */
    private String productName;
    /**
     * 商品价格
     */
    private String productPrice;
    /**
     * 月销售笔数
     */
    private String tradeNum;
    /**
     * 商品URL
     */
    private String productUrl;
    /**
     * 商品网店名称
     */
    private String shopName;
    /**
     * 电商名称
     */
    private String ecName;
    /**
     * 爬取入库日期
     */
    private Date date;

    public String getProductid() {
        return productid;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getTradeNum() {
        return tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getEcName() {
        return ecName;
    }

    public void setEcName(String ecName) {
        this.ecName = ecName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
