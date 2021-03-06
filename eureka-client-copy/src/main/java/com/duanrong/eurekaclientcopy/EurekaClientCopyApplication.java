package com.duanrong.eurekaclientcopy;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@SpringBootApplication
public class EurekaClientCopyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurekaClientCopyApplication.class, args);
	}

	@Value(value = "${server.port}")
	String port;

	@RequestMapping(value = "/hi")
	public String home(@RequestParam String name) {
		return "Hi " + name + ", I am from port: " + port;
	}
}
