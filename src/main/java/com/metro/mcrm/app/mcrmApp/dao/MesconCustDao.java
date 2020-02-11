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

import com.metro.mcrm.app.mcrmApp.entity.MesconCustomer;
@Repository
public class MesconCustDao {
	
	
 	@Autowired
 	@Qualifier("jdbcTemplateFifth")
    private JdbcTemplate jdbcTemplateFifth;
 	
 	class MesconCustRowMapper implements RowMapper<MesconCustomer>{
 		public MesconCustomer mapRow(ResultSet rs,int row_num)throws SQLException{
 			MesconCustomer mesCus=new MesconCustomer();
 			mesCus.setInsert_time_stamp(rs.getString("INSERTTIMESTAMP"));
 			mesCus.setCountry(rs.getString("COUNTRY"));
 			mesCus.setStore_Num(rs.getInt("STORE"));
 			mesCus.setOperation(rs.getString("OPERATION"));
 			mesCus.setProcessing_cd(rs.getInt("PROCESSING_CD"));
 			mesCus.setPayload(rs.getString("PAYLOAD"));
 			mesCus.setKey1(rs.getString("KEY1"));
 			mesCus.setKey4(rs.getString("KEY4"));
 			mesCus.setError_msg(rs.getString("ERROR_MSG"));
 			mesCus.setProcessed_time(rs.getString("PROCESSED_TIME"));
 			
 			return mesCus;
 		}
 	}
 	 	
 	public List<MesconCustomer> findAllData(String entityType,Optional<String> country,Optional<String> store,
 			Optional<String> operation,Optional<String[]> ProcessingCd,Optional<String> payload,
 			Optional<String> key1,Optional<String> key4,Optional<String> noOfDays){
		
 		String store_num="";
 		String key_one="";
 		String no_of_days = "1";
 		if (!(noOfDays.get() == "")) {
			no_of_days = noOfDays.get();
		}
 		String sql="SELECT to_char(INSERTTIMESTAMP,'DD-MM-YYYY HH24:MI:SS') as INSERTTIMESTAMP,COUNTRY,STORE,OPERATION,PROCESSING_CD,PAYLOAD,KEY1,KEY4,ERROR_MSG,to_char(PROCESSED_TIME, 'DD-MM-YYYY HH24:MI:SS') as PROCESSED_TIME FROM \n"
				+ entityType+ ".MSB_XML_IN where payload is not null " 
				+ "and INSERTTIMESTAMP >sysdate-" + no_of_days +" \n";
		String Process_cd="";
		if(ProcessingCd.isPresent()){
			for(int i=0; i<ProcessingCd.get().length-1; i++){
				Process_cd+=ProcessingCd.get()[i].substring(0,1)+ "','";
			}
			Process_cd+=ProcessingCd.get()[ProcessingCd.get().length-1].substring(0,1);
		}
		
		if (store.get() != null && store.isPresent()) {
			String[] storeArr = store.get().toString().split(",");

			if (storeArr.length == 1) {
				store_num = storeArr[0];
			} else {
				for (int i = 0; i < storeArr.length - 1; i++) {
					store_num += storeArr[i] + "','";
				}
				store_num += storeArr[storeArr.length - 1];
			}
		}
		
		if(key1.get()!=null && key1.isPresent()){
			String[] keyOneArr=key1.get().toString().split(",");
			if(keyOneArr.length==1){
				key_one=keyOneArr[0];
			}else{
				for(int i=0;i<keyOneArr.length-1;i++){
					key_one+=keyOneArr[i]+"','";
				}
				key_one+=keyOneArr[keyOneArr.length-1];
			}
		}
		
 		HashMap<String, String> inputs = new HashMap<String, String>();
		inputs.put("country", country.get());
		inputs.put("store", store_num);
		System.out.println(operation);
		System.out.println(operation.get()+"With Get");
		inputs.put("operation", operation.get());
		inputs.put("processing_cd", Process_cd);
		inputs.put("payload", payload.get());
		inputs.put("key1",key_one);
		inputs.put("key4",key4.get());
		
		
		for (HashMap.Entry<String, String> entry : inputs.entrySet()) {
			// System.out.println(entry.getKey() + " = " + entry.getValue());
			if (!(entry.getValue() == null) && !(entry.getValue().isEmpty()) && !((entry.getKey()== "payload")|| (entry.getKey()=="key4") )) {
				sql += "and " + entry.getKey() + " in('" + entry.getValue()
						+ "') \n";
			}else if(!(entry.getValue() == null) && !(entry.getValue().isEmpty()) && ((entry.getKey()== "payload")|| (entry.getKey()=="key4") )){
				sql += "and " + entry.getKey() + " like '%" + entry.getValue()
						+ "%' \n";
			}
			
		}
		System.out.println(sql);
		return jdbcTemplateFifth.query("\n"+sql, new MesconCustRowMapper());
 	}
 	
}
