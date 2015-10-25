package project4;

import project4.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae() {
		this.setEnergy(Params.start_energy);
	}

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}

	public static void runStats(java.util.List<Critter> algaes) {
		Critter.runStats(algaes);
	}
}
