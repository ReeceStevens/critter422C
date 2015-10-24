/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * <Student1 Name>
 * <Student1 EID>
 * <Student1 5-digit Unique No.>
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Fall 2015
 */
package project4;
import java.util.*;

public class Main {

	public static void main(String[] args) {
		System.out.println("Welcome to Critter Park. Beware the Critters.");
		Scanner input = new Scanner(System.in);
		while(true) {
			System.out.print("Critter World > ");
			String command = input.next();	
			command = command.toLowerCase();

			switch (command) {
				case "quit":
					return;
				case "show":
					Critter.displayWorld();
					continue;
				// TODO: accept a number parameter to step
				case "step":
					Critter.worldTimeStep();
					continue;
				case "seed":
					int seed_number = input.nextInt();
					Critter.setSeed(seed_number);
					continue;
				// TODO: implement make
				// 	Syntax: make <classname> <number>
				//case "make":
				
				case "stats":
					String requested_class = input.next();
					List<Critter> instances = null;
					try{
						instances = Critter.getInstances(requested_class);	
					} catch (InvalidCritterException e) {
						e.printStackTrace();	
					}
					Class<?> critter_class = null;
					// TODO: does this method reflection even work???
					try{
						critter_class = Class.forName(requested_class);
						java.lang.reflect.Method runStats = critter_class.getMethod("runStats", instances.getClass());
					} catch (Exception e) {
						e.printStackTrace();	
					}
					continue;

			}
		}
	}

}
