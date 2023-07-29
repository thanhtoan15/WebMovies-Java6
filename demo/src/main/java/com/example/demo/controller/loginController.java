package com.example.demo.controller;

import com.example.demo.DAO.NhanVienDAO;
import com.example.demo.entity.NhanVien;
import com.example.demo.service.EmailService;
import com.example.demo.until.CookieService;
import com.example.demo.until.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class loginController {
    @Autowired
    NhanVienDAO dao;

    @Autowired
    SessionService sessionService;

    @Autowired
    CookieService cookieService;

    @Autowired
    EmailService emailService;

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @PostMapping("/login")
    public String postLogin(Model model,
                            @RequestParam("tendangnhap") String tendangnhap,
                            @RequestParam("matkhau") String matkhau,
                            @RequestParam(value = "remember",required = false) Boolean remember){
        Optional<NhanVien> nhanvien_kiemtra = dao.findByNhanVien(tendangnhap, matkhau);
        if (nhanvien_kiemtra.isPresent()){
            System.out.println("ten nhan vien"+nhanvien_kiemtra.get().getTendangnhap());
            NhanVien nhanvien_dangnhap = nhanvien_kiemtra.get();
            if (remember != null){
                cookieService.add("ten dang nhap",tendangnhap,1);
                cookieService.add("mat khau",matkhau,1);
                sessionService.addsession("Dang nhap",nhanvien_dangnhap);
                cookieService.add("remember",Boolean.toString(remember),1);
                model.addAttribute("success","Login thành công");
                return "index";
            }else{
                sessionService.addsession("nhan vien dang nhap",nhanvien_dangnhap);
                model.addAttribute("success","Login thành công");
                return "index";
            }
        }else {
            System.out.println("user"+nhanvien_kiemtra.get().getTendangnhap());
            model.addAttribute("Ten dang nhap !!!",tendangnhap);
            model.addAttribute("mat khau",matkhau);
            model.addAttribute("Error","Login faile");
            return "login";
        }
    }
    @GetMapping("/register")
    public String getRegister(@ModelAttribute("nhanvien_register")NhanVien nhanvien){
        return "register";
    }
    @PostMapping("/register")
    public String postRegiter(Model model,
                              @ModelAttribute("nhanvien_register")NhanVien nhanVien_Register,
                              @RequestParam("repeatPass")String repeatPass){
        if(nhanVien_Register.getMatkhau().equals(repeatPass)){
            if(dao.findByNhanVienByTenDangNhap(nhanVien_Register.getTendangnhap()).isPresent()){
                model.addAttribute("erorr","Tên đăng nhập đã được sử dụng");
                return "register";
            }else {
                dao.save(nhanVien_Register);
                model.addAttribute("success","Đăng ký tài khoản thành công");
                return "register";
            }
        }else {
            model.addAttribute("erorr","Mật khẩu không hợp, hãy xem lại");
            return "register";
        }
    }

    @GetMapping("/changepassword")
    public String changePassWord(){
        return "changepassword";
    }
    @PostMapping("changepassword")
    public String changePassWord(Model model,
                                 @ModelAttribute("nhanvien_changepass")NhanVien nhanVien_changepass,
                                 @RequestParam("tendangnhap")String tendangnhap,
                                 @RequestParam("matkhau")String matkhau,
                                 @RequestParam("repeatPass")String repeatPass,
                                 @RequestParam("repeatPassagain")String repeatPassagain){
        Optional<NhanVien> nhanvien_thaydoi = dao.findByNhanVien(tendangnhap, matkhau);
        if(nhanvien_thaydoi.isPresent()){
            System.out.println("ten dang nhap"+nhanvien_thaydoi.get().getTendangnhap());
            NhanVien nhanvien_matkhau = nhanvien_thaydoi.get();
            if(nhanVien_changepass.getTendangnhap().equals(tendangnhap)){
                if(nhanVien_changepass.getMatkhau().equals(matkhau)){
                    if(repeatPass.equals(repeatPassagain)){
                        nhanvien_matkhau.setMatkhau(repeatPass);
                        dao.save(nhanvien_matkhau);
                        model.addAttribute("success","Thay đổi mật khẩu thành công");
                        return "changepassword";
                    }else {
                        model.addAttribute("error","Mật khẩu không trùng khớp");
                        return "changepassword";
                    }
                }else {
                    model.addAttribute("erorr","Mật khẩu cũ không tồn tại");
                    return "changepassword";
                }
            }else {
                model.addAttribute("erorr","Tên đăng nhập không tồn tại");
                return "changepassword";
            }
        }else {
            model.addAttribute("erorr","Không tìm thấy nhân viên này");
            return "changepassword";
        }
    }

}
