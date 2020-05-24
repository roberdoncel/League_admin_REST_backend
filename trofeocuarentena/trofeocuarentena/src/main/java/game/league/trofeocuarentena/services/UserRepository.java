package game.league.trofeocuarentena.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game.league.trofeocuarentena.entities.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
	
	@Query(value="SELECT * FROM users WHERE username= :username", nativeQuery=true)
	Users findUserByName(@Param("username") String username);
	
	@Query(value="SELECT * FROM users WHERE username= :username AND password= :password", nativeQuery = true)
	Users findUser(@Param("username") String username, @Param("password") String password);
}
