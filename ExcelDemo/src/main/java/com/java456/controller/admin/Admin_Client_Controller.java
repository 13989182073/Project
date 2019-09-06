package com.java456.controller.admin;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.java456.util.*;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.java456.dao.ClientDao;
import com.java456.entity.Client;

import net.sf.json.JSONObject;


@Controller
@RequestMapping("/admin/client")
public class Admin_Client_Controller {

    @Resource
    private ClientDao clientDao;
    @Autowired
    private ImportExcel importExcel;

    /**
     * /admin/client/add
     */
    @ResponseBody
    @RequestMapping("/add")
    public JSONObject add(@Valid Client client, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();
        if (bindingResult.hasErrors()) {
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        } else {
            client.setCreateDateTime(new Date());
            clientDao.save(client);
            result.put("success", true);
            result.put("msg", "添加成功");
            return result;
        }
    }

    /**
     * /admin/client/update
     */
    @ResponseBody
    @RequestMapping("/update")
    public JSONObject update(@Valid Client client, BindingResult bindingResult) throws Exception {
        JSONObject result = new JSONObject();
        if (bindingResult.hasErrors()) {
            result.put("success", false);
            result.put("msg", bindingResult.getFieldError().getDefaultMessage());
            return result;
        } else {
            //时间不更新
            Client temp = clientDao.findId(client.getId());
            client.setCreateDateTime(temp.getCreateDateTime());
            //时间不更新

            clientDao.save(client);
            result.put("success", true);
            result.put("msg", "添加成功");
            return result;
        }
    }


    /**
     * /admin/client/list
     *
     * @param page  默认1
     * @param limit 数据多少
     */
    @ResponseBody
    @RequestMapping("/list")
    public Map<String, Object> list(@RequestParam(value = "page", required = false) Integer page,
                                    @RequestParam(value = "limit", required = false) Integer limit,
                                    HttpServletResponse response,
                                    HttpServletRequest request) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        List<Client> clientList = clientDao.findAll(new PageRequest(page - 1, limit)).getContent();
        long total = clientDao.count();
        map.put("data", clientList);
        map.put("count", total);
        map.put("code", 0);
        map.put("msg", "");
        return map;
    }

    /**
     * /admin/client/delete
     */
    @ResponseBody
    @RequestMapping("/delete")
    public JSONObject delete(@RequestParam(value = "ids", required = false) String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        JSONObject result = new JSONObject();

        for (int i = 0; i < idsStr.length; i++) {
            clientDao.deleteById(Integer.parseInt(idsStr[i]));
        }
        result.put("success", true);
        return result;
    }


    /**
     * 接受文件   解析  上传资料。
     * /admin/client/upload_excel
     */
    @ResponseBody
    @RequestMapping("/upload_excel")
    public JSONObject upload_excel(@RequestParam("file") MultipartFile file, HttpServletResponse response, HttpServletRequest request) throws Exception {
        JSONObject result = new JSONObject();

        System.out.println(file.getOriginalFilename());

        if (!file.isEmpty()) {
            String webPath = request.getServletContext().getRealPath("");
            String filePath = "/static/upload_file/excel/";
            //把文件名子换成（时间搓.png）
            // String imageName="houtai_logo."+file.getOriginalFilename().split("\\.")[1];
            String fileName = DateUtil.formatDate(new Date(), "yyyyMMdd-HHmmssSSS") + "_" + file.getOriginalFilename();

            FileUtil.makeDirs(webPath + filePath);
            //保存服务器
            file.transferTo(new File(webPath + filePath + fileName));
            //保存服务器
            //解析 
            List<Client> list = importExcel.excel_to_clientInfo(new File(webPath + filePath + fileName));
            //解析
            //开始 上传 数据库
            for (Client client : list) {
                clientDao.save(client);
            }
            //开始 上传 数据库

            //删除用过的文件 
            FileUtil.deleteFile(webPath + filePath + fileName);
            //删除用过的文件 
        }
        result.put("success", true);
        result.put("msg", "导入成功");
        return result;
    }


}
