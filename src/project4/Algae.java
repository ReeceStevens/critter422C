package project4;

import project4.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae(int x, int y) {
		this.x_coord = x;
		this.y_coord = y;
	}

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
}
