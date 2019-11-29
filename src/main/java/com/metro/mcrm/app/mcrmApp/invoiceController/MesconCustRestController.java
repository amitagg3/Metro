package com.metro.mcrm.app.mcrmApp.invoiceController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.metro.mcrm.app.mcrmApp.dao.MesconCustDao;
import com.metro.mcrm.app.mcrmApp.entity.MesconCustomer;

@RestController
@RequestMapping("/mescon")
public class MesconCustRestController {
	@Autowired
	MesconCustDao msc_dao;
	
	@GetMapping("/allMesconCust")
	@ResponseBody
	public List<MesconCustomer> getAllMescCust() {
		//System.out.println(enterprise);
		return msc_dao.findAllCustomer();

	}
	
	/*@GetMapping("/customerByCountry")
	public List<MesconCustomer> getCustomerBycountry(@RequestParam String enterprise,@RequestParam String country ) {
		
		return msc_dao.findCustomerByCountry(enterprise,country);
	
	}*/
	

}
