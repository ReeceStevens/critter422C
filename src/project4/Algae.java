package project4;

import project4.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae() {
		int x = Critter.getRandomInt(Params.world_width-1);
		int y = Critter.getRandomInt(Params.world_height-1);
		this.setXCoord(x);
		this.setYCoord(y);
		this.setEnergy(Params.start_energy);
	}

	public String toString() { return "@"; }
	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
}
