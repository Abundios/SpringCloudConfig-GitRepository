package com.investproducts.appinvestproducts.configuration;


import com.sun.org.apache.xml.internal.security.utils.Base64;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;

//public class FeignClientInterceptor  {
//}

@Component
public class FeignClientInterceptor implements RequestInterceptor
{
    private static final String AUTHORIZATION_HEADER="Authorization";

    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        Collection<String> variablesTemplate = requestTemplate.getRequestVariables();
        if (requestTemplate.url().equals("/createUser"))
        {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = authentication.getName().toString();
        String password = authentication.getCredentials().toString();
        requestTemplate.header(AUTHORIZATION_HEADER, "Basic " + Base64.encode(new String(user+":"+password).getBytes()));
    }
}

