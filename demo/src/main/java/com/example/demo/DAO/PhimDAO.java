package com.example.demo.DAO;

import com.example.demo.entity.Phim;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhimDAO extends JpaRepository<Phim,Integer> {
}
