package game.league.trofeocuarentena.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game.league.trofeocuarentena.entities.LeagueStatus;

@Repository
public interface LeagueStatusRepository extends JpaRepository<LeagueStatus, Long> {
	
	@Query(value = "SELECT * FROM league_status WHERE status= :name", nativeQuery = true)
	LeagueStatus findStatusByName(@Param("name") String Name);
}
