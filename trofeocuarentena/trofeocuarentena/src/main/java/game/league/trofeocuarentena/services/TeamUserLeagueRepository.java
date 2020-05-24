package game.league.trofeocuarentena.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import game.league.trofeocuarentena.entities.TeamUserLeague;

@Repository
public interface TeamUserLeagueRepository extends JpaRepository<TeamUserLeague, Long> {
	
	@Query(value = "SELECT * FROM team_user_league AS tul"
			+ " INNER JOIN leagues AS l ON 	l.id = tul.league_id"
			+ " WHERE tul.league_id= :idLeague AND l.name=:nameLeague", nativeQuery = true)
	List<TeamUserLeague>findRegisteredTeamsByIdLeague(@Param("idLeague") long idLeague, @Param("nameLeague") String nameLeague);
	
	@Query(value="SELECT * FROM team_user_league AS tul"
			+ " INNER JOIN leagues AS l ON 	l.id = tul.league_id"
			+ " WHERE tul.league_id= :idLeague AND l.name= :nameLeague"
			+ " AND tul.team= :teamName", nativeQuery = true)
	TeamUserLeague findTeamUserLeagueByTeamNameAndLeague(@Param("idLeague") long idLeague, @Param("nameLeague") String nameLeague, 
														 @Param("teamName") String teamName);
}
