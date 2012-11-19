package controllers;


import java.util.Random;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

public class Api extends Controller {

	/**
	 * Uses 'min' and 'max parameters' and generates an Integer between them.
	 * If no parameter is given for min/max values, or
	 * the parameter for min or max is unparseable for Integer, then:
	 * 				 Integer.MIN_VALUE / .MAX_VALUE is used.
	 * If there is no parameter for min and max values, but a useless parameter name is given,
	 * then it is ignored, and the random number is generated using Integer.MIN_VALUE and .MAX_VALUE
	 * 
	 * @return - Random Integer generated
	 */
	public static Result random(Integer min, Integer max) {
		int minValue = (min == null) ? Integer.MIN_VALUE : min;
		int maxValue = (max == null) ? Integer.MAX_VALUE : max;
		
		ObjectNode result = Json.newObject();
		
		if ((maxValue - minValue + 1) <= 0) {
			result.put("error_message", "Invalid Min and Max values");
			return badRequest(result);
		}
		
		int random = new Random().nextInt(maxValue - minValue + 1) + minValue;
		
		response().setHeader("X-Mashape-Billing", "queries=1");
		
		result.put("random_integer", random);
		return ok(result);
	}
	

}




