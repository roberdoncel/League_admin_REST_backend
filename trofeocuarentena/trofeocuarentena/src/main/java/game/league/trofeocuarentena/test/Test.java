package game.league.trofeocuarentena.test;

public class Test {
	
	private final int id;
	private final String nombre;

	public Test(int id, String nombre) {
		// TODO Auto-generated constructor stub
		this.id= id;
		this.nombre= nombre;
	}

	public int getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}
	
	
	
	//es necesario crear los getters porque si no al llamar a la url y responder
	//el servidor nos dará el error: No converter found for return value of type:
	
}
