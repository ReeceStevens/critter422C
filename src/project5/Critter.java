/* CRITTERS Critter.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * <Student1 5-digit Unique No.>
 * Ajay Rastogi
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: 0
 * Fall 2015
 */
package project5;

import java.lang.reflect.Constructor;
import java.util.List;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {

	private static String [] validCritters = { "project5.Algae", "project5.Craig", "project5.ReeceCritter1", "project5.ReeceCritter2", "project5.AjayCritter1", "project5.AjayCritter2", "project5.Craig2" };

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
	private boolean hasMoved;

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


	// TODO: Fix the directionality of our critters
	// Implemented per critter	
	protected final void walk(int direction) {
		if (this.hasMoved) { return; }
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
		this.hasMoved = true;
	}

	// Implemented per critter	
	protected final void run(int direction) {	
		if (this.hasMoved) { return; }
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
		this.hasMoved = true;
	}

	//TODO: complete this method
	//Must look in direction either 1 or 2 steps and see if it is occupied.
	//If it's occupied, return the string of the class of the critter.
	protected String look(int direction, boolean steps) {
		int loc_x = 0;
		int loc_y = 0;
	    int	i = 0;		
		if (steps) { i = 1; } else { i = 2; } 	
		switch (direction) {
			case 0: //move east two units
				loc_x = wrapX(i);
			case 1: //move northeast two units
				loc_x = wrapX(i);
				loc_y = wrapY(-i);
			case 2: //move north two units
				loc_y = wrapY(-i);
			case 3: //move northwest two units
				loc_x = wrapX(-i);
				loc_y = wrapY(-i);
			case 4: //move west two units
				loc_x = wrapX(-i);
			case 5: //move southwest two units
				loc_x = wrapX(-i);
				loc_y = wrapY(i);
			case 6: //move south two units
				loc_y = wrapY(i);
			case 7: //southeast two units
				loc_x = wrapX(i);
				loc_y = wrapY(i);	
		}
		this.energy -= Params.look_energy_cost;
		for (Critter a : population) {
			if ((a.x_coord == loc_x) && (a.y_coord == loc_y)) {
				return a.stringRepresentation();
			}
		}
		return null;	
	}

	// Implemented per critter	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy) {
			return;
		}

		offspring.energy = (this.energy / 2);
		this.energy = (int) Math.ceil(this.energy /2);
		switch (direction) {
			case 0: //move east one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.y_coord;
			case 1: //move northeast one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.wrapY(-1);
			case 2: //move north one unit
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.wrapY(-1);
			case 3: //move northwest one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.wrapY(-1);
			case 4: //move west one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.y_coord;
			case 5: //move southwest one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.wrapY(1);
			case 6: //move south one unit
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.wrapY(1);
			case 7: //southeast one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.wrapY(1);	
		} 
		babies.add(offspring);
	}

	// Implemented per critter	
	public abstract void doTimeStep();

	// Implemented per critter	
	public abstract boolean fight(String oponent);

	public abstract String stringRepresentation();	
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
		try {
			newCritterClass = Class.forName(critter_class_name);	
			Constructor<?> constructor = null;
			constructor = newCritterClass.getConstructor();	
			Object newCritter = null;
			newCritter = constructor.newInstance();

			// Critter Initialization
			((Critter) newCritter).x_coord = Critter.getRandomInt(Params.world_width);
			((Critter) newCritter).y_coord = Critter.getRandomInt(Params.world_height);
			((Critter) newCritter).alive = true;
			((Critter) newCritter).energy = Params.start_energy;

			// Add critter to bulk population
			population.add((Critter) newCritter);
		} catch (Exception e) {
			// Lots of different exceptions can be thrown here. Just catch all of them.
			e.printStackTrace();
		}
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		Class<?> crit_class = null;
		try {
			crit_class = Class.forName(critter_class_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Critter> result = new java.util.ArrayList<Critter>();

		for (Critter a : population) {
			if (crit_class.isInstance(a)) {
				result.add(a);
			}
		}
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
		@Override 
		public boolean isAlive() {
			return (super.energy > 0);
		}

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

	/*
	 * Tells if this space in the world is occupied by another critter
	 */
	private boolean isOccupied() {
		for (Critter a : population) {
			if (a == this) { continue; }
			if ((a.x_coord == this.x_coord) && (a.y_coord == this.y_coord)) {
				return true;
			}
		}
		return false;
	}
		
	public static void worldTimeStep() {
		// 1. call doTimeStep() for every critter
		for (Critter a: population) {
			a.doTimeStep();
			a.hasMoved = false;
			if (a.energy <= 0) { 
				a.alive = false; 
			}
		}	


		// 2. Orchestrate all fights
		// TODO: This is a terrible O(n^2) solution. Make it better.
		for (Critter a: population) {
			for (Critter b: population) {
				if (a == b) { continue; }
				if ((a.x_coord == b.x_coord ) && (a.y_coord == b.y_coord)) {
					if (a.isAlive() && b.isAlive()){
						// CONFLICT!
						int x = a.x_coord;
						int y = a.y_coord;
						boolean a_fight = a.fight(b.toString());
						if (a.energy <= 0) { a.alive = false; }
						if (a.isOccupied()) { a.x_coord = x; a.y_coord = y; }
						x = b.x_coord;
						y = b.y_coord;
						boolean b_fight = b.fight(a.toString());
						if (b.energy <= 0) { b.alive = false; }
						if (b.isOccupied()) { b.x_coord = x; b.y_coord = y; }
						// If running away, a or b may die, or a or b may get away.
						if ((a.x_coord == b.x_coord ) && (a.y_coord == b.y_coord)) {
							if (a.isAlive() && b.isAlive()){
							// If there's still a conflict, time to duke it out.
								int a_roll, b_roll;
								if (a_fight) { a_roll = Critter.getRandomInt(a.energy);}
								else { a_roll = 0; }
								if (b_fight) {b_roll = Critter.getRandomInt(b.energy);}
								else { b_roll = 0; }

								if (a_roll > b_roll) {
									// A wins
									a.energy = a.energy + (b.energy)/2;
									b.energy = 0;
									b.alive = false;
								} else if (b_roll > a_roll) {
									// B wins
									b.energy = b.energy + (a.energy)/2;
									a.energy = 0;
									a.alive = false;
								}
								// Coin flip.
								else {
									int flip = Critter.getRandomInt(2);
									if (flip == 1) {
										// A wins
										a.energy = a.energy + (b.energy)/2;
										b.energy = 0;
										b.alive = false;
									}
									else { 
										// B wins
										b.energy = b.energy + (a.energy)/2;
										a.energy = 0;
										a.alive = false;
									}
								}
							}
						}
					}
				}
			}
		} 

		// 3. Deduct rest energy costs
		for (Critter a: population) {
			a.energy = a.energy - Params.rest_energy_cost;
		}	
		// 4. Generate new algae
		for (int i  = 0; i < Params.refresh_algae_count; i += 1) {
			try {
				Critter.makeCritter("project5.Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}	
		// 5. Add new babies into critter collection
		for (Critter a : babies) {
			a.alive = true;
			population.add(a);
		}
		java.util.ArrayList<Critter> temp = new java.util.ArrayList<Critter>(babies);
		babies.removeAll(temp);
		
		// 6. Remove dead critters
		java.util.ArrayList<Critter> toRemove = new java.util.ArrayList<Critter>();
		for (Critter a : population) {
			if (!a.isAlive() || a.energy <= 0) {
				toRemove.add(a);
			}	
		}
		for (Critter a : toRemove) {
			population.remove(a);
		}
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
