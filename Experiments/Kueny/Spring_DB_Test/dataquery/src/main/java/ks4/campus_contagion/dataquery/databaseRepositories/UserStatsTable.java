package ks4.campus_contagion.dataquery.databaseRepositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ks4.campus_contagion.dataquery.databaseEntities.UserStats;


@Repository
public interface UserStatsTable extends JpaRepository<UserStats, Integer> {

    List<UserStats> findByUid(Integer uid);

}