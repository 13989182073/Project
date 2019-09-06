package com.java456.util;

import com.java456.entity.Client;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImportExcel {

    public List<Client> excel_to_clientInfo(File userUploadFile) throws ParseException {
        List<Client> list = new ArrayList<Client>();
        Client client = null;
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(userUploadFile));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            //获取第一个sheet页
            HSSFSheet sheet = wb.getSheetAt(0);
            if (sheet != null) {
                for (int rowNum = 1; rowNum <= sheet.getLastRowNum(); rowNum++) {
                    HSSFRow row = sheet.getRow(rowNum);
                    if (row == null) {
                        continue;
                    }
                    client = new Client();
                    //去掉编码中的  .0 如果全是数字 后面有.0
                    String bianma = ExcelUtil.formatCell(row.getCell(1));
                    client.setBianhao(bianma);
                    client.setName(ExcelUtil.formatCell(row.getCell(2)).split("\\.")[0]);
                    client.setPhone(ExcelUtil.formatCell(row.getCell(3)));
                    client.setRemark(ExcelUtil.formatCell(row.getCell(4)));
                    client.setCreateDateTime(ExcelUtil.formatDate(row.getCell(5)));
                    client.setTest(ExcelUtil.formatCell(row.getCell(6)));
                    list.add(client);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

}
