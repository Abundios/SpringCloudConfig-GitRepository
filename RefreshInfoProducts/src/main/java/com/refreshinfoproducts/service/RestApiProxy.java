package com.refreshinfoproducts.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@FeignClient(name="restapi-service")
//@Headers({"Authorization: Basic {credentials}"})
public interface RestApiProxy
{
    @GetMapping("/products")
    public Object[] products();


    @RequestMapping(value = "/updateProduct/{isin}", produces = {"application/json; charset=UTF-8"},method = RequestMethod.PUT)
    @ResponseBody
    HashMap<String, Object> updateProductByIsin(@PathVariable String isin,  @RequestBody HashMap<String,String> map);
}
