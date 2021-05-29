package backend_bboss.demo.Models;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import backend_bboss.demo.Dto.ListCategorie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
public class ScategorieExcel {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ListCategorie> listCategories;
     
    public ScategorieExcel(List<ListCategorie> listCategories2) {
        this.listCategories = listCategories2;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("SCategoriess");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
        createCell(row, 0, "Code Sous catégorie", style);  
        createCell(row, 1, "Libelle Sous catégorie", style);  
        createCell(row, 2, "Libelle Catégorie", style);    
        createCell(row, 3, "Code Catégorie", style);
         
    }
     
    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        }else {
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
                 
        for (ListCategorie cat : listCategories) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            
            createCell(row, columnCount++, cat.getCode(), style);
            createCell(row, columnCount++, cat.getLibelle(), style);
            createCell(row, columnCount++, cat.getLibcateg(), style);
            createCell(row, columnCount++, cat.getCcateg(), style);
             
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
