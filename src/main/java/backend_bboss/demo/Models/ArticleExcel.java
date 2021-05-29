package backend_bboss.demo.Models;


import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import backend_bboss.demo.Dto.ListArticle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ArticleExcel {
	private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ListArticle> listArticles;
     
    public ArticleExcel(List<ListArticle> listArticles2) {
        this.listArticles = listArticles2;
        workbook = new XSSFWorkbook();
    }
 
 
    private void writeHeaderLine() {
        sheet = workbook.createSheet("Articles");
         
        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        font.setFontHeight(16);
        style.setFont(font);
       
        createCell(row, 0, "Code", style);  
        createCell(row, 1, "Libelle", style);  
        createCell(row, 2, "Sous Catégorie", style);    
        createCell(row, 3, "Catégorie", style);
        createCell(row, 4, "Prix Achat", style);  
        createCell(row, 5, "Prix Vente", style);  
        createCell(row, 6, "Tva", style);    
        createCell(row, 7, "Stock", style); 
        createCell(row, 8, "Code Four", style); 
        createCell(row, 9, "Fournisseur", style); 
       
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
                 
        for (ListArticle art  : listArticles) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
             
            
            createCell(row, columnCount++, art.getCode(), style);
            createCell(row, columnCount++, art.getLibelle(), style);
            createCell(row, columnCount++, art.getScateg(), style);
            createCell(row, columnCount++, art.getCateg(), style);
            createCell(row, columnCount++, String.valueOf(art.getPa()), style);
            createCell(row, columnCount++, String.valueOf(art.getPv()), style);
            createCell(row, columnCount++, String.valueOf(art.getTva()), style);
            createCell(row, columnCount++, String.valueOf(art.getStock()), style);
            createCell(row, columnCount++, String.valueOf(art.getCodef()), style);
            createCell(row, columnCount++, art.getFour(), style);
             
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
