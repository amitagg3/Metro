package com.metro.mcrm.app.mcrmApp.invoiceController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.metro.mcrm.app.mcrmApp.dao.InvoiceJdbcDao;
import com.metro.mcrm.app.mcrmApp.entity.Invoice;

@RestController
@RequestMapping("/invoices")
public class InvoiceRestController {

	@Autowired
	InvoiceJdbcDao dao;

	@GetMapping("/allInvoices")
	@ResponseBody
	public List<Invoice> getAllInvoices(@RequestParam String enterprise, @RequestParam Optional<String> country,@RequestParam Optional<String> noOfDays,@RequestParam Optional<String>store_num,@RequestParam Optional<String>invc_num,@RequestParam Optional<String>order_num,@RequestParam Optional<String[]> status) {

		return dao.findAll(enterprise,country,noOfDays,store_num,invc_num,order_num,status);

	}
	
	
}
