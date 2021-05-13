package com.example;

import code.MyCode;
import com.example.configuration.A;
import com.example.entity.Book;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@MapperScan("com.example.mapper") //扫描的mapper
@SpringBootApplication
@ComponentScan(basePackages = {"test","com.example"})
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	@Autowired
	ApplicationContext applicationContext;
	@Autowired
	private A a;
	@Autowired
	MyCode myCode;
	@PostConstruct
	public void get() {
		System.out.println(applicationContext.getBean(Book.class));
		System.out.println(a);
		System.out.println("spring.fatories");
		System.out.println(myCode.doCode());
	}
}
