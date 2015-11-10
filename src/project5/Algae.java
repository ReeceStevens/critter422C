package project5;

import project5.Critter.TestCritter;

public class Algae extends TestCritter {

	public Algae() {
		this.setEnergy(Params.start_energy);
	}

	public String toString() { return "@"; }
	public String stringRepresentation() { return "project5.Algae";}

	public CritterShape viewShape() {
		return Critter.CritterShape.CIRCLE;
	}

	@Override 
	public javafx.scene.paint.Color viewOutlineColor() { return javafx.scene.paint.Color.DARKGREEN; }
	@Override 
	public javafx.scene.paint.Color viewFillColor() { return javafx.scene.paint.Color.LIGHTGREEN; }

	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}

	public static void runStats(java.util.List<Critter> algaes) {
		Critter.runStats(algaes);
	}
}
