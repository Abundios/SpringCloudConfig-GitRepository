package com.investproducts.appinvestproducts.controller;

import com.investproducts.appinvestproducts.model.DetailPurchase;
import com.investproducts.appinvestproducts.model.UserProductWithdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.*;

import com.investproducts.appinvestproducts.model.Product;
import com.investproducts.appinvestproducts.model.User;
import com.investproducts.appinvestproducts.service.RestApiProxy;

@Controller
public class PagesController
{
    @Autowired
    private RestApiProxy restApiProxy;

    @GetMapping("/")
    public String index(Model model, HttpSession session)
    {
        User user = new User();
        session.setAttribute("user", user);
        model.addAttribute("user", user);

        return "index";
    }

    @GetMapping("/principal")
    public String principal(Model model, HttpSession session)
    {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal == null)
        {
            return "index";
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = principal.getUsername();
        Object[] productsUser = restApiProxy.productsByUser(uid);
        float totalInvestedAmount = 0;
        float totalCurrentValue = 0;
        for(int index=0; index< productsUser.length; index++)
        {
            totalInvestedAmount += Float.parseFloat( ((LinkedHashMap)((ArrayList)productsUser[index]).get(1)).
                                                        get("investedMount").toString() );
            totalCurrentValue += Float.parseFloat( ((LinkedHashMap)((ArrayList)productsUser[index]).get(0)).
                                 get("currentValue").toString() )
                                 *
                                 Float.parseFloat( ((LinkedHashMap)((ArrayList)productsUser[index]).get(1)).
                                 get("participations").toString() );
        }

        Object user = restApiProxy.user(uid);
        model.addAttribute(user);
        session.setAttribute("user", user);
        session.setAttribute("productsUser", productsUser);
        session.setAttribute("totalInvestedAmount", totalInvestedAmount);
        session.setAttribute("totalCurrentValue", totalCurrentValue);

        Object[] products = restApiProxy.products();
        session.setAttribute("products", products);

        Product product = new Product();
        model.addAttribute("product", product);
        DetailPurchase detailPurchase = new DetailPurchase();
        detailPurchase.setInvestedAmount(new Float(1000-totalInvestedAmount));
        model.addAttribute("detailPurchase", detailPurchase);

        UserProductWithdrawal userProductWithdrawal = new UserProductWithdrawal();
        model.addAttribute(userProductWithdrawal);

        return "userProducts";
    }

    @RequestMapping(value="/purchase-submit", method=RequestMethod.POST)
    public String purchaseSubmit(@ModelAttribute User user, @ModelAttribute Product product, @ModelAttribute DetailPurchase detailPurchase, BindingResult bindingResult, Model model, HttpSession session)
    {
        HashMap<String, String> requestedProduct = restApiProxy.productByDescription(product.getDescription());

        HashMap<String, String> attributesUserSession = (HashMap)session.getAttribute("user");

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("userid", String.valueOf(attributesUserSession.get("id")));
        mapData.put("productid", String.valueOf(requestedProduct.get("id").toString()));
        mapData.put("investedamount", detailPurchase.getInvestedAmount().toString());
        mapData.put("productvalue", String.valueOf(requestedProduct.get("currentValue").toString()));
        mapData.put("participations", String.valueOf(
                                    detailPurchase.getInvestedAmount().floatValue()
                                      /
                                      Float.parseFloat(requestedProduct.get("currentValue"))
                                      ));
        restApiProxy.createUserProduct(mapData);

        return "redirect:/principal";
    }

    @RequestMapping(value="/withdrawal-submit", method=RequestMethod.POST)
    public String withdrawalSubmit(@ModelAttribute UserProductWithdrawal userProductWithdrawal, BindingResult bindingResult, Model model, HttpSession session)
    {
        restApiProxy.deleteProductUser(userProductWithdrawal.getId());
        return "redirect:/principal";
    }

    @RequestMapping(value="/createUser", method=RequestMethod.POST)
    public String createUser(@ModelAttribute User user, BindingResult bindingResult, Model model, HttpSession session)
    {
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("user", user.getName());
        mapData.put("password", user.getPassword());
        mapData.put("forename", user.getForename());
        restApiProxy.createUser(mapData);

        return "redirect:/";
    }

}
