package com.example.demo.DAO;

import com.example.demo.entity.Phim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PhimDAO extends JpaRepository<Phim,Integer> {
    @Query("select o from  Phim o where  o.tieude = :tieude and  o.tacgia = :tacgia")
    Optional<Phim> findByPhim(String tieude, String tacgia);
}
