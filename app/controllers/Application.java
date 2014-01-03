package controllers;


import java.util.List;

import models.Questions;
import models.Report;
import models.User;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import viewModel.QuestionSetVM;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.SqlQuery;
import com.avaje.ebean.SqlRow;
import com.google.common.collect.Lists;

public class Application extends Controller {
  
    public static Result index() {
        return ok(views.html.login.createAccount.render());
    }
    
    public static Result createAccount(){
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	
    	User user = new User(form.get("email"),form.get("name"),form.get("code"));
    	user.save();
    	
    	session().clear();
        response().setCookie("email", user.email);
        session("email", user.email);
        session("flag", "true");
        return redirect("/apti/questionPage");
    }
    
    public static Result checkUserEmailAvailability() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
		String email = form.get("q");
		User user = User.find.where().eq("email", email).findUnique();
		
		if(user == null){
			return ok(Json.toJson(true));
		}
		else
			return ok(Json.toJson("Email ID is in use"));
    }
    
    public static Result checkUserCode() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
		String code = form.get("q");
		User user = User.find.where().eq("code", code).findUnique();
		
		if(user == null){
			return ok(Json.toJson(true));
		}
		else
			return ok(Json.toJson("Code is in use"));
    }
    
    public static Result adminLogin() {
    	return ok(views.html.login.login.render());
    }
    
    public static Result login() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	String username = form.get("username");
    	
    	User user = User.find.where().eq("name", username).findUnique();
    	
    	if(user == null ) {
    		flash("error", "Please enter Valid Details");
    		return redirect("/admin/login");
    	} else {
    		return ok("logged in report grid");
    	}
    	
    }
    
    public static Result logout() {
    	String email = session().get("email");
		User user = User.findByEmail(email);
		
    	List<Report> reports = Report.find.where().eq("users.id", user.id).findList();
    	Double totalMarks = 0.0;
    	for(Report report : reports) {
    		totalMarks = totalMarks + report.marks; 
    	}
    	
    	user.marks = totalMarks;
    	user.update();
    	
    	System.out.println("abcd");
    	
    	return ok();
    }
    
  
}