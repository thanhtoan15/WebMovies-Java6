package com.example.demo.controller;

import com.example.demo.DAO.PhimDAO;
import com.example.demo.entity.Phim;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

@Controller
public class updateMovie {

    @Autowired
    PhimDAO DAO;
    @GetMapping("/update")
    public String getMovie(Model model){
        return "capnhatphim";
    }
    @PostMapping("/update")
    public String postMovie(Model model,
                            @ModelAttribute("phim_update")Phim phim_update,
                            @RequestParam("photo") MultipartFile photo){
        Phim movies = new Phim();
        if(photo.isEmpty()){
            model.addAttribute("error","thiếu hình ảnh");
            System.out.println("thiếu ảnh");
        }else {
            DAO.save(phim_update);
            System.out.println("Save Successfull!!");
        }
        Path path = Paths.get("./resources/static/uploads/");
        try {
            InputStream inputStream = photo.getInputStream();
            Files.copy(inputStream,path.resolve(photo.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);
            movies.setHinhanh(photo.getOriginalFilename().toLowerCase());
            model.addAttribute("PhoTo",photo);
        }catch (Exception e){
            e.printStackTrace();
        }
        return "capnhatphim";
    }
    @GetMapping("/delete")
    public String getDelete(){
        return "deletephim";
    }

    @PostMapping("/delete")
    public String delete(Model model,
                         @RequestParam("tieude")String tieude,
                         @RequestParam("tacgia")String tacgia){
        Optional<Phim> phim_kiemtra = DAO.findByPhim(tieude,tacgia);
        if (phim_kiemtra.isPresent()){
            System.out.println("tiêu đề "+phim_kiemtra.get().getTieude());
            Phim phim_success = phim_kiemtra.get();
            if(phim_success.getTieude().equals(tieude) && phim_success.getTacgia().equals(tacgia)){
                DAO.delete(phim_success);
            }
        }
        return "deletephim";
    }
}
