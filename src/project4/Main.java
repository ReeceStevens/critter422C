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
					input.close();
					return;

				case "show":
					Critter.displayWorld();
					continue;

				case "step":
						String num_times = input.nextLine();
						Integer steps;
						try {
							steps = Integer.parseInt(num_times.substring(1));	
							for (int i = 0; i < steps; i += 1) {
								Critter.worldTimeStep();
							}
						} catch (NumberFormatException | StringIndexOutOfBoundsException e) {
							Critter.worldTimeStep();
						}
						continue;

				case "seed":
					int seed_number = input.nextInt();
					Critter.setSeed(seed_number);
					continue;

				case "make":
					String classname = input.next();
					int num = input.nextInt();
					for (int i = 0; i < num; i += 1) {
						try {
							Critter.makeCritter(classname);
						} catch (InvalidCritterException e) {
							e.printStackTrace();
						}
					}
					continue;
				
				case "stats":
					String requested_class = input.next();
					List<Critter> instances = null;
					try{
						instances = Critter.getInstances(requested_class);	
					} catch (InvalidCritterException e) {
						e.printStackTrace();	
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
					continue;

			}
		}
	}
}
