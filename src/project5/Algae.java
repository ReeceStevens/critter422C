package project5;

import project5.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae() {
		this.setEnergy(Params.start_energy);
	}

	public String toString() { return "@"; }
	public String stringRepresentation() { return "project5.Algae";}
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}

	public static void runStats(java.util.List<Critter> algaes) {
		Critter.runStats(algaes);
	}
}
