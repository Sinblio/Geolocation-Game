package ks4.campus_contagion.dataquery.databaseRepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ks4.campus_contagion.dataquery.databaseEntities.User;

/**
 * This is the Users entity set. 
 * @author Joseph Kueny
 */
@Repository
public interface UsersTable extends JpaRepository<User, Integer> {

    /**
     * 
     * @param uid : The UID to find a player by.
     * @return User : The user requested via SQL query.
     */
    User findByUid(Integer uid);

    /**
     * 
     * @param uname : The UNAME to find a player by.
     * @return User : The user requested via SQL query.
     */
    User findByUname(String uname);

}