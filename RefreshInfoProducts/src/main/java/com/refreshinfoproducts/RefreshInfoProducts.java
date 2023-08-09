package com.refreshinfoproducts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import com.refreshinfoproducts.service.RestApiProxy;
import org.springframework.core.env.Environment;

@SpringBootApplication
@EnableFeignClients("com.refreshinfoproducts.service")
public class RefreshInfoProducts
{
    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(RefreshInfoProducts.class, args);
    }

    @Bean
    public CommandLineRunner loadData(RestApiProxy restApiProxy)
    {
        return (args) ->
        {
            while (true)
            {
                try
                {
                    Object[] products = restApiProxy.products();

                    String link = "";
                    for(int index=0; index< products.length; index++)
                    {
                        link = ((LinkedHashMap) products[index]).get("link").toString();


                        Document document = Jsoup.connect(link)
                                .timeout(5000)
                                .get();
                        Element table = document.select("table").get(3);
                        Element VL = table.select("tr").get(1);
                        Element variacion = table.select("tr").get(2);

                        String currentValue =  VL.select("td").get(2).text().replace("USD","").replace(",",".").trim();

                        HashMap<String, String> mapData = new HashMap<>();
                        mapData.put("currentValue", currentValue);
                        restApiProxy.updateProductByIsin(((LinkedHashMap) products[index]).get("isin").toString(), mapData);

                    }
                    TimeUnit.SECONDS.sleep( Integer.parseInt(env.getRequiredProperty("timer.refresh")));
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }
}