package project4;

import java.util.*;

public class CritterCollection {
	private ArrayList<Critter> all_critters;
	public void addCritter(Critter a) {
		all_critters.add(a);
	}
	public void removeCritter(Critter a){ 
		all_critters.remove(a);
	}
}
