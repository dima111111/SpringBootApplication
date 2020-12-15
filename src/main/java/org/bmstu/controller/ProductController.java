package org.bmstu.controller;

import org.bmstu.model.ProductEntity;
import org.bmstu.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    private static final int  leftoversQuantity = 5;

    @Autowired
    ProductRepository productRepository;

    /**
     * Method allows to get product list
     * @return List<Product>
     */
    public List <ProductEntity> getProductList() {
        List <ProductEntity> productList = new ArrayList<>();
        for (ProductEntity productEntity: productRepository.findAll()){
            productList.add(productEntity);
        }
        return productList;
    }

    /**
     * Method that process product_list GET request
     * @param model Model which contains attributes used in jsp page
     * @return String jsp view page
     */
    @RequestMapping(value = {"/product_list"}, method = RequestMethod.GET)
    public String viewProductList(Model model) {
        List <ProductEntity> productList = getProductList();
        model.addAttribute("products", productList);
        return "product_list";
    }

    /**
     * Method that process product_list search GET request
     * @param model Model which contains attributes used in jsp page
     * @return String jsp view page
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.GET)
    public String viewProductSearch(Model model) {
        List <ProductEntity>  productList = getProductList();
        model.addAttribute("products", productList);
        return "product_search";
    }

    /**
     * Method that process search POST request by filter parameters
     * @param model Model which contains attributes used in jsp page
     * @param request HttpServletRequest object of current request needed to get request params
     * @return String jsp view page
     */
    @RequestMapping(value = {"/search"}, method = RequestMethod.POST)
    public String viewProductSearchByFilter(Model model, HttpServletRequest request) {
        List <ProductEntity> productList = new ArrayList<>();
        String nameFilter = request.getParameter("name");
        String brandFilter = request.getParameter("brand");

        // this can be only one of the filter options
        if (nameFilter != null) {
            productList = productRepository.findByName(nameFilter);
        }
        if (brandFilter != null) {
            productList = productRepository.findByBrand(brandFilter);
        }
        if (request.getParameter("leftovers") != null) {
            productList = productRepository.findByQuantityLessThan(leftoversQuantity);
        }
        model.addAttribute("products", productList);
        return "product_search";
    }
}