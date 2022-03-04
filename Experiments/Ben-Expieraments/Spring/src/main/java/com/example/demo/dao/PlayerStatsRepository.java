package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.model.PlayerStats;

public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long>{

}
