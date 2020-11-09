package org.example;

import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class ProductDetailController {

    private Product product;

    public Product getProduct(long id, HttpServletRequest request) {
        RestTemplate restTemplate = new RestTemplate();
        String requestUrl = String.format("%s://%s:%d/products/"  +  id, request.getScheme(),  request.getServerName(), request.getServerPort());
        try {
            Product product = restTemplate.getForObject(requestUrl, Product.class);
            return product;
        } catch (Exception e) {
            System.out.println(e);
            return product;
        }
    }

    public void updateProductByRest(long id, HttpServletRequest request) {
        String url = String.format("%s://%s:%d/products/"  +  id, request.getScheme(),  request.getServerName(), request.getServerPort());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = new Product(name, brand, price, quantity);

        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(url, entity, id);
    }

    public boolean addProductByRest(HttpServletRequest request) {
        String url = String.format("%s://%s:%d/products/", request.getScheme(),  request.getServerName(), request.getServerPort());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = new Product(name, brand, price, quantity);

        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Product> response = restTemplate.postForEntity(url, entity, Product.class);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            return true;
        } else {
            return false;
        }
    }

    @RequestMapping(value = {"/product_detail/{id}"}, method = RequestMethod.GET)
    public String viewProductDetail(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        Product product = getProduct(id, request);
        model.addAttribute("product", product);
        return "product_detail";
    }

    @RequestMapping(value = {"/product_detail/{id}"}, method = RequestMethod.POST)
    public String updateProduct(Model model, @PathVariable("id") long id, HttpServletRequest request) {
        updateProductByRest(id, request);
        Product product = getProduct(id, request);
        model.addAttribute("product", product);
        return "product_detail";
    }

    @RequestMapping(value = {"/add_product"}, method = RequestMethod.GET)
    public String viewProductAdd() {
        return "product_detail_new";
    }
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