package org.bmstu.controller;

import org.bmstu.model.ProductEntity;
import org.bmstu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Product detail controller
 * This controller is used at product detail page (/product_detail/{id}) and product add pages (/add_product).
 * Controller also have methods to update or add new product.
 */
@Controller
public class ProductDetailController {

    @Autowired
    ProductRepository productRepository;

    /**
     * Creates ProductEntity object by POST request parameters
     * @param request HttpServletRequest POST request
     * @return ProductEntity
     */
    private ProductEntity getProductEntityByRequest(HttpServletRequest request) {
        String name = request.getParameter("name");
        String brand = request.getParameter("brand");
        int price = Integer.parseInt(request.getParameter("price"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        return new ProductEntity(name, brand, price, quantity);
    }

    /**
     * Method allows to update product by id and request params
     * @param id long Product id
     * @param request HttpServletRequest object of current request needed to get request params
     */
    public void updateProductEntity(long id, HttpServletRequest request) {
        ProductEntity productEntity = getProductEntityByRequest(request);
        productEntity.setId(id);
        productRepository.save(productEntity);
    }
    /**
     * Method allows to add product making rest api request
     * @param request HttpServletRequest object of current request needed to get request params
     */
    public void addProductEntity(HttpServletRequest request) {
        ProductEntity productEntity = getProductEntityByRequest(request);
        productRepository.save(productEntity);
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
        ProductEntity product = productRepository.findById(id);
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
        updateProductEntity(id, request);
        ProductEntity product = productRepository.findById(id);
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
        addProductEntity(request);
        List <ProductEntity> productList = new ArrayList<>();
        for (ProductEntity productEntity: productRepository.findAll()){
            productList.add(productEntity);
        }
        model.addAttribute("products", productList);
        return "product_list";
    }
}