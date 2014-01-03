package models;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Questions extends Model{

	@Id
	public Long id;
	
	public String question;
	
	public String option1;
	public String option2;
	public String option3;
	public String option4;
	
	public String correctAnswer;
	
	public String questionType;
	
	public static Finder<Long, Questions> find = new Finder<Long, Questions>(Long.class, Questions.class);
}
