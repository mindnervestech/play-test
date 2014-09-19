package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
@Entity
public class SubReport extends Model{
	
	
	@Id
	public Long id;
	
	@ManyToOne
	public User users;
	
	public String question;
	
	public String answer;
	
	public Date lastUpdated  = new Date();
	
	public static Finder<Long, SubReport> find = new Finder<Long, SubReport>(Long.class, SubReport.class);

	
	public SubReport(User user, String question, String answer) {
		this.users = user;
		this.question = question;
		this.answer = answer;
		
	}
}
