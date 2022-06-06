package com.lti.todosmanagement.util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.lti.todosmanagement.entity.Todo;
 

public class TodoExcelExporter {

	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<Todo> todo;
     
    public TodoExcelExporter(List<Todo> todo) {
        this.todo = todo;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Todos");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
         
        createCell(row, 0, "Todo ID", style);      
        createCell(row, 1, "user name", style);       
        createCell(row, 2, "Description", style);    
        createCell(row, 3, "Date", style);
        createCell(row, 4, "isCompleted", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Long) {
            cell.setCellValue((Long) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        }else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }
        else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }
     
    private void writeDataLines() {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(14);
        style.setFont(font);
                 
        for (Todo ToDo : todo) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            createCell(row, columnCount++, ToDo.getId(), style);
            createCell(row, columnCount++, ToDo.getUsername(), style);
            createCell(row, columnCount++, ToDo.getDescription(), style);
           createCell(row, columnCount++, ToDo.getTargetDate().toString(), style);
           createCell(row, columnCount++, ToDo.isDone(), style);
             
        }
    }
     
    public void export(HttpServletResponse response) throws IOException {
        writeHeaderLine();
        writeDataLines();
         
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
         
        outputStream.close();
         
    }
}
