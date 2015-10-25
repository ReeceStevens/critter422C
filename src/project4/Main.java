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

		//TESTING ONLY
		for (int i = 0; i < 100; i += 1) {
			Critter.worldTimeStep();
		}
		for (int i = 0; i < 10; i += 1) {
			try{
			Critter.makeCritter("project4.ReeceCritter1");
			Critter.makeCritter("project4.Craig");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}


		while(true) {
			System.out.print("Critter World > ");
			//String command = input.next();	
			String command = input.nextLine();	
			String[] arguments = command.split(" ");
			command = command.toLowerCase();

			switch (arguments[0]) {
				case "quit":
					if (arguments.length > 1) {
						System.out.println("error processing: " + command);
					}
					else {
						input.close();
					}
					return;

				case "show":
					if (arguments.length > 1) {
						System.out.println("error processing: " + command);
					}
					else {
						Critter.displayWorld();
					}
					continue;

				case "step":

					if (arguments.length > 2) {
						System.out.println("error processing: " + command);
					}
					else {
						Integer steps;
						try {
							steps = Integer.parseInt(arguments[1]);
							for (int i = 0; i < steps; i += 1) {
								Critter.worldTimeStep();
							}
						}
						catch (IndexOutOfBoundsException e) {
							Critter.worldTimeStep();

						} catch (NumberFormatException e ) {
							System.out.println("error processing: " + command);
						}
					}
					continue;

				case "seed":	
					if (arguments.length > 2) {
						System.out.println("error processing: " + command);
					}
					else {
						Integer seed_number;
						try{
							seed_number = Integer.parseInt(arguments[1]);
							Critter.setSeed(seed_number);
						} catch (IndexOutOfBoundsException e) {
							System.out.println("error processing: " + command);
						} catch (NumberFormatException e) {
							System.out.println("error processing: " + command);
						}
					}
					continue;

				case "make":
					if (arguments.length != 3) {
						System.out.println("error processing: " + command);
					}
					else {
						Integer num;
						String classname = arguments[1];
						try{
							num = Integer.parseInt(arguments[2]);
						} catch (NumberFormatException e) {
							System.out.println("error processing: " + command);
							continue;
						}
						for (int i = 0; i < num; i += 1) {
							try {
								Critter.makeCritter(classname);
							} catch (InvalidCritterException e) {
								System.out.println("" + classname + " is not a valid critter class.");
								break;
							}
						}
					}
					continue;
				
				case "stats":
					if (arguments.length != 2) {
						System.out.println("error processing: " + command);
					}
					else {
						String requested_class = arguments[1];
						List<Critter> instances = null;
						try{
							instances = Critter.getInstances(requested_class);	
						} catch (InvalidCritterException e) {
							e.printStackTrace();	
						}
						// No instances of the class are alive.
						if (instances.size() <= 0) {
							System.out.println("No instances of " + requested_class + " are alive.");
							continue;
						}
						Class<?> critter_class = null;
						Class [] paramList = new Class[1];
						paramList[0] = java.util.List.class;

						try{
							critter_class = Class.forName(requested_class);
							java.lang.reflect.Method runStats = critter_class.getMethod("runStats", paramList);
							runStats.invoke(critter_class, instances);
						} catch (Exception e) {
							e.printStackTrace();	
						}
					}
					continue;
				default:
					System.out.println("Invalid command: " + command);
			}

		}
	}
}
