package ks4.campus_contagion.dataquery.databaseRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ks4.campus_contagion.dataquery.databaseEntities.UserStats;

/**
 * This is the UserStats entity set. 
 * @author Joseph Kueny
 */
@Repository
public interface UserStatsTable extends JpaRepository<UserStats, Integer> {

    /**
     * 
     * @param uid : The UID to find a player's stats.
     * @return UserStats : The user's stats requested via SQL query.
     */
    UserStats findByUid(Integer uid);

}