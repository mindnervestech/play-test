package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import play.db.ebean.Model;
@Entity
public class Report extends Model{
	
	
	@Id
	public Long id;
	
	@ManyToOne
	public User users;
	
	@OneToOne
	public Questions question;
	
	public String answer;
	public String remark;
	public Double marks;
	
	public static Finder<Long, Report> find = new Finder<Long, Report>(Long.class, Report.class);

	
	public Report(User user, Questions question, String answer, String remark, Double marks) {
		this.users = user;
		this.question = question;
		this.answer = answer;
		this.remark = remark;
		this.marks = marks;
	}
}
