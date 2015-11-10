package project5;

/*
 * Critter Description: ReeceCritter2 (Rook)
 *
 * Movement: 
 * 	Walks 2/3 of the time.
 *
 * Turning:
 * 	Always randomly chooses one of four cardinal directions.
 *
 * 	Fighting:
 * 	If it hasn't moved, it will flee from fights with its own species.
 * 	Otherwise, it will always fight.
 *
 * Reproduction:
 * 	If energy > 180, reproduce.
 */

public class ReeceCritter2 extends Critter {

	private int dir;
	private int age;
	private boolean hasMoved;

	@Override 
	public String toString() { return "R"; }

	public String stringRepresentation() { return "project5.ReeceCritter2";}

	public ReeceCritter2() {
		dir = Critter.getRandomInt(8);
		age = 0;
	}

	public CritterShape viewShape() {
		return Critter.CritterShape.STAR;
	}

	public boolean fight(String enemy) {
		// If it can afford it, it won't fight its own species.
		if (!hasMoved) {
			if (enemy.equals("project4.ReeceCritter2")) { walk(dir); return false; }
		}
		return true;
	} 

	@Override 
	public void doTimeStep() {
		age += 1;

		// Decide whether to move or not.
		int flip = Critter.getRandomInt(3);
		if (flip != 1) {
			walk(dir);
			hasMoved = true;
		}
	
		// Figure out whether to reproduce
		if (getEnergy() > 180) {
			ReeceCritter2 newReece1 = new ReeceCritter2();
			reproduce(newReece1, Critter.getRandomInt(8));
		}	

		// Turn -- rook style movement (cardinals only)
			int new_dir = Critter.getRandomInt(4);
			switch(new_dir) {
				case 0: 
					dir = 0; 
				case 1: 
					dir = 2;
				case 2: 
					dir = 4;
				case 3: 
					dir = 6;
			}
			
	}

	public static String runStats(java.util.List<Critter> reece2) {
		int avg_age = 0;
		int num_crits = 0;
		int avg_energy = 0;
		for (Object obj : reece2) {
			num_crits += 1;
			ReeceCritter2 r = (ReeceCritter2) obj;
			avg_age += r.age;
			avg_energy += r.getEnergy();
		}
		avg_age /= num_crits;
		avg_energy /= num_crits;
		String output = "";
		output += "" + num_crits + " total ReeceCritter2       ";
		output += "Average age: " + avg_age + "       ";
		output += "Average energy: " + avg_energy + "\n";
		return output;
	}


}
