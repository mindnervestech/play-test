package controllers;

import java.util.Date;
import java.util.List;

import models.Questions;
import models.Report;
import models.User;
import play.data.DynamicForm;
import play.mvc.Controller;
import play.mvc.Result;
import viewModel.QuestionSetVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

public class SubQuestion extends Controller{
	
	
	
	public static Result evaluate() {
		
		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		String email = session().get("email");
		User user = User.findByEmail(email);
		
		if(user == null) {
			return redirect("/logout");
		} 
		
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
					marks = marks + question.marks;
					remark = "Correct";
				} else {
					marks = marks - 0.0;
				}
			} 
			
			if(report == null) {
				Report reportObj = new Report(user, question, answer, remark, marks);
				reportObj.save();
			} else {
				report.marks = marks;
				report.answer = answer;
				report.remark = remark;
				report.lastUpdated = new Date();
				report.update();
			}
		}
		return ok("Done");
	}
	

}
