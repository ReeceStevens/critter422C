 /* EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * 16340
 * Ajay Rastogi
 * asr2368
 * 16345
 * Slip days used: 0
 * Fall 2015
 */
package project5;

public class InvalidCritterException extends Exception {
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
