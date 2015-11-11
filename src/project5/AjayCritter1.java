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

import javafx.scene.paint.Color;
public class AjayCritter1 extends Critter {

	@Override
	public String toString() {return "A";}

	public String stringRepresentation() { return "project5.AjayCritter1";}

	private int dir;
	private int numFights;
	private boolean hasMoved;

	public AjayCritter1() {
		dir = Critter.getRandomInt(8);
		hasMoved = false;
		numFights = 0;
	}
	
	public CritterShape viewShape() {
		return Critter.CritterShape.STAR;
	}

	@Override
	public javafx.scene.paint.Color viewOutlineColor() {
		return Color.DARKRED;
	}	

	@Override
	public javafx.scene.paint.Color viewFillColor() {
		return Color.DARKSEAGREEN;
	}
	public boolean fight(String opponent) {
		if (opponent.equals("project4.Alage")) { //always fight (eat) algae
			return true;
		}
		if (hasMoved == false) {
			if(getEnergy() >= 100) {
				numFights += 1;
				return true;
			}
			else if(getEnergy() < 100) { //not that strong...try to run and find an empty space/algae
				walk(dir);
				return false;
			}
		}
		numFights += 1;
		return true; //these critters have already moved in their time step so they must fight
	}


	public void doTimeStep() {

		hasMoved = false;
		if(getEnergy() < 20) { //running low on energy...must run to find food ASAP
			run(dir);
			hasMoved = true;
		}

		if (getEnergy() >= 20 && getEnergy() < 100) { //decent energy...walk to find food
			walk(dir);
			hasMoved = true;
		}

		if(getEnergy() >= 100 && getEnergy() < 120) { //good energy..should still walk to find food, but can also reproduce
			walk(dir);
			hasMoved = true;
			AjayCritter1 baby = new AjayCritter1();
			reproduce(baby, Critter.getRandomInt(8));
		}

		if(getEnergy() >= 120){ //excellent energy...no need to walk, just reproduce
			AjayCritter1 baby = new AjayCritter1();
			reproduce(baby, Critter.getRandomInt(8));
		}

		//new direction chosen at random
		dir = getEnergy() * Critter.getRandomInt(8) % 8;
	}
	

	public static String runStats(java.util.List<Critter> ajaycritters1) {
		int total_fights = 0 ;
		for(Object obj : ajaycritters1) {
			AjayCritter1 crit = (AjayCritter1) obj;
			total_fights += crit.numFights;
		}
		String output = "";
		output += "Total number of AjayCritter1s: " + ajaycritters1.size();
		output += "\nTotal number of fights: " + total_fights;
		return output;
	}
}

