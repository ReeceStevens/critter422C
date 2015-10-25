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
	private int gene;

	@Override 
	public String toString() { return "R"; }

	public boolean fight(String enemy) {
		if (enemy.equals("project4.Algae")) { return true; }
		if (enemy.equals("project4.ReeceCritter1")) { return false; }
		return true;
	} 

	public ReeceCritter1() {
		// Raw initialization values
		int parent_gene = 1;
		int parent_age = 0;
		dir = Critter.getRandomInt(8);
		age = 0;
		int mutation = Critter.getRandomInt(10);
		int flip = Critter.getRandomInt(2);
		try{ 
			mutation /= (parent_age + 1) / 50; 
		} catch (ArithmeticException e) {
			mutation = 1;
		}
		if (flip == 1) {
			gene = parent_gene + mutation;
		} else {
			gene = parent_gene - mutation; 
			if (gene < 1) { gene = 1;}
		}
	}

	public ReeceCritter1(int parent_gene, int parent_age) {
		dir = Critter.getRandomInt(8);
		age = 0;
		int mutation = Critter.getRandomInt(10);
		int flip = Critter.getRandomInt(2);
		try{ 
			mutation /= (parent_age + 1) / 50; 
		} catch (ArithmeticException e) {
			mutation = 1;
		}
		if (flip == 1) {
			gene = parent_gene + mutation;
		} else {
			gene = parent_gene - mutation; 
			if (gene < 1) { gene = 1;}
		}
	}

	@Override 
	public void doTimeStep() {
		age += 1;
		// Figure out whether to walk, run, or stay
		if (getEnergy() < 70) { walk(dir); }
		else {
			int flip = Critter.getRandomInt(2);
			if (flip == 1) {
				walk(dir);
			}
		}
		// Figure out whether to reproduce
		if (getEnergy() > 180) {
			ReeceCritter1 newReece1 = new ReeceCritter1(this.gene, this.age);
			reproduce(newReece1, Critter.getRandomInt(8));
		}	
		int turn = Critter.getRandomInt(this.gene);
		if (turn == 0) {
			dir = Critter.getRandomInt(8);
		}
			
	}

	public static void runStats(java.util.List<Critter> reece1) {
		int avg_age = 0;
		int num_crits = 0;
		int avg_energy = 0;
		int avg_gene = 0;
		for (Object obj : reece1) {
			num_crits += 1;
			ReeceCritter1 r = (ReeceCritter1) obj;
			avg_age += r.age;
			avg_energy += r.getEnergy();
			avg_gene += r.gene;
		}
		avg_age /= num_crits;
		avg_energy /= num_crits;
		System.out.print("" + num_crits + " total ReeceCritter1s       ");
		System.out.print("Average age: " + avg_age + "       ");
		System.out.print("Average energy: " + avg_energy + "       ");
		System.out.print("Average gene value: " + avg_gene + "\n");
	}

}
