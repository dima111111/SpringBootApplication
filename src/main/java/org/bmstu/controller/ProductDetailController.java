package org.bmstu.controller;

import org.bmstu.dto.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Product detail controller
 * This controller is used at product detail page (/product_detail/{id}) and product add pages (/add_product).
 * Controller also have methods to update or add new product.
 */
@Controller
public class ProductDetailController {

    /**
     * Variable which store selected {@link Product}
     */
    private Product product;

    /**
     * Method allows to get product by id making rest api request
     * @param id long Product id
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return Product object
     */
    public Product getProduct(long id, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = String.format("%s://%s:%d/products/"  +  id, request.getScheme(),  request.getServerName(), request.getServerPort());
        try {
            product = restTemplate.getForObject(requestUrl, Product.class);
            return product;
        } catch (Exception e) {
            return product;
        }
    }

    /**
     * Creates Product object by POST request parameters
     * @param request HttpServletRequest POST request
     * @return Product
     */
    private Product getProductByPostRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        return new Product(name, brand, price, quantity);
    }

    /**
     * Creates HttpEntity by request parameters
     * @param request HttpServletRequest POST request
     * @return HttpEntity<Product>
     */
    private HttpEntity<Product> getHttpEntity(HttpServletRequest request) {
        // set Headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // get Product object by request parameters
        product = getProductByPostRequest(request);

        // making HttpEntity<Product> entity by headers and product object
        return new HttpEntity<>(product, headers);
    }

    /**
     * Method allows to update product by id making rest api request
     * @param id long Product id
     * @param request HttpServletRequest object of current request needed to make rest api request url
     */
    public void updateProductByRest(long id, HttpServletRequest request) {
        // set URL
        String url = String.format("%s://%s:%d/products/"  +  id, request.getScheme(),  request.getServerName(), request.getServerPort());
        // execute rest api update product request using RestTemplate.put method
        HttpEntity<Product> entity = getHttpEntity(request);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url, entity, id);
    }

    /**
     * Method allows to add product by id making rest api request
     * @param request HttpServletRequest object of current request needed
     * to make rest api request url and get all POST parameters
     * @return boolean value - was product successfully added
     */
    public boolean addProductByRest(HttpServletRequest request) {
        // set Headers and URL
        String url = String.format("%s://%s:%d/products/", request.getScheme(),  request.getServerName(), request.getServerPort());
        // execute rest api update product request using RestTemplate.postForEntity method
        HttpEntity<Product> entity = getHttpEntity(request);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.postForEntity(url, entity, Product.class);

        return response.getStatusCode() == HttpStatus.CREATED;
    }

    /**
     * Method that process product GET request by product id
     * @param model Model which contains attributes used in jsp page
     * @param id int product id
     * @param request HttpServletRequest object of current request needed to make rest api request url
     * @return String jsp view pag
     */
    @RequestMapping(value = {"/product_detail/{id}"}, method = RequestMethod.GET)
    public String viewProductDetail(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        product = getProduct(id, request);
        model.addAttribute("product", product);
        return "product_detail";
    }

    /**
     * Method that process product POST request by product id and change selected product
     * @param model Model which contains attributes used in jsp page
     * @param id int product id
     * @param request HttpServletRequest object of current request needed to make rest api request url and get POST parameters
     * @return String jsp view pag
     */
    @RequestMapping(value = {"/product_detail/{id}"}, method = RequestMethod.POST)
    public String updateProduct(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        updateProductByRest(id, request);
        product = getProduct(id, request);
        model.addAttribute("product", product);
        return "product_detail";
    }

    /**
     * Method that process add_product GET request and return jsp
     * @return String jsp view pag
     */
    @RequestMapping(value = {"/add_product"}, method = RequestMethod.GET)
    public String viewProductAdd() {
        return "product_detail_new";
    }

    /**
     * Method that process add_product POST request and add product in table
     * @param model Model which contains attributes used in jsp page
     * @param request HttpServletRequest object of current request needed to make rest api request url and get POST parameters
     * @return String jsp view pag
     */
    @RequestMapping(value = {"/add_product"}, method = RequestMethod.POST)
    public String createProduct(Model model, HttpServletRequest request) {
        boolean isSuccess = addProductByRest(request);
        if (isSuccess) {
            ProductController controller = new ProductController();
            List<Product> productList = controller.getProductList(request, "products");
            model.addAttribute("products", productList);
            return "product_list";
        } else {
            return "product_detail_new";
        }
    }
}