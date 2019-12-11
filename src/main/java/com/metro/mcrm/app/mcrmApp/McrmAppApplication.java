package com.metro.mcrm.app.mcrmApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.metro.mcrm.app.mcrmApp.invoiceController.InvoiceRestController;
import com.metro.mcrm.app.mcrmApp.invoiceController.MesconCustRestController;

@SuppressWarnings("deprecation")
@SpringBootApplication()
public class McrmAppApplication implements CommandLineRunner {



		private Logger logger = LoggerFactory.getLogger(this.getClass());

		@Autowired
		InvoiceRestController invoiceRestController;
		
		@Autowired
		MesconCustRestController mesconCustRestController;

		public static void main(String[] args) {
			SpringApplication.run(McrmAppApplication.class, args);
		}

		 @Bean
		    public WebMvcConfigurer corsConfigurer() {
		        return new WebMvcConfigurerAdapter() {
		            @Override
		            public void addCorsMappings(CorsRegistry registry) {
		                registry.addMapping("/**").allowedOrigins("*");
		            }
		        };
		    }
		public void run(String... args) throws Exception {
			//logger.info("All users -> {}", .getAllInvoices("E1", "undefined", "10", "undefined"));
			//logger.info("All users -> {}", invoiceRestController.getAllInvoices("E2", null, null, null));
			

			logger.info("Hello There");
//			logger.info("User id 10001 -> {}", dao.findById(10001));
//			
//			logger.info("Deleting 10002 -> No of Rows Deleted - {}", 
//					dao.deleteById(10002));
//			
//			logger.info("Inserting 10004 -> {}", 
//					dao.insert(new Person(10004, "Tara", "Berlin", new Date())));
//			
//			logger.info("Update 10003 -> {}", 
//					dao.update(new Person(10003, "Pieter", "Utrecht", new Date())));
			
		}
	}

