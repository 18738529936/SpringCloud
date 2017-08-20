package com.duanrong.servicehi;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Level;
import java.util.logging.Logger;

@EnableEurekaClient
@EnableHystrix
@EnableHystrixDashboard
@RestController
@SpringBootApplication
public class ServiceHiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceHiApplication.class, args);
	}

	private static final Logger logger = Logger.getLogger(ServiceHiApplication.class.getName());

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@RequestMapping(value = "/hi")
	public String callHome() {
		logger.log(Level.INFO, "calling trace service-hi->hi  ");
		return restTemplate.getForObject("http://localhost:8989/miya", String.class);
	}

	@RequestMapping(value = "/info")
	public String info() {
		logger.log(Level.INFO, "calling trace service-hi->info  ");
		return "i'm service-hi";
	}

	@Bean
	public AlwaysSampler defaultSampler() {
		return new AlwaysSampler();
	}

	// 测试断路器监控(Hystrix Dashboard)

	@Value(value = "${server.port}")
	String port;

	@RequestMapping(value = "/hello")
	@HystrixCommand(fallbackMethod = "helloError")
	public String home(@RequestParam String name) {
		return "hello " + name + ", I am from port : " + port;
	}

	public String helloError(String name) {
		return "hello, " + name + ", sorry, error!";
	}
}
