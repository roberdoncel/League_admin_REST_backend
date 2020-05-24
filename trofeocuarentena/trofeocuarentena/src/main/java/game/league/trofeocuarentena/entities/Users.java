package game.league.trofeocuarentena.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import lombok.Data;

@Data
@Entity
@Table(name="users")
public class Users implements Serializable{
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id;
	
	@Column(name="USERNAME", nullable=false, unique = true, length=30)
	private String username;
	
	@Column(name="PASSWORD", nullable=false)
//	@ColumnTransformer( read="AES_DECRYPT(password, 'yudjfosk')", 
	//		  			write="AES_ENCRYPT(?, 'yudjfosk')")
	private String password;
	
	public Users() {
		// TODO Auto-generated constructor stub
	}

	public Users(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Users [id=" + id + ", username=" + username + ", password=" + password + "]";
	}
	
	
	

}
