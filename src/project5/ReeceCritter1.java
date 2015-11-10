package project5;

import javafx.scene.paint.Color;

/*
 * Critter Description: ReeceCritter1 (Bishop)
 *
 * Movement: 
 * 	Always walks.
 *
 * Turning:
 * 	Always randomly chooses one of four diagonal directions.
 *
 * 	Fighting:
 * 	Always fights.
 *
 * Reproduction:
 * 	If energy > 150, reproduce.
 */

public class ReeceCritter1 extends Critter {

	private int dir;
	private int age;

	@Override 
	public String toString() { return "B"; }

	public String stringRepresentation() { return "project5.ReeceCritter1";}

	public CritterShape viewShape() {
		return Critter.CritterShape.SQUARE;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return Color.STEELBLUE;
	}	

	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return Color.LIGHTBLUE;
	}

	public ReeceCritter1() {
		dir = Critter.getRandomInt(8);
		age = 0;
	}

	public boolean fight(String enemy) {
		return true;
	} 

	@Override 
	public void doTimeStep() {
		age += 1;
		
		// Move
		walk(dir);

		// Reproduce
		if (getEnergy() > 150) {
			ReeceCritter1 newReece1 = new ReeceCritter1();
			reproduce(newReece1, Critter.getRandomInt(8));
		}	

		// Turn -- bishop style movement (diagonals only)
			int new_dir = Critter.getRandomInt(4);
			switch(new_dir) {
				case 0: 
					dir = 1; 
				case 1: 
					dir = 3;
				case 2: 
					dir = 5;
				case 3: 
					dir = 7;
			}
			
	}

	public static void runStats(java.util.List<Critter> reece1) {
		int avg_age = 0;
		int num_crits = 0;
		int avg_energy = 0;
		for (Object obj : reece1) {
			num_crits += 1;
			ReeceCritter1 r = (ReeceCritter1) obj;
			avg_age += r.age;
			avg_energy += r.getEnergy();
		}
		avg_age /= num_crits;
		avg_energy /= num_crits;
		System.out.print("" + num_crits + " total ReeceCritter1s       ");
		System.out.print("Average age: " + avg_age + "       ");
		System.out.print("Average energy: " + avg_energy + "\n");
	}


}
