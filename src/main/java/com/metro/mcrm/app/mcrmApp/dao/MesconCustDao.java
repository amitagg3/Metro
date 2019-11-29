package com.metro.mcrm.app.mcrmApp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
 			mesCus.setInsert_time_stamp(rs.getDate("INSERTTIMESTAMP"));
 			mesCus.setCountry(rs.getString("COUNTRY"));
 			mesCus.setStore_Num(rs.getInt("STORE"));
 			mesCus.setOperation(rs.getString("OPERATION"));
 			mesCus.setProcessing_cd(rs.getInt("PROCESSING_CD"));
 			mesCus.setPayload(rs.getString("PAYLOAD"));
 			return mesCus;
 		}
 	}
 	public List<MesconCustomer> findAllCustomer(){
 		String sql="SELECT INSERTTIMESTAMP,COUNTRY,STORE,OPERATION,PROCESSING_CD,PAYLOAD \n"
 				+ "FROM MESCON_CUSTOMER.MSB_XML_IN \n"
 				+ "WHERE ROWNUM<20 \n";
 		return jdbcTemplateFifth.query("\n"+sql, new MesconCustRowMapper());
 		/*if(enterprise.equals("E1"))
 			return jdbcTemplateOne.query("\n"+sql, new MesconCustRowMapper());
 		if(enterprise.equals("E2"))
 			return jdbcTemplateTwo.query("\n"+sql, new MesconCustRowMapper());
 		return null;*/
 	}

}
