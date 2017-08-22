package com.example.demo.web.util;

import java.util.Collections;
import java.util.List;

import com.example.demo.bean.Page;
import com.example.demo.web.datatables.DataTablesResponse;

public abstract class DataTablesUtils {

	public static void setDtResponseFromPage(Page<?> page, DataTablesResponse dtResponse) {
		List<?> data = page.getData();
		dtResponse.setData(data == null ? Collections.EMPTY_LIST : data);
		dtResponse.setRecordsFiltered(page.getTotal());
		dtResponse.setRecordsTotal(page.getTotal());
	}

}
