package game.league.trofeocuarentena.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="roles")
public class Roles implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "ROL", nullable = false, unique = true, length = 20, updatable = true)
	private String rol;

	public Roles() {
		// TODO Auto-generated constructor stub
	}

	public Roles(String rol) {
		this.rol = rol;
	}
	
	/**De acuerdo a la documentación @Data genera automáticamente los setters y getters, pero utilizando eclipse esto no funciona.
	 * Al devolver los registros de la tabla, devuelve una lista vacía, pero con el número de elementos necesarios.
	 * Así que me toca generar los getters y setters.
	 */

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		return "Roles [id=" + id + ", rol=" + rol + "]";
	}
	
	

}
