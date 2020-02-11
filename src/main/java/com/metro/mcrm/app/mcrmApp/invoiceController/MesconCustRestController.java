package com.metro.mcrm.app.mcrmApp.invoiceController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.metro.mcrm.app.mcrmApp.dao.MesconCustDao;
import com.metro.mcrm.app.mcrmApp.entity.MesconCustomer;

@RestController
@RequestMapping("/mescon")
public class MesconCustRestController {
	@Autowired
	MesconCustDao msc_dao;
	
	
	@GetMapping("/mesconCustomer")
	@ResponseBody
	public List<MesconCustomer> getMesconDetails(@RequestParam String entityType,@RequestParam Optional<String> country,
			@RequestParam Optional<String> store,@RequestParam Optional<String> operation,
			@RequestParam Optional<String[]> ProcessingCd,@RequestParam Optional<String> payload,
			@RequestParam Optional<String> key1,@RequestParam Optional<String> key4,@RequestParam Optional<String> noOfDays){
		   return msc_dao.findAllData(entityType,country,store,operation,ProcessingCd,payload,key1,key4,noOfDays);
		}
		
	
	}
	
