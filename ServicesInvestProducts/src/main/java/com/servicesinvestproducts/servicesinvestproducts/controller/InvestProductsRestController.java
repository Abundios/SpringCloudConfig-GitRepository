package com.servicesinvestproducts.servicesinvestproducts.controller;

import com.servicesinvestproducts.servicesinvestproducts.model.UserProduct;
import org.springframework.web.bind.annotation.*;

import com.servicesinvestproducts.servicesinvestproducts.services.UserService;
import com.servicesinvestproducts.servicesinvestproducts.services.ProductService;
import com.servicesinvestproducts.servicesinvestproducts.services.UserProductService;
import com.servicesinvestproducts.servicesinvestproducts.services.LDAPService;
import com.servicesinvestproducts.servicesinvestproducts.model.Product;
import com.servicesinvestproducts.servicesinvestproducts.model.User;

import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;


@RestController
public class InvestProductsRestController
{

    final UserService userService;
    final ProductService productService;
    final UserProductService userProductService;
    final LDAPService ldapService;

    public InvestProductsRestController(UserService userService, ProductService productService, UserProductService userProductService, LDAPService ldapService)
    {
        this.userService = userService;
        this.productService = productService;
        this.userProductService = userProductService;
        this.ldapService = ldapService;
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> multipleProducts()
    {
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> multipleUsers()
    {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/productsByUser/{uid}")
    public ResponseEntity<List<Object[]>> multipleProductsByuid(@PathVariable String uid)
    {
        List<Object[]> userProductDetail = userProductService.findProductByuid(uid);
        return ResponseEntity.ok(userProductDetail);
    }

    @GetMapping("/user/{uid}")
    public ResponseEntity<User> userByUid(@PathVariable String uid)
    {
        User user = userService.getUserByUid(uid);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/product/{description}")
    public ResponseEntity<Product> productByDescription(@PathVariable String description)
    {
        Product product = productService.findByDescription(description);
        return ResponseEntity.ok(product);
    }
    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody Product product) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        productService.create(product);
        map.put("status", 1);
        map.put("message", "Record is Saved Successfully!");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/createUser", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUser(@RequestBody Map<String, String> json) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        ldapService.create(json.get("user"), json.get("password"), json.get("forename"));
        map.put("status", 1);
        map.put("message", "Record is Saved Successfully!");

        User user = new User();
        user.setUid(json.get("user"));
        user.setForename(json.get("forename"));
        userService.create(user);

        return new ResponseEntity<>(map, HttpStatus.CREATED);

    }

    @RequestMapping(value = "/createUserProduct", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> createUserProduct(@RequestBody Map<String, String> json) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        UserProduct userProduct = new UserProduct();
        Product product = new Product();
        product.setId(Integer.parseInt(json.get("productid")));
        User user = new User();
        user.setId(Integer.parseInt(json.get("userid")));
        userProduct.setUser(user);
        userProduct.setProduct(product);
        userProduct.setInvestedMount(Float.parseFloat(json.get("investedamount")));
        userProduct.setProductValue(Float.parseFloat(json.get("productvalue")));
        userProduct.setParticipations(Float.parseFloat(json.get("participations")));
        userProductService.create(userProduct);
        map.put("status", 1);
        map.put("message", "Record is Saved Successfully!");
        return new ResponseEntity<>(map, HttpStatus.CREATED);
    }

    @PutMapping("/updateProduct/{isin}")
    public ResponseEntity<?> updateProductByIsin(@PathVariable String isin, @RequestBody Product productDetail) {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try
        {
            Product product = productService.findByIsin(isin);
            product.setCurrentValue(productDetail.getCurrentValue());
            productService.update(product);
            map.put("status", 1);
            map.put("data", productService.findByIsin(isin));
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex)
        {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteProduct/{isin}")
    public ResponseEntity<?> deleteProduct(@PathVariable String isin)
    {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            Product product = productService.findByIsin(isin);
            productService.delete(product);
            map.put("status", 1);
            map.put("message", "Record is deleted successfully!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteProductUser/{id}")
    public ResponseEntity<?> deleteProductUser(@PathVariable Integer id)
    {
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        try {
            userProductService.delete(id);
            map.put("status", 1);
            map.put("message", "Record is deleted successfully!");
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception ex) {
            map.clear();
            map.put("status", 0);
            map.put("message", "Data is not found");
            return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);
        }
    }

}
