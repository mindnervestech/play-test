package controllers;


import models.User;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import models.Report;
import java.util.List;

public class Application extends Controller {
  
    public static Result index() {
    	
        return ok(views.html.login.createAccount.render());
    }
    
    public static Result createAccount(){
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	
    	try {
	    	User u = User.find.where().eq("email", form.get("email")).findUnique();
	    	if(u != null && u.passed == 1) {
	    		session().clear();
	            session("email", u.email);
	            session("flag", "true");
	    		return redirect(routes.Question.subQuestionIndex()); //TODO
	    	}
    	} catch (Exception e) {
    		return index();
    	}
    	
    	User user = new User(form.get("email"),
    						form.get("name"),
    						form.get("phone"),
    						form.get("code"));
    	user.gender = form.get("gender");
    	user.degree = form.get("degree");
	user.passout = form.get("passout");
	user.college = form.get("college");
	user.cgpa = form.get("cgpa");
	user.hscboard = form.get("hscboard");
	user.sscboard = form.get("sscboard");
	user.hscpercent = form.get("hscpercent");
	user.sscpercent = form.get("sscpercent");
	user.nativetown = form.get("nativetown");
    	user.level = form.get("level") == null || "".equals(form.get("level"))? "fresher" : form.get("level");
    	user.save();
    	
    	session().clear();
        session("email", user.email);
        session("level", user.email);
        session("flag", "true");
        return redirect(routes.Question.index());
    }
    
    public static Result checkUserEmailAvailability() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
		String email = form.get("q");
		User user = User.find.where().eq("email", email).findUnique();
		
		if(user == null){
			return ok(Json.toJson(true));
		}
		else if (user.passed == 1){
			return ok(Json.toJson(true));
		} else {
			return ok(Json.toJson("Email ID is in use"));
		}
    }
    
    public static Result checkUserCode() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
		String code = form.get("q");
		User user = User.find.where().eq("code", code).findUnique();
		
		if(user == null){
			return ok(Json.toJson(true));
		}
		else if (user.passed == 1) {
			return ok(Json.toJson(true));
		} else {
			return ok(Json.toJson("Code is in use"));
		}
    }
    
    public static Result adminLogin() {
    	return ok(views.html.login.login.render());
    }
    
    public static Result login() {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	String email = form.get("email");
    	String code = "mntAdmin"; 
    	User user = User.find.where().eq("email", email).eq("code", code).findUnique();
    	
    	if(user == null ) {
    		flash("error", "Please enter Valid Details");
    		return redirect("/admin/login");
    	} else {
    		List<Report> reports = Report.find.where().eq("users.id", form.get("userId")).findList();
    		
    		return ok(views.html.Admin.report.render(reports));
    	}
    	
    }
    
    public static Result seeReport(Long id) {
    	DynamicForm form = DynamicForm.form().bindFromRequest();
    	List<Report> reports = Report.find.where().eq("users.id", id).findList();
    	return ok(views.html.Admin.report.render(reports));
    	
    }
    
    public static Result logout() {
    	
    	session().clear();
        response().discardCookie("email");
        
    	flash("success", "You have been logged out");
    	return redirect("/");
    }
    
  
}
