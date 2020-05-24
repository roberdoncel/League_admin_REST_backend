package game.league.trofeocuarentena.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import game.league.trofeocuarentena.entities.LeagueStatus;
import game.league.trofeocuarentena.entities.Leagues;
import game.league.trofeocuarentena.entities.PartOfSeason;
import game.league.trofeocuarentena.entities.Results;
import game.league.trofeocuarentena.entities.Roles;
import game.league.trofeocuarentena.entities.TeamUserLeague;
import game.league.trofeocuarentena.entities.Users;


@Service
public class LeagueService {
	
	@Autowired //necesario para inyectar la dependencia. (La interfaz repository)
	private final LeagueRepository leagueRepository;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final RoleRepository roleRepository;
	
	@Autowired
	private final TeamUserLeagueRepository TULRepository;
	
	@Autowired
	private final LeagueStatusRepository LSRepository;
	
	@Autowired
	private final PartOfSeasonRepository posRepository;
	
	@Autowired
	private final ResultsRepository resultsRepository;
	
	//podemos evitar generar el constructor gracias a @RequiredArgsConstructor. Pertenece a Lombok
	public LeagueService(LeagueRepository leagueRepository, 
						 UserRepository userRepository,
						 RoleRepository roleRepository,
						 TeamUserLeagueRepository tulRepository,
						 LeagueStatusRepository lsRepository,
						 PartOfSeasonRepository posRepository,
						 ResultsRepository resultsRepository) {

		this.leagueRepository = leagueRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.TULRepository = tulRepository;
		this.LSRepository = lsRepository;
		this.posRepository = posRepository;
		this.resultsRepository = resultsRepository;
	}
	
	//generamos los métodos de gestión del repositorio si no es alguno de los autogenerados o si queremos personalizarlo
	
	public boolean doLogin(String username, String password) {
		Users user = userRepository.findUser(username, password);
		
		if(user != null) {
			return true;
		}else {
			return false;
		}
	}
	
	//Leagues
	public Leagues saveLeague(Leagues league) {
		return leagueRepository.save(league);
	}
	
	public List<Leagues>findAll(){
		/*List<Leagues> lista = leagueRepository.findAll();
		for (Leagues league : lista) {
			System.out.print(league);
		}*/
		return leagueRepository.findAll();
	}
	
	public Leagues findLeagueByIdAndName(long Id, String Name) {
		return leagueRepository.findLeagueByIdAndName(Id, Name);
	}
	
	//Users
	public Users findUserByName(String username) {
		Users user = userRepository.findUserByName(username);	
		return user;
	}
	
	public Users saveUser(Users user) {
		return userRepository.save(user);
	}
	
	//Roles
	public List<Roles>findAllRoles(){
		return roleRepository.findAll();
	}
	
	public Roles findRoleByName(String name) {
		return roleRepository.findRoleByName(name);
	}
	
	public Roles saveRole(Roles role){
		return roleRepository.save(role);
	}
	
	//Leagues Status 
	public List<LeagueStatus>findAllLeagueStatus(){
		return LSRepository.findAll();
	}
	
	public LeagueStatus findLeagueStatusByName(String Name) {
		return LSRepository.findStatusByName(Name);
	}
	
	public LeagueStatus saveLeagueStatus(LeagueStatus ls) {
		return LSRepository.save(ls);
	}
	
	//TeamUserLeague
	public List<TeamUserLeague>getLeagueRegisteredTeams(long idLeague, String nameLeague){
		return TULRepository.findRegisteredTeamsByIdLeague(idLeague, nameLeague);
	}
	
	public TeamUserLeague saveTeamUserLeague(TeamUserLeague TUL) {
		return TULRepository.save(TUL);
	}
	
	public TeamUserLeague findTeamUserLeagueByTeamNameAndLeague(long idLeague, String nameLeague, String teamName) {
		return TULRepository.findTeamUserLeagueByTeamNameAndLeague(idLeague, nameLeague, teamName);
	}
	
	//PartOfSeason
	public List<PartOfSeason> findAllPartOfSeason(){
		return posRepository.findAll();
	}
	
	public PartOfSeason createPartOfSeason(PartOfSeason pos) {
		return posRepository.save(pos);
	}
	
	public PartOfSeason findPartOfSeasonByName(String name) {
		return posRepository.findPartOfSeasonByName(name);
	}
	
	//Results
	public Results saveResult(Results result) {
		return resultsRepository.save(result);
	}

}
