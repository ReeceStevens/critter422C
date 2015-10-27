
package project4;

public class AjayCritter2 extends Critter {

	@Override
	public String toString() {return "X";}

	private int age;
	private int dir;
	private int prev_dir;
	private int num_walks;
	private int num_runs;
	private boolean hasMoved;


	public AjayCritter2() {
		age = 0;
		dir = Critter.getRandomInt(8);
		prev_dir = Critter.getRandomInt(8);
		hasMoved = false;
		num_walks = 0;
		num_runs = 0;
	}
	
	public boolean fight(String opponent) {
		if (opponent.equals("project4.Alage")) {
			return true;
		}
		if (hasMoved == false) {
			if(getEnergy() >= 100) {
				return true;
			}
			else if(getEnergy() < 100) {
				run(dir);
				num_runs += 1;
				return false;
			}
		}
		return true; //these critters have already moved in their time step so they must fight
	}


	public void doTimeStep() { 
		
		age += 1;
		hasMoved = false;
		if(getEnergy() < 20) { //is weak...walks to find food so that it doesnt waste extra energy
			walk(dir);
			num_walks += 1;
			hasMoved = true;
		}

		if (getEnergy() >= 20 && getEnergy() < 100) { //moderately weak...can use energy on running to find food/beat other critters
			run(dir);
			num_runs += 1;
			hasMoved = true;
		}

		if(getEnergy() >= 100 && getEnergy() < 120) { //pretty good energy...can run and then reproduce
			run(dir);
			num_runs += 1;
			hasMoved = true;
			AjayCritter2 baby = new AjayCritter2();
			reproduce(baby, Critter.getRandomInt(8));
		}
		if(getEnergy() >= 120){ //very good energy...only reproduces
			AjayCritter2 baby = new AjayCritter2();	
			reproduce(baby, Critter.getRandomInt(8));
		}

		//new direction chosen at random EXCEPT IT CANNOT BE THE SAME DIRECTION THE CRITTER JUST CAME FROM
		//if the critter just came from a certain spot, it has already either eaten the algae at taht spot or has 
		//killed the critter taht was previously there, so retracing its step will most likely be a waste of a move and energy
		int temp_dir = dir;
		dir = Critter.getRandomInt(8);
		while (dir == prev_dir) {
			dir = Critter.getRandomInt(8);
		}
		prev_dir = temp_dir;

	}
	

	public static void runStats(java.util.List<Critter> ajaycritters2) {
		int total_walks = 0;
		int total_runs = 0;
		int max_age = 0;
		for(Object obj : ajaycritters2) {
			AjayCritter2 crit = (AjayCritter2) obj;
			total_walks += crit.num_walks;
			total_runs += crit.num_runs;
			if (crit.age > max_age) {
				max_age = crit.age;
			}
		}
		System.out.println("Total number of AjayCritter2s: " + ajaycritters2.size());
		System.out.println("Total number of walks: " + total_walks);
		System.out.println("Total number of runs: " + total_runs);
		System.out.println("The oldest critter is " + max_age + " years old.");
	}
}

