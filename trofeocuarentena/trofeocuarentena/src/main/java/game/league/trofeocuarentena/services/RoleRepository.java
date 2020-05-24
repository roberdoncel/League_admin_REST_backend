package game.league.trofeocuarentena.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game.league.trofeocuarentena.entities.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
	
	@Query(value = "SELECT * FROM roles AS r WHERE rol= :name", nativeQuery = true)
	Roles findRoleByName(@Param("name") String Name);
}
