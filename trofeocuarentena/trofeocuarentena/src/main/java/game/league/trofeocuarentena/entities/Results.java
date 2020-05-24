package game.league.trofeocuarentena.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;


@Entity
@Table(name="results",
	   uniqueConstraints = { @UniqueConstraint( columnNames = { "TEAM1_ID", "TEAM2_ID", "LEAGUE_ID", "PART_OF_SEASON_ID" }, 
	   name = "FK_Results" ) })
public class Results implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="TEAM1_ID", referencedColumnName = "ID")	
	private TeamUserLeague tul1;
	

	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="TEAM2_ID", referencedColumnName = "ID")	
	private TeamUserLeague tul2;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="LEAGUE_ID", referencedColumnName = "ID")
	private Leagues league;
	
	@Column(name="GOALS_TEAM1", nullable = false)
	private int goalsTeam1;
	
	@Column(name = "GOALS_TEAM2", nullable = false)
	private int goalsTeam2;
		
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="PART_OF_SEASON_ID", referencedColumnName = "ID")
	private PartOfSeason partOfSeason;
	
	
	public Results() {
		// TODO Auto-generated constructor stub
	}


	public Results(TeamUserLeague tul1, TeamUserLeague tul2, int goalsTeam1, int goalsTeam2, Leagues league, PartOfSeason partOfSeason) {
		this.tul1 = tul1;
		this.tul2 = tul2;
		this.goalsTeam1 = goalsTeam1;
		this.goalsTeam2 = goalsTeam2;
		this.league = league;
		this.partOfSeason = partOfSeason;
	}


	public long getId() {
		return id;
	}


	public void setId(long id) {
		this.id = id;
	}


	public TeamUserLeague getTul1() {
		return tul1;
	}


	public void setTul1(TeamUserLeague tul1) {
		this.tul1 = tul1;
	}


	public TeamUserLeague getTul2() {
		return tul2;
	}


	public void setTul2(TeamUserLeague tul2) {
		this.tul2 = tul2;
	}


	public Leagues getLeague() {
		return league;
	}


	public void setLeague(Leagues league) {
		this.league = league;
	}


	public int getGoalsTeam1() {
		return goalsTeam1;
	}


	public void setGoalsTeam1(int goalsTeam1) {
		this.goalsTeam1 = goalsTeam1;
	}


	public int getGoalsTeam2() {
		return goalsTeam2;
	}


	public void setGoalsTeam2(int goalsTeam2) {
		this.goalsTeam2 = goalsTeam2;
	}


	public PartOfSeason getPartOfSeason() {
		return partOfSeason;
	}


	public void setPartOfSeason(PartOfSeason partOfSeason) {
		this.partOfSeason = partOfSeason;
	}


	@Override
	public String toString() {
		return "Results [id=" + id + ", tul1=" + tul1 + ", tul2=" + tul2  + ", goalsTeam1="
				+ goalsTeam1 + ", goalsTeam2=" + goalsTeam2 + ", partOfSeason=" + partOfSeason + "]";
	}

	
	
	

}
