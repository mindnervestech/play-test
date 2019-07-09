package controllers;


import models.User;
import play.data.DynamicForm;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

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
    	user.save();
    	
    	session().clear();
        session("email", user.email);
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
    		Admin.calculateMarks();
    		return redirect("/admin/grid");
    	}
    	
    }
    
    public static Result logout() {
    	
    	session().clear();
        response().discardCookie("email");
        
    	flash("success", "You have been logged out");
    	return redirect("/");
    }
    
  
}
