package com.mt4agents.web.views.excel.clients;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import com.mt4agents.dto.MT4TradeDTO;
import com.mt4agents.web.views.excel.MT4TradeExcelView;

public class AgentClientsCloseTradesExcelView extends MT4TradeExcelView {

	public AgentClientsCloseTradesExcelView() {
		initExcelDataModel();
	}

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			Workbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		super.buildExcelDocument(model, workbook, request, response);
		tradesSheet.setColumnWidth(6, MT4TradeExcelView.EXCEL_DATE_WIDTH);
		tradesSheet.setColumnWidth(10, MT4TradeExcelView.EXCEL_DATE_WIDTH);
		tradesSheet.setColumnWidth(22, MT4TradeExcelView.EXCEL_DATE_WIDTH);
		tradesSheet.setColumnWidth(23, MT4TradeExcelView.EXCEL_DATE_WIDTH);
	}

	/**
	 * I am not particularly proud of this design because it involves repetition
	 * of constructs.
	 * 
	 * TODO: Research a way to map a String to a Java function. Java 8 Lambda
	 * looks promising.
	 */
	private void initExcelDataModel() {
		int cellPosition = 0;
		addDataAttribute("TICKET", new MT4TradeExcelView.CellValueSetter(
				cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getTicket());
			}
		});
		addDataAttribute("LOGIN", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getLogin());
			}
		});
		addDataAttribute("SYMBOL", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getSymbol());
			}
		});
		addDataAttribute("DIGITS", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getDigits());
			}
		});
		addDataAttribute("CMD", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getCmd());
			}
		});
		addDataAttribute("VOLUME", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getVolume());
			}
		});
		addDataAttribute("OPEN_TIME", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper()
						.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
				cell.setCellValue(tradeDTO.getOpenTime());
				cell.setCellStyle(cellStyle);
			}
		});
		addDataAttribute("OPEN_PRICE", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getOpenPrice());
			}
		});
		addDataAttribute("SL", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getSl());
			}
		});
		addDataAttribute("TP", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getTp());
			}
		});
		addDataAttribute("CLOSE_TIME", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper()
						.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
				cell.setCellValue(tradeDTO.getCloseTime());
				cell.setCellStyle(cellStyle);
			}
		});
		addDataAttribute("CLOSE_PRICE", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getClosePrice());
			}
		});
		addDataAttribute("EXPIRATION", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getExpiration());
			}
		});
		addDataAttribute("CONV_RATE1", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getConvRate1());
			}
		});
		addDataAttribute("CONV_RATE2", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getConvRate2());
			}
		});
		addDataAttribute("COMMISSION_AGENT",
				new CellValueSetter(cellPosition++) {
					@Override
					public void setValue(MT4TradeDTO tradeDTO,
							Workbook workbook, Row row) {
						Cell cell = row.createCell(getCellPosition());
						cell.setCellValue(tradeDTO.getCommissionAgent());
					}
				});
		addDataAttribute("SWAPS", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getSwaps());
			}
		});
		addDataAttribute("PROFIT", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getProfit());
			}
		});
		addDataAttribute("TAXES", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getTaxes());
			}
		});
		addDataAttribute("COMMENT", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getComment());
			}
		});
		addDataAttribute("INTERNAL_ID", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getInternalID());
			}
		});
		addDataAttribute("MARGIN_RATE", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getMarginRate());
			}
		});
		addDataAttribute("TIMESTAMP", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper()
						.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
				cell.setCellValue(new Date(tradeDTO.getTimestamp()));
				cell.setCellStyle(cellStyle);
			}
		});
		addDataAttribute("MODIFY_TIME", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.setDataFormat(workbook.getCreationHelper()
						.createDataFormat().getFormat("yyyy-mm-dd hh:mm"));
				cell.setCellValue(tradeDTO.getModifyTime());
				cell.setCellStyle(cellStyle);
			}
		});
		addDataAttribute("COMMISSION", new CellValueSetter(cellPosition++) {
			@Override
			public void setValue(MT4TradeDTO tradeDTO, Workbook workbook,
					Row row) {
				Cell cell = row.createCell(getCellPosition());
				cell.setCellValue(tradeDTO.getCommission());
			}
		});
	}
}
