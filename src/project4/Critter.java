/* CRITTERS Critter.java
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

import java.lang.reflect.Constructor;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {

	private static String [] validCritters = { "project4.Algae", "project4.Craig", "project4.ReeceCritter1", "project4.ReeceCritter2", "project4.AjayCritter1", "project4.AjayCritter2" };

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private boolean alive;
	public boolean isAlive() {
		return alive;
	}
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	
	//These two functions exist to implement the 2-D torus projection property of the world
	private final int wrapX(int steps) {
		if ((x_coord + steps) < 0){
			return (Params.world_width - steps);
		} 
		else if ((x_coord + steps) > (Params.world_width - 1)){
			return (steps - 1);
		}
		else {return x_coord += steps;}
	}

	private final int wrapY(int steps) {
		if ((y_coord + steps) < 0){
			return (Params.world_height - steps);
		} 
		else if ((y_coord + steps) > (Params.world_height - 1)){
			return (steps - 1);
		}
		else {return y_coord += steps;}
	}

	// Implemented per critter	
	protected final void walk(int direction) {
		switch (direction) {
			case 0: //move east one unit
				x_coord = wrapX(1);
			case 1: //move northeast one unit
				x_coord = wrapX(1);
				y_coord = wrapY(-1);
			case 2: //move north one unit
				y_coord = wrapY(-1);
			case 3: //move northwest one unit
				x_coord = wrapX(-1);
				y_coord = wrapY(-1);
			case 4: //move west one unit
				x_coord = wrapX(-1);
			case 5: //move southwest one unit
				x_coord = wrapX(-1);
				y_coord = wrapY(1);
			case 6: //move south one unit
				y_coord = wrapY(1);
			case 7: //southeast one unit
				x_coord = wrapX(1);
				y_coord = wrapY(1);	
		}
		energy -= Params.walk_energy_cost;
	}

	// Implemented per critter	
	protected final void run(int direction) {	
		switch (direction) {
			case 0: //move east two units
				x_coord = wrapX(2);
			case 1: //move northeast two units
				x_coord = wrapX(2);
				y_coord = wrapY(-2);
			case 2: //move north two units
				y_coord = wrapY(-2);
			case 3: //move northwest two units
				x_coord = wrapX(-2);
				y_coord = wrapY(-2);
			case 4: //move west two units
				x_coord = wrapX(-2);
			case 5: //move southwest two units
				x_coord = wrapX(-2);
				y_coord = wrapY(2);
			case 6: //move south two units
				y_coord = wrapY(2);
			case 7: //southeast two units
				x_coord = wrapX(2);
				y_coord = wrapY(2);	
		}
		energy -= Params.run_energy_cost;
	}

	// Implemented per critter	
	protected final void reproduce(Critter offspring, int direction) {
	}

	// Implemented per critter	
	public abstract void doTimeStep();

	// Implemented per critter	
	public abstract boolean fight(String oponent);
	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> newCritterClass = null; 

		// Check to make sure the critter class is valid
		boolean exists = false;
		for (String name : validCritters) {
			if (name.equals(critter_class_name)) { exists = true; }
		}
		if (!exists) { throw new InvalidCritterException(critter_class_name); }

		// If critter is valid, instantiate it and put it in the population
		// TODO: initialize the critter's position, etc.
		try {
			newCritterClass = Class.forName(critter_class_name);	
			Constructor<?> constructor = null;
			constructor = newCritterClass.getConstructor();	
			Object newCritter = null;
			newCritter = constructor.newInstance();
			// Add critter to bulk population
			population.add((Critter) newCritter);
		} catch (Exception e) {
			// Lots of different exceptions can be thrown here. Just catch all of them.
			e.printStackTrace();
		}
	}

	// TODO: FINISH THIS METHOD. Return the list of all instances of the given class.	
	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();
		return result;
	}
	
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();		
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

		
	public static void worldTimeStep() {
		// 1. Remove dead critters
		for (Critter a : population) {
			if (!a.isAlive()) {
				population.remove(a);
			}	
		}
		// 2. call doTimeStep() for every critter
		for (Critter a: population) {
			a.doTimeStep();
		}	
		// 3. Orchestrate all fights
		
		// 4. Deduct rest energy costs
		for (Critter a: population) {
			a.energy = a.energy - Params.rest_energy_cost;
		}	
		// 5. Generate new algae
		for (int i  = 0; i < Params.refresh_algae_count; i += 1) {
			try {
				Critter.makeCritter("project4.Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}	
		// 6. Add new babies into critter collection
		
	}
	
	public static void displayWorld() {
		String[][] output = new String[Params.world_height+2][Params.world_width+2]; 
		// Draw Grid
		for (int i = 0; i < Params.world_height+2; i +=1 ) {
			for (int j = 0; j < Params.world_width+2; j +=1 ) {
				if (i == 0 || i == Params.world_height+1) {
					if (j == 0 || j == Params.world_width+1) {
						output[i][j] = "+";
						continue;
					}
					else {
						output[i][j] = "-";
					}
				}
				else if (j == 0 || j == Params.world_width+1) {
					output[i][j] = "|";
				}
				else {
					output[i][j] = " ";
				}
			}
		}
		// Draw Critters
		for (Critter a : population) {
			System.out.println("Algae at " + a.x_coord + ", " + a.y_coord + "!");
			output[a.y_coord + 1][a.x_coord + 1] = a.toString();			
		}
		for (int i = 0; i < Params.world_height+2; i += 1) {
			for (int j = 0; j < Params.world_width+2; j += 1) {
				System.out.print(output[i][j]);
			}
			System.out.print("\n");
		}
	}
}
