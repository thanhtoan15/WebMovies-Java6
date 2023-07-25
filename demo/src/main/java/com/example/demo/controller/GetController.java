package com.example.demo.controller;

import com.example.demo.DAO.*;
import com.example.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GetController {
    @Autowired
    NhanVienDAO dao;
    @Autowired
    PhimDAO phimdao;

    @Autowired
    PhongChieuDAO phongChieudao;

    @Autowired
    DatChoDAO datChoDao;
    @Autowired
    SuatChieuDAO suatChieuDAO;

    @Autowired
    GheDatChoDAO gheDatChoDAO;

    @Autowired
    GheDAO gheDAO;
    @GetMapping("/get/index")
    public List<Ghe> getAll(){
        List<Ghe> list = gheDAO.findAll() ;
        return list;
    }

    @RequestMapping("/ciner")
    public String index(){
        return "index";
    }
}
