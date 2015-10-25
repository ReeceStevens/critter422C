package project4;

/*
 * Critter Description: ReeceCritter1
 *
 * Movement: 
 * 	If energy > 100, walk about 50% of the time.
 * 	If energy < 100, walk all the time.
 *
 * Reproduction:
 * 	If energy > 150, reproduce.
 */

public class ReeceCritter1 extends Critter {

	private int dir;
	private int age;

	@Override 
	public String toString() { return "R"; }

	public boolean fight (String enemy) { return true; }
	/*
	public boolean fight(String enemy) {
		if ((getEnergy() < 70) && (getEnergy() > 30)) { 
			run(dir);
			return false;
		}	
		else { return true; }
	} */

	public ReeceCritter1() {
		dir = Critter.getRandomInt(8);
		age = 0;
	}

	@Override 
	public void doTimeStep() {
		age += 1;
		// Figure out whether to walk, run, or stay
		if (getEnergy() < 100) { walk(dir); }
		else {
			int flip = Critter.getRandomInt(2);
			if (flip == 1) {
				walk(dir);
			}
			else {
				dir = Critter.getRandomInt(8);
			}
		}
		// Figure out whether to reproduce
		if (getEnergy() > 100) {
			ReeceCritter1 newReece1 = new ReeceCritter1();
			reproduce(newReece1, Critter.getRandomInt(8));
		}	
			
	}

	public static void runStats(java.util.List<Critter> reece1) {
		int avg_age = 0;
		int num_crits = 0;
		for (Object obj : reece1) {
			num_crits += 1;
			ReeceCritter1 r = (ReeceCritter1) obj;
			avg_age += r.age;
		}
		avg_age /= num_crits;
		System.out.print("" + num_crits + " total ReeceCritter1s       ");
		System.out.print("Average age: " + avg_age + "\n");
	}

}
