package com.java456.util;

import com.java456.entity.Client;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExportExcel {
    /**
     * @param templateFileUrl excel模板的路径 /admin/page/JYZ/client/client_info.xls
     * @return
     */
    public static Workbook fillExcel(List<Client> list, String templateFileUrl) {

        Workbook wb = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(templateFileUrl));
            wb = new HSSFWorkbook(fs);
            // 取得 模板的 第一个sheet 页
            Sheet sheet = wb.getSheetAt(0);
            // 拿到sheet页有 多少列
            int cellNums = sheet.getRow(0).getLastCellNum();
            // 从第2行 开搞    下标1  就是第2行
            int rowIndex = 1;
            Row row;
            for (Client client : list) {
                row = sheet.createRow(rowIndex);
                rowIndex++;
                row.createCell(0).setCellValue(client.getId());

                row.createCell(1).setCellValue(client.getBianhao());
                row.createCell(2).setCellValue(client.getName());
                row.createCell(3).setCellValue(client.getPhone());
                row.createCell(4).setCellValue(client.getRemark());
                row.createCell(5).setCellValue(DateUtil.formatDate(client.getCreateDateTime(), "yyyy-MM-dd HH:mm:ss"));
                row.createCell(6).setCellValue("test");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }
}
