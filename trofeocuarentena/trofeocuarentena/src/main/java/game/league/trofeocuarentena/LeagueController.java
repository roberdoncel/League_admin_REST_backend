package game.league.trofeocuarentena;

import java.util.List;

import javax.validation.Valid;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import game.league.trofeocuarentena.entities.LeagueStatus;
import game.league.trofeocuarentena.entities.Leagues;
import game.league.trofeocuarentena.entities.PartOfSeason;
import game.league.trofeocuarentena.entities.Results;
import game.league.trofeocuarentena.entities.Roles;
import game.league.trofeocuarentena.entities.TeamUserLeague;
import game.league.trofeocuarentena.entities.Users;
import game.league.trofeocuarentena.services.LeagueRepository;
import game.league.trofeocuarentena.services.LeagueService;
import game.league.trofeocuarentena.test.Test;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LeagueController {
	
	@Autowired
	private final LeagueService leagueService;
	
	public LeagueController(LeagueService leagueService) {
		this.leagueService = leagueService;
	}
	
	/** TERMINAR MÉTODO LOGIN PROXIMAMENTE */
	@PostMapping("/login")
	public ResponseEntity login(@Valid @RequestBody String str) throws JsonMappingException, JsonProcessingException {
		
		System.out.println(str);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.readTree(str);
		
		JsonNode username = obj.get("username");
		JsonNode password = obj.get("password");
		
		String username_txt = username.asText();
		String password_txt = password.asText();
		
		try {
			leagueService.doLogin(username_txt, password_txt);
			return new ResponseEntity("Login efectuado", HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
		
	@GetMapping("/get_leagues")
	public ResponseEntity<List<Leagues>> get_teams() {
		try {			
			return new ResponseEntity<>(leagueService.findAll(), HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	
	@GetMapping("/get_roles")
	public ResponseEntity<List<Roles>> getRoles(){
		try {
			return new ResponseEntity<>(leagueService.findAllRoles(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	@GetMapping("/create_parts_of_season")
	public ResponseEntity<List<PartOfSeason>> createPartsOfSeason(){
		PartOfSeason posIda = new PartOfSeason("IDA");
		PartOfSeason posVuelta = new PartOfSeason("VUELTA");
		//we can create other such as playoff(final, semifinal...), friendly...
		
		try {
			leagueService.createPartOfSeason(posIda);
			leagueService.createPartOfSeason(posVuelta);
			return new ResponseEntity(leagueService.findAllPartOfSeason(), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/get_parts_of_season")
	public ResponseEntity <List<PartOfSeason>> getPartsOfSeason(){
		try {
			return new ResponseEntity(leagueService.findAllPartOfSeason(), HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/create_roles")
	public ResponseEntity<List<Roles>> createRoles(){
		Roles roleAdmin = new Roles("admin");
		Roles rolePlayer =  new Roles("player");
		
		try {
			leagueService.saveRole(roleAdmin);
			leagueService.saveRole(rolePlayer);
			return new ResponseEntity(leagueService.findAllRoles(), HttpStatus.CREATED);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@GetMapping("/create_league_status")
	public ResponseEntity<List<LeagueStatus>> createLeagueStatus(){
		LeagueStatus lSNoStarted = new LeagueStatus("NO_INICIADA");
		LeagueStatus lSStarted = new LeagueStatus("INICIADA");
		LeagueStatus lSFinished = new LeagueStatus("FINALIZADA");
		
		try {
			leagueService.saveLeagueStatus(lSNoStarted);
			leagueService.saveLeagueStatus(lSStarted);
			leagueService.saveLeagueStatus(lSFinished);
			return new ResponseEntity(leagueService.findAllLeagueStatus(), HttpStatus.CREATED);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}
	//@valid is to validate a model after binding user input to it
	
	@PostMapping("/create_league")
	public ResponseEntity createLeague(@Valid @RequestBody Leagues league) {
		
		LeagueStatus lStatus = leagueService.findLeagueStatusByName("NO_INICIADA");	
		league.setLeagueStatus(lStatus);
		
		//para que funcione, el nombre del campo del formulario debe ser igual al de la propiedad de entity
		try {
			return new ResponseEntity<>(leagueService.saveLeague(league), HttpStatus.CREATED);			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.EXPECTATION_FAILED);
		}	
	}
	
	@PostMapping("/register_team")
	public ResponseEntity registerTeam(@Valid @RequestBody String str) throws JsonMappingException, JsonProcessingException {
		System.out.println(str);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.readTree(str);
		
		JsonNode idLeague = obj.get("id");
		JsonNode nameLeague = obj.get("leaguename");
		JsonNode username = obj.get("username");
		JsonNode team = obj.get("team");
		JsonNode password = obj.get("password");
		JsonNode role = obj.get("role");
		
		try {
			
			//check if league is started. If true, league doesn't accept new teams
			Leagues league = leagueService.findLeagueByIdAndName(idLeague.asLong(), nameLeague.textValue());	
			LeagueStatus lStatus = league.getLeagueStatus();

			
			//no podemos usar lStatus.getStatus() != "NO_INICIADA". No funciona ya que compara objeto contra la cadena de texto.
			//ver mejor explicado en: https://www.arquitecturajava.com/comparando-java-vs-equals/
			if(lStatus.getStatus().equals("NO_INICIADA") == false ) {
				System.out.println("La liga está iniciada");
				return new ResponseEntity(HttpStatus.UNAUTHORIZED);
			}
			
			//TODO: Check if team already exists in league.
			
			Users user = leagueService.findUserByName(username.textValue());

			if(user == null) {
				user = new Users(username.textValue(), password.textValue()); 
				user = leagueService.saveUser(user);
			}
			
			//para que no de error de elemento duplicado debe tener su clave primaria id para que no de error 
			//de registro duplicado cuando tenemos elementos que deben ser únicos en la tabla
			Roles rol = leagueService.findRoleByName(role.textValue());

			TeamUserLeague TUL = new TeamUserLeague(team.textValue(), user, league, rol);
			return new ResponseEntity<>(leagueService.saveTeamUserLeague(TUL), HttpStatus.CREATED);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<>(e, HttpStatus.ACCEPTED);
		}
		
		
	}
	
	@GetMapping(path="/get_registered_teams/{idleague}/{nameleague}")
	public ResponseEntity getRegisteredTeams(@PathVariable int idleague, @PathVariable String nameleague) {
		try {
			return new ResponseEntity(leagueService.getLeagueRegisteredTeams(idleague, nameleague), HttpStatus.OK);
			
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(null, HttpStatus.EXPECTATION_FAILED);
		}		
	}
	
	@PostMapping("/startLeague")
	public ResponseEntity startLeague(@Valid @RequestBody Leagues league) {
		
		LeagueStatus lStatus = leagueService.findLeagueStatusByName("INICIADA");	
		league.setLeagueStatus(lStatus);
		
		try {
			return new ResponseEntity(leagueService.saveLeague(league), HttpStatus.ACCEPTED);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
	@PostMapping("/register_result")
	public ResponseEntity registerResult(@Valid @RequestBody String body) throws JsonMappingException, JsonProcessingException {
		
		System.out.println(body);
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode obj = mapper.readTree(body);
		
		JsonNode idLeague = obj.get("leagueId");
		JsonNode nameLeague = obj.get("leagueName");
		JsonNode team1 = obj.get("team1");
		JsonNode team2 = obj.get("team2");
		JsonNode team1Goals = obj.get("team1Goals");
		JsonNode team2Goals = obj.get("team2Goals");
		JsonNode partOfSeason = obj.get("partOfSeason");
		
		
		if(team1.equals(team2) == true) {
			return new ResponseEntity("El equipo local y el visitante es el mismo", HttpStatus.UNAUTHORIZED);
		}
		
		
		Leagues league = leagueService.findLeagueByIdAndName(idLeague.asLong(), nameLeague.textValue());
		LeagueStatus lStatus = league.getLeagueStatus();

		if(lStatus.getStatus().equals("NO_INICIADA") == true ) {
			System.out.println("La liga no está iniciada");
			return new ResponseEntity("La liga no está iniciada", HttpStatus.UNAUTHORIZED);
		}
		
		TeamUserLeague localTeam = leagueService.findTeamUserLeagueByTeamNameAndLeague(idLeague.asLong(), nameLeague.textValue(), team1.textValue());
		TeamUserLeague visitorTeam = leagueService.findTeamUserLeagueByTeamNameAndLeague(idLeague.asLong(), nameLeague.textValue(), team2.textValue());
		
		if(localTeam == null || visitorTeam == null) {
			return new ResponseEntity("No se han introducido dos equipos válidos", HttpStatus.UNAUTHORIZED);
		}
		
		PartOfSeason POS = leagueService.findPartOfSeasonByName(partOfSeason.textValue());
		
		Results resultMatch = new Results(localTeam, visitorTeam, team1Goals.asInt(),team2Goals.asInt(), league, POS);
		
		try {
			//database checks if result exists previously
			return new ResponseEntity(leagueService.saveResult(resultMatch), HttpStatus.CREATED);
		}catch (Exception e) {
			return new ResponseEntity(e, HttpStatus.UNAUTHORIZED);
		}		
	}
	
	@GetMapping("/get_league_results/{id}/{leagueName}")
	public ResponseEntity getLeagueResults(@PathVariable long id, @PathVariable String leagueName) {
		
		try {
			Leagues league = leagueService.findLeagueByIdAndName(id, leagueName);
			if(league == null) {
				return new ResponseEntity(null, HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity(league.getResults(), HttpStatus.OK);
		}catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity(e, HttpStatus.EXPECTATION_FAILED);
		}
	}

}

