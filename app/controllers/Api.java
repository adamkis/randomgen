package controllers;


import java.util.Map;
import java.util.Random;

import org.codehaus.jackson.node.ObjectNode;

import play.libs.Json;
import play.mvc.*;


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
	public static Result random() {
			
		int minValue = Integer.MIN_VALUE;
		int maxValue = Integer.MAX_VALUE;
		String errorMessage = "";
		
		if(! request().queryString().isEmpty() ){
		
			Map<String, String[]> urlParams = request().queryString();
			
			if( urlParams.containsKey("min") ){
				try {
					// If more parameters have the name min, we take the first, ignore the others
					// Trimming handles if the the number starts with a '+' sign -> URL decoding would
					// convert it to a space
					minValue = Integer.parseInt(urlParams.get("min")[0].trim()); 
				} catch (NumberFormatException e) {
					errorMessage += "Min value cannot be parsed. Using Integer minimum value ";
				}
			}
			
			if( urlParams.containsKey("max") ){
				try {
					// If more parameters have the name max, we take the first, ignore the others
					// Trimming handles if the the number starts with a '+' sign -> URL decoding would
					// convert it to a space
					maxValue = Integer.parseInt(urlParams.get("max")[0].trim()); // ignore all the other values
				} catch (NumberFormatException e) {
					errorMessage += "Max value cannot be parsed. Using Integer maximum value ";
				}
			}
			
			//There was no min or max param, but a useless one was given
			if( !urlParams.containsKey("min") && !urlParams.containsKey("max") )
				errorMessage += "Query parameter is useless ";
			
			// Generates the integer using min as max if the order was wrong. Adds error message though
			if ( minValue > maxValue ) {
				errorMessage += "Max value is less than the Min value. Values are swapped";
			}
		}
		
		//Generating Random number
		
		Random randomGenerator = new Random();
		
		long range = (long)maxValue - (long)minValue + 1;
		int random = (int)( range * randomGenerator.nextDouble() + minValue );
		
		// Building JSON return
		
		ObjectNode randNumJSON = Json.newObject();
		randNumJSON.put("random_integer", random);
		
		if( !errorMessage.equals("") )
			randNumJSON.put("error_message", errorMessage);
		
		// increment query count for billing
		response().setHeader("X-Mashape-Billing", "queries=1");
		
		
		return ok(randNumJSON);
	
	 
	}
	

}




