package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import play.db.ebean.Model;

@Entity
public class User extends Model{
	
	@Id
	public Long id;

	@Column(unique = true)
	public String email;
	
	public String name;
	public Double marks;
	
	@Column(unique = true)
	public String code;
	
	@OneToMany
	public List<Report> reports ;
	
	public Date loginTime = new Date();
	
	public int passed = 0;
	public String phone ;
	
	public String gender;
	public String degree;
	public String passout;
	public String college;
	public String cgpa;
	public String hscboard;
	public String sscboard;
	public String hscpercent;
	public String sscpercent;
	public String nativetown;
	

	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);

	public User(String email, String name,String phone, String code) {
		this.email = email;
		this.name = name;
		this.code = code;
		this.phone = phone;
		this.marks = 0.0;
	}
	
	public static User findByEmail(String email) {
        return find.where().eq("email", email).findUnique();
    }
	
}
