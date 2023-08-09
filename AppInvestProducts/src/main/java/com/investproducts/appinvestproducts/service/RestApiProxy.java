package com.investproducts.appinvestproducts.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;


@FeignClient(name="restapi-service")
public interface RestApiProxy {
    @GetMapping("/productsByUser/{uid}")
    public Object[] productsByUser(@PathVariable("uid") String uid);

    @GetMapping("/products")
    public Object[] products();

    @GetMapping("/user/{uid}")
    public Object user(@PathVariable("uid") String uid);

    @GetMapping("/product/{isin}")
    public HashMap<String, String> productByIsin(@PathVariable("isin") String isin);

    @GetMapping("/product/{description}")
    public HashMap<String, String> productByDescription(@PathVariable("description") String description);

    @RequestMapping(value = "/createUser", produces = {"application/json; charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    HashMap<String, Object> createUser(@RequestBody HashMap<String,String> map);

    @RequestMapping(value = "/createUserProduct", produces = {"application/json; charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    HashMap<String, Object> createUserProduct(@RequestBody HashMap<String,String> map);

    @DeleteMapping("/deleteProductUser/{id}")
    public ResponseEntity<?> deleteProductUser(@PathVariable Integer id);
}