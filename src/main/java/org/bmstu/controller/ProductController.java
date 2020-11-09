package org.bmstu.controller;

import org.bmstu.dto.Product;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Product controller
 * This controller is used at product list (/product_list) and product search pages (/search).
 * Controller also have methods to get all product list or filter list.
 */
@Controller
public class ProductController {
    /**
     * Variable which store product list of type {@link Product}
     */
    private List <Product> productList = new ArrayList<>();

    private static final int  leftoversQuantity = 5;

    /**
     * Method allows to get product list filtered by name making rest api request
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @param restApiUrl String rest api url to get all products of filtered product list
     * @return List<Product>
     */
    public List <Product> getProductList(HttpServletRequest request, String restApiUrl) {
        productList.clear();

        // making server request url and execute rest api GET request using RestTemplate.getForObject method
        String requestUrl = String.format("%s://%s:%d/" + restApiUrl ,request.getScheme(),  request.getServerName(), request.getServerPort());
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(requestUrl, String.class);

        // parsing string result to JSON object
        JSONObject objResponse = new JSONObject(result);
        JSONArray productsObj = objResponse.getJSONObject("_embedded").getJSONArray("products");

        // get each JSON object of product, create Product object from JSONObject and add it in productList
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

    /**
     * Method that process product_list GET request
     * @param model Model which contains attributes used in jsp page
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return String jsp view page
     */
    @RequestMapping(value = {"/product_list"}, method = RequestMethod.GET)
    public String viewProductList(Model model, HttpServletRequest request) {
        productList = getProductList(request, "products");
        model.addAttribute("products", productList);
        return "product_list";
    }

    /**
     * Method that process product_list search GET request
     * @param model Model which contains attributes used in jsp page
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return String jsp view page
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String viewProductSearch(Model model, HttpServletRequest request) {
        productList = getProductList(request, "products");
        model.addAttribute("products", productList);
        return "product_search";
    }

    /**
     * Method that process search POST request by filter parameters
     * @param model Model which contains attributes used in jsp page
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return String jsp view page
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String viewProductSearchByFilter(Model model, HttpServletRequest request) {
        String nameFilter = request.getParameter("name");
        String brandFilter = request.getParameter("brand");

        // this can be only one of the filter options
        if (nameFilter != null) {
            productList = getNameFilteredProductList(nameFilter, request);
        }
        if (brandFilter != null) {
            productList = getBrandFilteredProductList(brandFilter, request);
        }
        if (request.getParameter("leftovers") != null) {
            productList = getLeftoversProductList(request);
        }
        model.addAttribute("products", productList);
        return "product_search";
    }

    /**
     * Method allows to get product list filtered by name making rest api request
     * @param nameFilter String filter by name
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return List<Product>
     */
    private List<Product> getNameFilteredProductList(String nameFilter, HttpServletRequest request) {
        return getProductList(request, "products/search/findByName?name=" + nameFilter);
    }

    /**
     * Method allows to get product list filtered by brand making rest api request
     * @param brandFilter String filter by brand
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return List<Product>
     */
    private List<Product> getBrandFilteredProductList(String brandFilter, HttpServletRequest request) {
        return getProductList(request, "products/search/findByBrand?brand=" + brandFilter);
    }

    /**
     * Method allows to get product list which quantity is less then leftoversQuantity
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return List<Product>
     */
    private List<Product> getLeftoversProductList(HttpServletRequest request) {
        return getProductList(request, "/products/search/findByQuantityLessThan?quantity=" + leftoversQuantity);
    }
}