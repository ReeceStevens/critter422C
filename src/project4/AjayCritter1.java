package project4;

public class AjayCritter1 extends Critter {

	@Override
	public String toString() {return "A";}

	private int dir;
	private int numFights;
	private boolean hasMoved;


	public AjayCritter1() {
		dir = Critter.getRandomInt(8);
		hasMoved = false;
		numFights = 0;
	}
	
	public boolean fight(String opponent) {
		if (opponent.equals("project4.Alage")) {
			return true;
		}
		if (hasMoved == false) {
			if(getEnergy() >= 100) {
				numFights += 1;
				return true;
			}
			else if(getEnergy() < 100) {
				walk(dir);
				return false;
			}
		}
		numFights += 1;
		return true; //these critters have already moved in their time step so they must fight
	}


	public void doTimeStep() {

		hasMoved = false;
		if(getEnergy() < 20) {
			run(dir);
			hasMoved = true;
		}

		if (getEnergy() >= 20 && getEnergy() < 100) {
			walk(dir);
			hasMoved = true;
		}

		if(getEnergy() >= 100 && getEnergy() < 120) {
			walk(dir);
			hasMoved = true;
			AjayCritter1 baby = new AjayCritter1();
			reproduce(baby, Critter.getRandomInt(8));
		}

		if(getEnergy() >= 120){
			AjayCritter1 baby = new AjayCritter1();
			reproduce(baby, Critter.getRandomInt(8));
		}

		//new direction chosen at random
		dir = getEnergy() * Critter.getRandomInt(8) % 8;
	}
	

	public static void runStats(java.util.List<Critter> ajaycritters1) {
		int total_fights = 0 ;
		for(Object obj : ajaycritters1) {
			AjayCritter1 crit = (AjayCritter1) obj;
			total_fights += crit.numFights;
		}
		System.out.println("Total number of AjayCritter1s: " + ajaycritters1.size());
		System.out.println("Total number of fights: " + total_fights);
	}
}

