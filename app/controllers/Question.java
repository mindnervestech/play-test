package controllers;

import java.util.List;

import models.Questions;
import models.Report;
import models.User;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import viewModel.QuestionSetVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

public class Question extends Controller{
	
	public static Result index() {
		if(session().get("flag") == null){
			return ok("Your are Logged out !!!!!!");
		}
		
        session().remove("flag");
       
        String email = session().get("email");
		User user = User.findByEmail(email);
		
		String query = "select * from questions Order by RAND() LIMIT 40";
        
        SqlQuery questionQuery = Ebean.createSqlQuery(query);
        List<SqlRow> results = questionQuery.findList();
        
        List<QuestionSetVM> questionSetVMs = Lists.newArrayList();
        int counter = 1;
        
        for(SqlRow row : results) {
        	QuestionSetVM questionSetVM = new QuestionSetVM(counter++,row.getString("id"), row.getString("question"), row.getString("option1"), 
        			row.getString("option2"), row.getString("option3"), row.getString("option4"), row.getString("question_type"));
        	questionSetVMs.add(questionSetVM);
        }
        
        return ok(views.html.studentPage.questionPage.render(user , questionSetVMs));
	}
	
	public static Result evaluate() {
		
		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		String email = session().get("email");
		User user = User.findByEmail(email);
		
		Double marks = 0.0;
		
		String remark = "Wrong"; 
		String id = form.get("id");
		String answer = form.get("answer");
		
		Questions question = Questions.find.byId(Long.parseLong(id));
		
		Report report = Report.find.where().eq("users.id", user.id).eq("question.id", question.id).findUnique();

		if(answer == "") {
			Report.find.ref(report.id).delete();
		} else {
			if(question.questionType.equals("obj")) {
				if(question.correctAnswer.equals(answer)) {
					marks = marks + 1.0;
					remark = "Correct";
				} else if(question.correctAnswer.equals(answer)) {
					marks = marks + 1.0;
					remark = "Correct";
				} else if(question.correctAnswer.equals(answer)) {
					marks = marks + 1.0;
					remark = "Correct";
				} else if(question.correctAnswer.equals(answer)) {
					marks = marks + 1.0;
					remark = "Correct";
				} else {
					marks = marks - 0.25;
				}
			} 
			
			if(report == null) {
				Report reportObj = new Report(user, question, answer, remark, marks);
				reportObj.save();
			} else {
				report.marks = marks;
				report.answer = answer;
				report.remark = remark;
				report.update();
			}
		}
		return ok("Done");
	}

}
