package com.metro.mcrm.app.mcrmApp.dao;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.metro.mcrm.app.mcrmApp.entity.Invoice;
import com.opencsv.CSVWriter;

@Repository
public class InvoiceJdbcDao {

	@Autowired
	@Qualifier("jdbcTemplateOne")
	private JdbcTemplate jdbcTemplateOne;

	@Autowired
	@Qualifier("jdbcTemplateTwo")
	private JdbcTemplate jdbcTemplateTwo;

	class InvoiceRowMapper implements RowMapper<Invoice> {

		public Invoice mapRow(ResultSet rs, int rowNum) throws SQLException {
			Invoice invoice = new Invoice();
			invoice.setInvc_num(rs.getString("invc_num"));
			invoice.setOrder_num(rs.getString("order_num"));
			invoice.setError_desc(rs.getString("X_ERROR_DESC"));
			invoice.setCreated(rs.getString("created"));
			invoice.setOrder_created(rs.getString("order_created"));
			invoice.setStore(rs.getString("store_num"));
			invoice.setCustomer_num(rs.getString("customer_num"));
			invoice.setStatus(rs.getString("status"));
			invoice.setOrder_status(rs.getString("order_status"));
			invoice.setBu_id(rs.getString("bu_id"));
			invoice.setRow_id(rs.getString("row_id"));
			return invoice;
		}

	}

	public List<Invoice> findAll(String enterprise, Optional<String> country,
			Optional<String> noOfDays, Optional<String> store_num,
			Optional<String> invc_num, Optional<String> order_num,
			Optional<String[]> status) {

		String[] countryArr = { "DE", "PT", "SK", "PL", "IT", "TR", "HR", "CZ",
				"BG", "NL", "BE", "ES", "RU", "FR", "HU", "RO", "DK", "GR",
				"RS", "MD", "UA", "IN", "PK", "JP", "VN", "CN", "KZ" };
		String[] buId = { "1-84WN", "1-4TPWTY", "1-4TPWTS", "1-4TPWTW",
				"1-MAWW0E", "1-MAWW0I", "1-TCPGOG", "1-TCPGOC", "1-TCPGOE",
				"1-33NFRR2", "1-33NFRR4", "1-O2IZ", "1-O2J7", "1-O2IT",
				"1-6I59PK", "1-6I59PO", "1-W46DZ9", "1-W46DZ5", "1-W46DZ7",
				"1-3MIMP61", "1-6I59PS", "1-IAZH", "1-IAYZ", "1-FL8L7",
				"1-FL8L9", "1-4REV9D", "1-4REV97" };
		String no_of_days = "3";

		HashMap<String, String> h = new HashMap<String, String>();
		for (int i = 0; i < countryArr.length; i++) {
			h.put(countryArr[i], buId[i]);

		}
		if (!(noOfDays.get() == "")) {
			no_of_days = noOfDays.get();
		}
		String invStatus = "";
		String invoiceNum = "";
		String orderNum = "";
		if (status.isPresent()) {
			if (status.get().length == 1)
				invStatus = status.get()[0];

			else {
				for (int i = 0; i < status.get().length - 1; i++) {
					invStatus += status.get()[i] + "','";
				}
				invStatus += status.get()[status.get().length - 1];
			}

		}

		if (invc_num.get() != null && invc_num.isPresent()) {
			String[] invcArr = invc_num.get().toString().split(",");

			if (invcArr.length == 1) {
				invoiceNum = invcArr[0];
			} else {
				for (int i = 0; i < invcArr.length - 1; i++) {
					invoiceNum += invcArr[i] + "','";
				}
				invoiceNum += invcArr[invcArr.length - 1];
			}
		}
		if (order_num.get() != null && order_num.isPresent()) {
			String[] orderArr = order_num.get().toString().split(",");

			if (orderArr.length == 1) {
				orderNum = orderArr[0];
			} else {
				for (int i = 0; i < orderArr.length - 1; i++) {
					orderNum += orderArr[i] + "','";
				}
				orderNum += orderArr[orderArr.length - 1];
			}
		}
		String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
				+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
				+ "where cs.row_id = b.x_store_id \n"
				+ "and b.accnt_id = cc.row_id \n"
				+ "and b.X_INVOICE_ID  = a.row_id \n"
				+ "and a.created>sysdate-" + no_of_days +" \n";

		HashMap<String, String> inputs = new HashMap<String, String>();
		inputs.put("b.bu_id", h.get(country.get()));
		inputs.put("cs.ou_num", store_num.get());
		inputs.put("a.invc_num", invoiceNum);
		inputs.put("b.order_num", orderNum);
		inputs.put("a.status_cd", invStatus);

		for (HashMap.Entry<String, String> entry : inputs.entrySet()) {
			// System.out.println(entry.getKey() + " = " + entry.getValue());
			if (!(entry.getValue() == null) && !entry.getValue().isEmpty()) {
				sql += "and " + entry.getKey() + " in('" + entry.getValue()
						+ "') \n";
			}
		}
		if(inputs.get("a.invc_num").isEmpty() && inputs.get("b.order_num").isEmpty() && inputs.get("a.status_cd").isEmpty()){
			sql += "and a.X_ERROR_DESC <> 'Invoiced' \n";
		}
		System.out.println(sql);
		if (enterprise.equals("E1"))
			return jdbcTemplateOne.query("\n" + sql, new InvoiceRowMapper());

		if (enterprise.equals("E2"))
			return jdbcTemplateTwo.query("\n" + sql, new InvoiceRowMapper());

		return null;

	}

	public void generatecsv() {
		// TODO public static void writeDataAtOnce(String filePath)

		// first create file object for file placed at location
		// specified by filepath
		String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
				+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
				+ "where cs.row_id = b.x_store_id \n"
				+ "and b.accnt_id = cc.row_id \n"
				+ "and b.X_INVOICE_ID  = a.row_id \n"
				+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
				+ "and a.created>sysdate-10 \n ";

		DateFormat df = new SimpleDateFormat("ddmmyyyy_HHmmss");
		Calendar calobj = Calendar.getInstance();

		File file_e1 = new File("T:/Kunde/04_CRM/Akash/UnprocessedInvoices/E1/"
				+ df.format(calobj.getTime()) + ".csv");

		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file_e1);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);
			// create a List which contains String array
			List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
					new InvoiceRowMapper());
			List<String[]> result = new ArrayList<String[]>();
			String[] nextLine = new String[10];
			nextLine[0] = "Invoice#";
			nextLine[1] = "Order_num";
			nextLine[2] = "Customer#";
			nextLine[3] = "Store";
			nextLine[4] = "Error_Desc";
			nextLine[5] = "InvStatus";
			nextLine[6] = "OrdStatus";
			nextLine[7] = "InvCreated";
			nextLine[8] = "OrdCreated";
			nextLine[9] = "bu_id";
			result.add(nextLine);
			for (int i = 1; i <= data.size(); i++) {
				String[] nextLine1 =new String[10]; 
				nextLine1[0] = data.get(i - 1).getInvc_num();
				nextLine1[1] = data.get(i - 1).getOrder_num();
				nextLine1[2] = data.get(i - 1).getCustomer_num();
				nextLine1[3] = data.get(i - 1).getStore();
				nextLine1[4] = data.get(i - 1).getError_desc();
				nextLine1[5] = data.get(i - 1).getStatus();
				nextLine1[6] = data.get(i - 1).getOrder_status();
				nextLine1[7] = data.get(i - 1).getCreated();
				nextLine1[8] = data.get(i - 1).getOrder_created();
				nextLine1[9] = data.get(i - 1).getBu_id();
				result.add(nextLine1);

			}
			writer.writeAll(result,false);
			System.out.println("T me check kar");
			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file_e2 = new File("T:/Kunde/04_CRM/Akash/UnprocessedInvoices/E2/"
				+ df.format(calobj.getTime()) + ".csv");

		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file_e2);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);
			// create a List which contains String array
			List<Invoice> data = jdbcTemplateTwo.query("\n" + sql,
					new InvoiceRowMapper());
			List<String[]> result = new ArrayList<String[]>();
			String[] nextLine = new String[10];
			nextLine[0] = "Invoice#";
			nextLine[1] = "Order_num";
			nextLine[2] = "Customer#";
			nextLine[3] = "Store";
			nextLine[4] = "Error_Desc";
			nextLine[5] = "InvStatus";
			nextLine[6] = "OrdStatus";
			nextLine[7] = "InvCreated";
			nextLine[8] = "OrdCreated";
			nextLine[9] = "bu_id";
			result.add(nextLine);			
			for (int i = 1; i <= data.size(); i++) {
				String[] nextLine1 =new String[10]; 
				nextLine1[0] = data.get(i - 1).getInvc_num();
				nextLine1[1] = data.get(i - 1).getOrder_num();
				nextLine1[2] = data.get(i - 1).getCustomer_num();
				nextLine1[3] = data.get(i - 1).getStore();
				nextLine1[4] = data.get(i - 1).getError_desc();
				nextLine1[5] = data.get(i - 1).getStatus();
				nextLine1[6] = data.get(i - 1).getOrder_status();
				nextLine1[7] = data.get(i - 1).getCreated();
				nextLine1[8] = data.get(i - 1).getOrder_created();
				nextLine1[9] = data.get(i - 1).getBu_id();
				result.add(nextLine1);

			}
			writer.writeAll(result,false);
			System.out.println("T me check kar e2");
			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file_e3 = new File("T:/Kunde/04_CRM/Akash/UnprocessedInvoices/E3/"
				+ df.format(calobj.getTime()) + ".csv");

		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file_e3);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);
			// create a List which contains String array
			List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
					new InvoiceRowMapper());
			List<String[]> result = new ArrayList<String[]>();
			String[] nextLine = new String[10];
			nextLine[0] = "Invoice#";
			nextLine[1] = "Order_num";
			nextLine[2] = "Customer#";
			nextLine[3] = "Store";
			nextLine[4] = "Error_Desc";
			nextLine[5] = "InvStatus";
			nextLine[6] = "OrdStatus";
			nextLine[7] = "InvCreated";
			nextLine[8] = "OrdCreated";
			nextLine[9] = "bu_id";
			result.add(nextLine);			
			for (int i = 1; i <= data.size(); i++) {
				String[] nextLine1 =new String[10]; 
				nextLine1[0] = data.get(i - 1).getInvc_num();
				nextLine1[1] = data.get(i - 1).getOrder_num();
				nextLine1[2] = data.get(i - 1).getCustomer_num();
				nextLine1[3] = data.get(i - 1).getStore();
				nextLine1[4] = data.get(i - 1).getError_desc();
				nextLine1[5] = data.get(i - 1).getStatus();
				nextLine1[6] = data.get(i - 1).getOrder_status();
				nextLine1[7] = data.get(i - 1).getCreated();
				nextLine1[8] = data.get(i - 1).getOrder_created();
				nextLine1[9] = data.get(i - 1).getBu_id();
				result.add(nextLine1);

			}
			writer.writeAll(result,false);
			System.out.println("T me check kar");
			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File file_e0 = new File("T:/Kunde/04_CRM/Akash/UnprocessedInvoices/E0/"
				+ df.format(calobj.getTime()) + ".csv");

		try {
			// create FileWriter object with file as parameter
			FileWriter outputfile = new FileWriter(file_e0);

			// create CSVWriter object filewriter object as parameter
			CSVWriter writer = new CSVWriter(outputfile);

			// create a List which contains String array
			List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
					new InvoiceRowMapper());
			List<String[]> result = new ArrayList<String[]>();
			String[] nextLine = new String[10];
			nextLine[0] = "Invoice#";
			nextLine[1] = "Order_num";
			nextLine[2] = "Customer#";
			nextLine[3] = "Store";
			nextLine[4] = "Error_Desc";
			nextLine[5] = "InvStatus";
			nextLine[6] = "OrdStatus";
			nextLine[7] = "InvCreated";
			nextLine[8] = "OrdCreated";
			nextLine[9] = "bu_id";
			result.add(nextLine);			
			for (int i = 1; i <= data.size(); i++) {
				String[] nextLine1 =new String[10]; 
				nextLine1[0] = data.get(i - 1).getInvc_num();
				nextLine1[1] = data.get(i - 1).getOrder_num();
				nextLine1[2] = data.get(i - 1).getCustomer_num();
				nextLine1[3] = data.get(i - 1).getStore();
				nextLine1[4] = data.get(i - 1).getError_desc();
				nextLine1[5] = data.get(i - 1).getStatus();
				nextLine1[6] = data.get(i - 1).getOrder_status();
				nextLine1[7] = data.get(i - 1).getCreated();
				nextLine1[8] = data.get(i - 1).getOrder_created();
				nextLine1[9] = data.get(i - 1).getBu_id();
				result.add(nextLine1);

			}
			writer.writeAll(result,false);
			System.out.println("T me check kar");
			// closing writer connection
			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void generate_infile() {
		String[] countryArr = { "DE", "PT", "SK", "PL", "IT", "TR", "HR", "CZ",
				"BG", "NL", "BE", "ES", "RU", "FR", "HU", "RO", "DK", "GR",
				"RS", "MD", "UA", "IN", "PK", "JP", "VN", "CN", "KZ" };

		String[] countryArrE0 = { "PT", "SK", "PL", "IT", "TR", "HR", "CZ",
				"BG", "NL", "BE" };
		String[] countryArrE1 = { "ES", "RU", "FR", "HU", "RO", "DK", "GR",
				"RS", "MD", "UA" };
		String[] countryArrE2 = { "IN", "PK", "JP", "VN", "CN", "KZ" };
		String[] countryArrE3 = { "DE" };
		String[] buId = { "1-84WN", "1-4TPWTY", "1-4TPWTS", "1-4TPWTW",
				"1-MAWW0E", "1-MAWW0I", "1-TCPGOG", "1-TCPGOC", "1-TCPGOE",
				"1-33NFRR2", "1-33NFRR4", "1-O2IZ", "1-O2J7", "1-O2IT",
				"1-6I59PK", "1-6I59PO", "1-W46DZ9", "1-W46DZ5", "1-W46DZ7",
				"1-3MIMP61", "1-6I59PS", "1-IAZH", "1-IAYZ", "1-FL8L7",
				"1-FL8L9", "1-4REV9D", "1-4REV97" };

		HashMap<String, String> h = new HashMap<String, String>();
		for (int i = 0; i < countryArr.length; i++) {
			h.put(countryArr[i], buId[i]);

		}
		for (String s : countryArrE1) {
			String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
					+ "where cs.row_id = b.x_store_id \n"
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n"
					+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
					+ "and a.created >sysdate-30 \n "
					+ "and b.bu_id = '"
					+ h.get(s) + "' \n";

			DateFormat df = new SimpleDateFormat("ddmmyyyy_HHmmss");
			Calendar calobj = Calendar.getInstance();

			File file_e1 = new File("T:/Kunde/04_CRM/Akash/Infiles/E1/" + s
					+ df.format(calobj.getTime()) + ".in");

			try {
				// create FileWriter object with file as parameter
				FileWriter outputfile = new FileWriter(file_e1);

				// create CSVWriter object filewriter object as parameter
				CSVWriter writer = new CSVWriter(outputfile,
						CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.NO_ESCAPE_CHARACTER,
						CSVWriter.RFC4180_LINE_END);

				// create a List which contains String array
				List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
						new InvoiceRowMapper());
				List<String[]> result = new ArrayList<String[]>();
				for (int i = 0; i < data.size(); i++) {
					String[] nextLine = { "run task for component WfProcBatchMgr with SearchSpec = \"([Id] = '" +data.get(i).getRow_id()+"' AND [Account Organization Id] = '"+data.get(i).getBu_id()+"'),ProcessName=\"DC_MG Billing Process - Send Invoice\"" };

					result.add(nextLine);

				}

				writer.writeAll(result);
				System.out.println("T me check kar");
				System.out.println(sql);

				// closing writer connection
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String s : countryArrE2) {
			String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
					+ "where cs.row_id = b.x_store_id \n"
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n"
					+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
					+ "and a.created >sysdate-30 \n "
					+ "and b.bu_id = '"
					+ h.get(s) + "' \n";

			DateFormat df = new SimpleDateFormat("ddmmyyyy_HHmmss");
			Calendar calobj = Calendar.getInstance();

			File file_e2 = new File("T:/Kunde/04_CRM/Akash/Infiles/E2/" + s
					+ df.format(calobj.getTime()) + ".in");

			try {
				// create FileWriter object with file as parameter
				FileWriter outputfile = new FileWriter(file_e2);

				// create CSVWriter object filewriter object as parameter
				CSVWriter writer = new CSVWriter(outputfile,
						CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.NO_ESCAPE_CHARACTER,
						CSVWriter.RFC4180_LINE_END);

				// create a List which contains String array
				List<Invoice> data = jdbcTemplateTwo.query("\n" + sql,
						new InvoiceRowMapper());
				List<String[]> result = new ArrayList<String[]>();
				for (int i = 0; i < data.size(); i++) {
					String[] nextLine = { "run task for component WfProcBatchMgr with SearchSpec = \"([Id] = '" +data.get(i).getRow_id()+"' AND [Account Organization Id] = '"+data.get(i).getBu_id()+"'),ProcessName=\"DC_MG Billing Process - Send Invoice\"" };
					result.add(nextLine);

				}
				writer.writeAll(result);
				System.out.println(sql);

				// closing writer connection
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String s : countryArrE3) {
			String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
					+ "where cs.row_id = b.x_store_id \n"
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n"
					+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
					+ "and a.created >sysdate-30 \n "
					+ "and b.bu_id = '"
					+ h.get(s) + "' \n";

			DateFormat df = new SimpleDateFormat("ddmmyyyy_HHmmss");
			Calendar calobj = Calendar.getInstance();

			File file_e3 = new File("T:/Kunde/04_CRM/Akash/Infiles/E3/" + s
					+ df.format(calobj.getTime()) + ".in");

			try {
				// create FileWriter object with file as parameter
				FileWriter outputfile = new FileWriter(file_e3);

				// create CSVWriter object filewriter object as parameter
				CSVWriter writer = new CSVWriter(outputfile,
						CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.NO_ESCAPE_CHARACTER,
						CSVWriter.RFC4180_LINE_END);

				// create a List which contains String array
				List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
						new InvoiceRowMapper());
				List<String[]> result = new ArrayList<String[]>();
				for (int i = 0; i < data.size(); i++) {
					String[] nextLine = { "run task for component WfProcBatchMgr with SearchSpec = \"([Id] = '" +data.get(i).getRow_id()+"' AND [Account Organization Id] = '"+data.get(i).getBu_id()+"'),ProcessName=\"DC_MG Billing Process - Send Invoice\"" };
					result.add(nextLine);

				}
				writer.writeAll(result);
				System.out.println("T me check kar");
				System.out.println(sql);

				// closing writer connection
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (String s : countryArrE0) {
			String sql = "select a.invc_num as invc_num, b.order_num as order_num, a.X_ERROR_DESC as X_ERROR_DESC,to_char(a.created, 'MM-DD-YYYY HH24:MI:SS') as created ,to_char(b.created, 'MM-DD-YYYY HH24:MI:SS') as order_created, cs.ou_num as store_num, cc.ou_num as customer_num ,a.status_cd as status,b.status_cd as order_status,b.bu_id as bu_id,a.row_id as row_id \n"
					+ "from siebel.s_invoice a, siebel.s_order b, siebel.s_org_ext cc, Siebel.s_org_ext cs \n"
					+ "where cs.row_id = b.x_store_id \n"
					+ "and b.accnt_id = cc.row_id \n"
					+ "and b.X_INVOICE_ID  = a.row_id \n"
					+ "and a.X_ERROR_DESC <> 'Invoiced' \n"
					+ "and a.created >sysdate-30 \n "
					+ "and b.bu_id = '"
					+ h.get(s) + "' \n";

			DateFormat df = new SimpleDateFormat("ddmmyyyy_HHmmss");
			Calendar calobj = Calendar.getInstance();

			File file_e0 = new File("T:/Kunde/04_CRM/Akash/Infiles/E0/" + s
					+ df.format(calobj.getTime()) + ".in");

			try {
				// create FileWriter object with file as parameter
				FileWriter outputfile = new FileWriter(file_e0);

				// create CSVWriter object filewriter object as parameter
				CSVWriter writer = new CSVWriter(outputfile,
						CSVWriter.DEFAULT_SEPARATOR,
						CSVWriter.NO_QUOTE_CHARACTER,
						CSVWriter.NO_ESCAPE_CHARACTER,
						CSVWriter.RFC4180_LINE_END);

				// create a List which contains String array
				List<Invoice> data = jdbcTemplateOne.query("\n" + sql,
						new InvoiceRowMapper());
				List<String[]> result = new ArrayList<String[]>();
				for (int i = 0; i < data.size(); i++) {
					String[] nextLine = { "run task for component WfProcBatchMgr with SearchSpec = \"([Id] = '" +data.get(i).getRow_id()+"' AND [Account Organization Id] = '"+data.get(i).getBu_id()+"'),ProcessName=\"DC_MG Billing Process - Send Invoice\"" };
					result.add(nextLine);

				}
				writer.writeAll(result);
				System.out.println(sql);

				// closing writer connection
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
