package com.mt4agents.web.views.excel;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mt4agents.dto.MT4TradeDTO;
import com.mt4agents.services.MT4RemoteService.TradeType;

public abstract class MT4TradeExcelView extends AbstractExcelXView {
	
	public static final int EXCEL_DATE_WIDTH = 4300;
	
	protected Sheet tradesSheet;

	public abstract static class CellValueSetter {
		private int cellPosition;

		public CellValueSetter(int cellPosition) {
			this.cellPosition = cellPosition;
		}

		public int getCellPosition() {
			return this.cellPosition;
		}

		public abstract void setValue(MT4TradeDTO tradeDTO, Workbook workbook, Row row);
	}
	
	private Map<String, CellValueSetter> excelDataModel = new LinkedHashMap<String, CellValueSetter>();

	@Override
	protected Workbook createWorkbook() {
		return new XSSFWorkbook();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		@SuppressWarnings("unchecked")
		List<MT4TradeDTO> clientTrades = (List<MT4TradeDTO>) model
				.get("clientTrades");
		TradeType tradeType = (TradeType) model.get("tradeType");

		// http://stackoverflow.com/questions/15278505/spring-java-config-for-excel-view-resolver
		tradesSheet = workbook.createSheet(tradeType.name() + " Trades");
		createHeaderRow(tradesSheet);
		
		Set<String> dataModelAttributes = excelDataModel.keySet();

		int i = 1;
		for (MT4TradeDTO tradeDTO : clientTrades) {
			Row tradeRow = tradesSheet.createRow(i++);
			for (String attribute : dataModelAttributes) {
				excelDataModel.get(attribute).setValue(tradeDTO, workbook,
						tradeRow);
			}
		}

		tradesSheet.createFreezePane(0, 1);
	}

	protected void addDataAttribute(String attributeName,
			CellValueSetter cellValueSetter) {
		excelDataModel.put(attributeName, cellValueSetter);
	}

	private Row createHeaderRow(Sheet sheet) {
		Set<String> dataModelAttributes = excelDataModel.keySet();
		Row headerRow = sheet.createRow(0);
		int i = 0;
		for (String attribute : dataModelAttributes) {
			Cell cell = headerRow.createCell(i++);
			cell.setCellValue(attribute);
		}
		return headerRow;
	}

}
//trapti gupta railsbox