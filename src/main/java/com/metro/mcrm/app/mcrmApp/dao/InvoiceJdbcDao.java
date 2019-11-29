package com.metro.mcrm.app.mcrmApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.metro.mcrm.app.mcrmApp.entity.Invoice;

@Repository
public class InvoiceJdbcDao {
	
	 	@Autowired
	 	@Qualifier("jdbcTemplateOne")
	    private JdbcTemplate jdbcTemplateOne;

	 	@Autowired
	 	@Qualifier("jdbcTemplateTwo")
	    private JdbcTemplate jdbcTemplateTwo;
	 	
	 	
	class InvoiceRowMapper implements RowMapper<Invoice>{

		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Invoice invoice= new Invoice();
			invoice.setInvc_num(rs.getString("invc_num"));
			invoice.setOrder_num(rs.getString("order_num"));
			invoice.setError_desc(rs.getString("X_ERROR_DESC"));
			invoice.setCreated(rs.getDate("created"));
			invoice.setOrder_created(rs.getDate("order_created"));
			invoice.setStore(rs.getString("store_num"));
			invoice.setCustomer_num(rs.getString("customer_num"));
			invoice.setStatus(rs.getString("status"));
			invoice.setOrder_status(rs.getString("order_status"));
			invoice.setBu_id(rs.getString("bu_id"));
			return invoice;
		}
		
	}
	
	public List<Invoice> findAll(String enterprise, Optional<String> country, Optional<String> noOfDays, Optional<String> store_num, Optional<String> invc_num){
		
		String[] countryArr={"DE","PT","SK","PL","IT","TR","HR","CZ","BG","NL","BE","ES","RU","FR","HU","RO","DK","GR","RS","MD","UA","IN","PK","JP","VN","CN","KZ"};
	 	String[] buId={"1-84WN","1-4TPWTY","1-4TPWTS","1-4TPWTW","1-MAWW0E","1-MAWW0I","1-TCPGOG","1-TCPGOC","1-TCPGOE","1-33NFRR2","1-33NFRR4","1-O2IZ","1-O2J7","1-O2IT","1-6I59PK","1-6I59PO","1-W46DZ9","1-W46DZ5","1-W46DZ7","1-3MIMP61","1-6I59PS","1-IAZH","1-IAYZ","1-FL8L7","1-FL8L9","1-4REV9D","1-4REV97"};
	 	
		HashMap<String, String> h= new HashMap<String, String>();
	 	for(int i=0;i < countryArr.length;i++){
	 		h.put(countryArr[i], buId[i]);
	 		
	 	}
	 	String sql="";
	 	String[] invcArr=invc_num.get().toString().split(",");
		String invoiceNum="";
	 	if(country.get().equals("undefined")){
	 		if(invc_num.get().equals("undefined")&& store_num.get().equals("undefined")){
	 		 sql="select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC, a.created as created ,b.created as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n" 
					+ "where cs.row_id = b.x_store_id \n" 
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n" 
					+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
					+ "and a.created>sysdate-"+noOfDays.get()+" \n ";
	 		 System.out.println(enterprise);
	 		 System.out.println("country store or invc tino null");
	 		}
	 		else{
	 			
	 			if(invcArr.length==1){
	 				invoiceNum=invcArr[0];
	 			}
	 			else{
	 			for(int i=0;i<invcArr.length-1;i++){
	 			 invoiceNum+=invcArr[i]+"','";
	 			}
	 			invoiceNum+=invcArr[invcArr.length-1];
	 			}
	 			sql="select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC, a.created as created ,b.created as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id \n"
						+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n" 
						+ "where cs.row_id = b.x_store_id \n" 
						+ "and b.accnt_id = cc.row_id \n"
						+ "and b.X_INVOICE_ID  = a.row_id \n" 
						+"and a.invc_num in('"+invoiceNum+"')  \n";
	 			System.out.println("country null or invc not null store null");
	 			System.out.println(invoiceNum);
	 		}
	 		
	 		
	 	}
	 	else{
	 		if(invc_num.get().equals("undefined")){
	 			if(store_num.get().equals("undefined")){
	 			sql="select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC, a.created as created ,b.created as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id \n"
						+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n" 
						+ "where cs.row_id = b.x_store_id \n" 
						+ "and b.accnt_id = cc.row_id \n"
						+ "and b.X_INVOICE_ID  = a.row_id \n" 
						+ "and a.created>sysdate-"+noOfDays.get()+" \n "
						+ "and b.bu_id='"+ h.get(country.get()) +"' \n"
						+"and a.X_ERROR_DESC <> 'Invoiced' \n";
	 			System.out.println("country not null invc  null store null");
	 			}
	 			else{
	 				sql="select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC, a.created as created ,b.created as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id \n"
							+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n" 
							+ "where cs.row_id = b.x_store_id \n" 
							+ "and b.accnt_id = cc.row_id \n"
							+ "and b.X_INVOICE_ID  = a.row_id \n" 
							+ "and a.created>sysdate-"+noOfDays.get()+" \n "
							+ "and b.bu_id='"+ h.get(country.get()) +"' \n"
							+ "and cs.ou_num='"+ store_num.get() +"' \n"
							+"and a.X_ERROR_DESC <> 'Invoiced' \n";
		 			System.out.println("country not null invc  null store not null");
	 				
	 			}
	 		}
	 		else{
	 			if(invcArr.length==1){
	 				invoiceNum=invcArr[0];
	 			}
	 			else{
	 			for(int i=0;i<invcArr.length-1;i++){
	 			 invoiceNum+=invcArr[i]+"','";
	 			}
	 			invoiceNum+=invcArr[invcArr.length-1];
	 			}
	 		 sql="select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC, a.created as created ,b.created as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n" 
					+ "where cs.row_id = b.x_store_id \n" 
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n" 
					+ "and b.bu_id='"+ h.get(country.get()) +"' \n"
					+"and a.invc_num in('"+invoiceNum+"')  \n";
	 		System.out.println(invoiceNum);
	 		 System.out.println("country or invc dono not null");
	 		}
			}
	 
		if(enterprise.equals("E1"))			
			 return jdbcTemplateOne.query("\n"+sql, new InvoiceRowMapper());
		
		if(enterprise.equals("E2"))			
		return jdbcTemplateTwo.query("\n"+sql, new InvoiceRowMapper());
			 
		return null;
		
	}

	public List<Invoice> findByInvcNum(String invcNum, String enterprise, String country) {
		// TODO Auto-generated method stub
		return null;
	}


	
	public List<Invoice> findByCountry(String enterprise, String country) {
		// TODO Auto-generated method stub
		
		
		return null;
	}

}