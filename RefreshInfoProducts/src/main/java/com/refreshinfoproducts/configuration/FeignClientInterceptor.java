package com.refreshinfoproducts.configuration;

import com.sun.org.apache.xml.internal.security.utils.Base64;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
public class FeignClientInterceptor implements RequestInterceptor
{
    private static final String AUTHORIZATION_HEADER="Authorization";

    @Autowired
    private Environment env;

    @Override
    public void apply(RequestTemplate requestTemplate)
    {
        Collection<String> variablesTemplate = requestTemplate.getRequestVariables();
        if (requestTemplate.url().equals("/createUser"))
        {
            return;
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String user = env.getRequiredProperty("restapiservice.user");
        String password = env.getRequiredProperty("restapiservice.password");
        requestTemplate.header(AUTHORIZATION_HEADER, "Basic " + Base64.encode(new String(user+":"+password).getBytes()));
    }
}

