package com.duanrong.ribbon.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HelloService {

    @Autowired
    RestTemplate restTemplate;

    // 对该方法创建熔断器，指定熔断方法
    @HystrixCommand(fallbackMethod = "hiError")
    public String hiService(String name) {
        return restTemplate.getForObject("http://EUREKA-CLIENT/hi?name=" + name, String.class);
    }

    // 熔断方法
    public String hiError(String name) {
        return "hi, " + name + ", sorry, error!";
    }
}

