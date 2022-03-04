package ks4.campus_contagion.dataquery.databaseRepositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ks4.campus_contagion.dataquery.databaseEntities.User;

@Repository
public interface UsersTable extends JpaRepository<User, Integer> {

    List<User> findByUid(Integer uid);
    List<User> findByUname(String uname);

}