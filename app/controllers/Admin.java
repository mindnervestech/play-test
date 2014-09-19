package controllers;

import java.util.List;

import models.Report;
import models.SubReport;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;

public class Admin extends Controller{
	
	public static Result index() {
		
		List<User> users = User.find.where().isNotNull("name").order().desc("marks").findList();
		
		return ok(views.html.Admin.report.render(users));
	}
	
	public static Result report() {
		String id = request().body().asFormUrlEncoded().get("id")[0];
		List<Report> reports = Report.find.where().eq("users.id", User.find.byId(Long.parseLong(id)).id).findList();
		return ok(views.html.Admin.completeReport.render(reports));
	}

	public static Result logout() {
		flash("success", "You have been logged out");
    	return redirect("/admin/login");
	}
	
	public static void calculateMarks() {
		
		List<User> users = User.find.where().isNotNull("code").findList();
		
		for(User user : users) {
    	List<Report> reports = Report.find.where().eq("users.id", user.id).findList();
    	Double totalMarks = 0.0;
    	for(Report report : reports) {
    		totalMarks = totalMarks + report.marks; 
    	}
    	
    	user.marks = totalMarks;
    	user.update();
		}
		
	}
	
	public static Result viewsubjective(String email) {
    	
	    User u = User.find.where().ieq("email", email).findUnique();
	    List<SubReport> reports = SubReport.find.where().eq("users", u).findList();
		
		return ok(views.html.studentPage.viewsubjective.render(u , reports));
	}
	
	public static Result viewsubjectivebyCode(String code) {
    	
	    User u = User.find.where().ieq("code", code).findUnique();
	    List<SubReport> reports = SubReport.find.where().eq("users", u).findList();
		
		return ok(views.html.studentPage.viewsubjective.render(u , reports));
	}
	
	public static Result viewsubjectivebyid(Long id) {
    	
	    User u = User.find.where().eq("id", id).findUnique();
	    List<SubReport> reports = SubReport.find.where().eq("users", u).findList();
		
		return ok(views.html.studentPage.viewsubjective.render(u , reports));
	}
}
