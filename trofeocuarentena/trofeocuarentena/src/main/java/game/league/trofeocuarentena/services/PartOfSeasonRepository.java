package game.league.trofeocuarentena.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import game.league.trofeocuarentena.entities.PartOfSeason;

public interface PartOfSeasonRepository extends JpaRepository<PartOfSeason, Long> {
	
	@Query(value = "SELECT * FROM part_of_season WHERE name= :name", nativeQuery = true)
	PartOfSeason findPartOfSeasonByName(@Param("name") String name);
}
