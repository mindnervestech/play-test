package controllers;

import java.util.Date;
import java.util.List;

import models.Questions;
import models.Report;
import models.SubReport;
import models.User;
import play.data.DynamicForm;
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
			return redirect("/logout");
		}
		
        session().remove("flag");
       
        String email = session().get("email");
		User user = User.findByEmail(email);
		
		String query = "select * from questions where status=1 Order by RAND() LIMIT 40";
        
        SqlQuery questionQuery = Ebean.createSqlQuery(query);
        List<SqlRow> results = questionQuery.findList();
        
        List<QuestionSetVM> questionSetVMs = Lists.newArrayList();
        int counter = 1;
        
        for(SqlRow row : results) {
        	QuestionSetVM questionSetVM = new QuestionSetVM(counter++,row.getString("id"), row.getString("question"), row.getString("option1"), 
        			row.getString("option2"), row.getString("option3"), row.getString("option4"), row.getString("question_type"),
        			String.valueOf(row.getDouble("marks")));
        	questionSetVMs.add(questionSetVM);
        }
        
        response().setHeader("Pragma", "no-cache");
        response().setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        return ok(views.html.studentPage.questionPage.render(user , questionSetVMs));
	}
	
	public static Result subQuestionIndex() {
		if(session().get("flag") == null){
			return redirect("/logout");
		}
		
        session().remove("flag");
       
        String email = session().get("email");
		User user = User.findByEmail(email);
		
		String query = "select * from subquestions where status=1 Order by RAND() LIMIT 5";
        
        SqlQuery questionQuery = Ebean.createSqlQuery(query);
        List<SqlRow> results = questionQuery.findList();
        
        List<QuestionSetVM> questionSetVMs = Lists.newArrayList();
        int counter = 1;
        
        for(SqlRow row : results) {
        	QuestionSetVM questionSetVM = new QuestionSetVM(counter++,
        			row.getString("id"), row.getString("question"),
        			String.valueOf(row.getDouble("marks")));
        	questionSetVMs.add(questionSetVM);
        }
        
        response().setHeader("Pragma", "no-cache");
        response().setHeader("Cache-Control", "no-cache,no-store,must-revalidate");
        return ok(views.html.studentPage.subjectiveQuestionPage.render(user , questionSetVMs));
	}
	
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
	
	public static Result subEvaluate() {
		
		DynamicForm form = DynamicForm.form().bindFromRequest();
		
		String email = session().get("email");
		User user = User.findByEmail(email);
		
		if(user == null) {
			return redirect("/logout");
		} 
		
		String id = form.get("id");
		String answer = form.get("answer");
		
		SubReport report = SubReport.find.where()
				.eq("users.id", user.id).
				eq("question", id).findUnique();

		if(answer == "") {
			//Report.find.ref(report.id).delete();
		} else {
			
			if(report == null) {
				SubReport reportObj = new SubReport(user, id, answer);
				reportObj.save();
			} else {
				report.answer = answer;
				report.lastUpdated = new Date();
				report.update();
			}
		}
		return ok("Done");
	}
	

}
