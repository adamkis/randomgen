package controllers;

import play.*;
import play.mvc.*;

import views.html.*;

public class Api extends Controller {

	public static Result random() {
		 
		return ok(index.render("Random number will be generated here"));
	 
	}

}




