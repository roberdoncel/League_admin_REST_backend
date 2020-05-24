package game.league.trofeocuarentena.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import game.league.trofeocuarentena.entities.Results;

@Repository
public interface ResultsRepository extends JpaRepository<Results, Long> {
	
}
