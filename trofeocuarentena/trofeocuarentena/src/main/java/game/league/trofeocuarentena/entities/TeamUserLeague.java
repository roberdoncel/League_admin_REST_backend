package game.league.trofeocuarentena.entities;



import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;

@Data
@Entity
@Table(name="team_user_league",
	   uniqueConstraints = { @UniqueConstraint( columnNames = { "TEAM", "USER_ID", "LEAGUE_ID" }, name = "ForeignKey_tul" ) })
public class TeamUserLeague implements Serializable {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	
	@Column(name="TEAM", nullable= false, length=60)
	private String team;
	
	
	@ManyToOne(optional= false, fetch = FetchType.EAGER) 
	@JoinColumn(name = "USER_ID", referencedColumnName = "ID")
	private Users user;
	

	@ManyToOne(optional= false, fetch = FetchType.EAGER)
	@JoinColumn(name = "LEAGUE_ID", referencedColumnName = "ID")
	private Leagues league;
	
	@ManyToOne(optional = false, fetch=FetchType.EAGER)
	@JoinColumn(name="ROLE_ID")
	private Roles role;

	public TeamUserLeague() {
	}

	public TeamUserLeague(String team, Users user, Leagues league, Roles role) {
		this.team = team;
		this.user = user;
		this.league = league;
		this.role = role;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public Leagues getLeague() {
		return league;
	}

	public void setLeague(Leagues league) {
		this.league = league;
	}

	public Roles getRole() {
		return role;
	}

	public void setRole(Roles role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "TeamUserLeague [team=" + team + ", user=" + user + ", league=" + league + ", role=" + role + "]";
	}
			
}

