package com.java456.controller.houtai;


import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.java456.util.ExportExcel;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.java456.dao.ClientDao;
import com.java456.entity.Client;
import com.java456.util.ResponseUtil;


@Controller
@RequestMapping("/houtai/client")
public class HouTai_Client_Controller {

    @Resource
    private ClientDao clientDao;

    @Resource
    private ExportExcel exportExcel;


    /**
     * /houtai/client/manage
     */
    @RequestMapping("/manage")
    public ModelAndView manage() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/page/client/client_manage");
        return mav;
    }

    /**
     * /houtai/client/add
     */
    @RequestMapping("/add")
    public ModelAndView add() throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("btn_text", "添加");
        mav.addObject("save_url", "/admin/client/add");
        mav.setViewName("/page/client/add_update");
        return mav;
    }

    /**
     * /houtai/client/edit?id=1
     */
    @RequestMapping("/edit")
    public ModelAndView edit(@RequestParam(value = "id", required = false) Integer id, HttpServletResponse response,
                             HttpServletRequest request) throws Exception {
        ModelAndView mav = new ModelAndView();
        Client client = clientDao.findId(id);
        mav.addObject("client", client);
        mav.addObject("btn_text", "修改");
        mav.addObject("save_url", "/admin/client/update?id=" + id);
        mav.setViewName("/page/client/add_update");
        return mav;
    }

    /**
     * 下载客户资料    导出 excel 使用我们的模板导出
     * /houtai/client/excel_down
     */
    @RequestMapping("/excel_down")
    public String excel_down(HttpServletResponse response, HttpServletRequest request)
            throws Exception {
        String webPath = request.getServletContext().getRealPath("/");
        List<Client> list = clientDao.findAll();

        Workbook wb = exportExcel.fillExcel(list, webPath + "/static/client_down_model.xls");

        ResponseUtil.export(response, wb, "客户.xls");
        return null;
    }


}
