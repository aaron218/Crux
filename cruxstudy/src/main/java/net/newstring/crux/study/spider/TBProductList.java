package net.newstring.crux.study.spider;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * TBProductList
 *
 * @author lic
 * @date 2018/3/6
 */
public class TBProductList  implements ProductList {
    static PriceCheckUtil pcu = PriceCheckUtil.getInstance();

    private String tbUrl;

    private String productName;

    public TBProductList(String tbUrl, String productName) {
        this.tbUrl = tbUrl;
        this.productName = productName;
    }

    @Override
    public List<ProductInfo> getProductList() {
        List<ProductInfo> tbProductList = new ArrayList<ProductInfo>();
        ProductInfo productInfo = null;
        String url = "";
        int page = 0;
        for (int i = 0; i < 10; i++) {
            try {
                System.out.println("TB Product 第[" + (i + 1) + "]页");
                if (i == 0) {
                    url = tbUrl;
                } else {
                    page += 44;
                    url = Constants.TBURL + pcu.getUrlCode(productName) + Constants.TBPAGE + page;
                }
                System.out.println(url);
                Document doc = Jsoup.parse(pcu.getXmlByHtmlunit(url));
                Elements itemlist = doc.select("div[class=m-itemlist]");
                Iterator<Element> it = itemlist.iterator();
                while (it.hasNext()) {
                    Element item = it.next();
                    Elements items = item.select("div[data-category=auctions]");
                    System.out.println(items.size());
                    Iterator<Element> one = items.iterator();
                    while (one.hasNext()) {
                        Element e = one.next();
                        Elements price = e.select("div[class=price g_price g_price-highlight]>strong");
                        String productPrice = price.text();
                        Elements title = e.select("div[class=row row-2 title]>a");
                        String productName = title.text();
                        String href = e.select("div[class=row row-2 title]>a").get(0).attr("href");
                        productInfo = new ProductInfo();
                        productInfo.setProductUrl(href);
                        productInfo.setProductName(productName);
                        productInfo.setProductPrice(productPrice);
                        tbProductList.add(productInfo);
                    }

                }
            } catch (Exception e) {
                System.out.println("Get TB product has error");
                System.out.println(e.getMessage());
            }
        }


        return tbProductList;
    }

    public static void main(String[] args) {
        try {
            String productName = "DELL";
            String tbUrl = Constants.TBURL + pcu.getUrlCode(productName);
            List<ProductInfo> list = new TBProductList(tbUrl, productName).getProductList();
            for (ProductInfo pi : list) {
                System.out.println("[" + pi.getProductName() + "]  [" + pi.getProductPrice() + "] :"+pi.getProductUrl());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}