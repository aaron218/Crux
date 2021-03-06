package net.newstring.crux.study.spider;

import java.util.*;

/**
 * PriceCheckMain
 *
 * @author lic
 * @date 2018/3/6
 */
public class PriceCheckMain {
    private static PriceCheckUtil pcu = PriceCheckUtil.getInstance();

    public List<Map<String, ProductInfo>> getProductList(String productName) {

        String jdUrl = Constants.JDURL + productName + Constants.JDENC;

        String tbUrl = Constants.TBURL + productName;

        return getProductFromUrls(jdUrl, tbUrl, productName);
    }

    public List<Map<String, ProductInfo>> getProductFromUrls(String jdUrl, String tbUrl, String productName) {
        List<Map<String, ProductInfo>> retListMap = new ArrayList<Map<String,ProductInfo>>();
        List<ProductInfo> jdProductList = new JDProductList(jdUrl, productName).getProductList();
        List<ProductInfo> tbProductList = new TBProductList(tbUrl, productName).getProductList();
        for(int i = 0; i < jdProductList.size(); i++){
            String jdProductName = jdProductList.get(i).getProductName();
            Map<String, ProductInfo> map = new HashMap<String, ProductInfo>();
            map.put("JD", jdProductList.get(i));
            ProductInfo tbProduct = pcu.getSimilarity(jdProductName, tbProductList);
            map.put("TB", tbProduct);
            retListMap.add(map);
        }

        return retListMap;
    }


    public static void main(String[] args) {
        System.out.println("输入商品名称：");
        Scanner scanner = new Scanner(System.in);
        String productName = scanner.next();
        scanner.close();
        System.out.println("京东和淘宝[" + productName + "]商品比价开始。。。。。。");
        try{
            long starTime = System.currentTimeMillis();
            List<Map<String, ProductInfo>> list = new PriceCheckMain().getProductList(productName);
            for(Map<String, ProductInfo> map : list){
                String jdName = map.get("JD").getProductName();
                String jdPrice = map.get("JD").getProductPrice();
                String ddName = map.get("TB").getProductName();
                String ddPrice = map.get("TB").getProductPrice();

                System.out.println("[" + jdName + "]  [" + ddName + "]");
                System.out.println("[" + jdPrice + "]  [" + ddPrice + "]");
                System.out.println("-----------------------------------------------------------");
            }
            long endTime = System.currentTimeMillis();
            System.out.println("用时 [" + pcu.msToss(endTime - starTime) + "]");
        }catch(Exception e){
            System.out.println("error");
            System.out.println(e.getMessage());
        }
    }
}
