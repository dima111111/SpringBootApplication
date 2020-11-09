package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.object.SqlQuery;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProductController {

    private List <Product> productList = new ArrayList<Product>();

    public List <Product> getProductList(HttpServletRequest request, String restApiUrl) {

        productList.clear();
        String requestUrl = String.format("%s://%s:%d/" + restApiUrl ,request.getScheme(),  request.getServerName(), request.getServerPort());
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);
        JSONObject objResponse = new JSONObject(result);
        JSONArray productsObj = objResponse.getJSONObject("_embedded").getJSONArray("products");
        for(int i = 0 ; i < productsObj.length() ; i++){
            JSONObject productObj = productsObj.getJSONObject(i);
            String name = (String) productObj.get("name");
            String brand = (String) productObj.get("brand");
            int price = (int) productObj.get("price");
            int quantity = (int) productObj.get("quantity");
            String link = (String) productObj.getJSONObject("_links").getJSONObject("self").get("href");
            productList.add(new Product(link, name, brand, price, quantity));
        }

        return productList;
    }

    @RequestMapping(value = {"/product_list"}, method = RequestMethod.GET)
    public String viewProductList(Model model, HttpServletRequest request) {
        productList = getProductList(request, "products");
        model.addAttribute("products", productList);
        return "product_list";
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String viewProductSearch(Model model, HttpServletRequest request) {
        productList = getProductList(request, "products");
        model.addAttribute("products", productList);
        return "product_search";
    }

    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String viewProductSearchByFilter(Model model, HttpServletRequest request) {
        String nameFilter = request.getParameter("name");
        String brandFilter = request.getParameter("brand");
        if (nameFilter != null) {
            productList = getNameFilteredProductList(nameFilter, request);
        }
        if (brandFilter != null) {
            productList = getBrandFilteredProductList(brandFilter, request);
        }
        if (request.getParameter("leftLovers") != null) {
            productList = getLeftLoversProductList(request);
        }
        model.addAttribute("products", productList);
        return "product_search";
    }

    private List<Product> getNameFilteredProductList(String nameFilter, HttpServletRequest request) {
        return getProductList(request, "products/search/findByName?name=" + nameFilter);
    }

    private List<Product> getBrandFilteredProductList(String brandFilter, HttpServletRequest request) {
        return getProductList(request, "products/search/findByBrand?brand=" + brandFilter);
    }

    // TODO: it should be SQL: "select * from PRODUCT_ENTITY WHERE QUANTITY < 5";
    private List<Product> getLeftLoversProductList(HttpServletRequest request) {
        productList = getProductList(request, "products");
        // foreach
        productList.removeIf(product -> product.getQuantity() >= 5);
        return productList;
    }
}