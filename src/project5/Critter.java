/* CRITTERS Critter.java
 * EE422C Project 5 submission by
 * Replace <...> with your actual data.
 * Reece Stevens
 * rgs835
 * 16340
 * Ajay Rastogi
 * asr2368
 * 16345
 * Slip days used: 0
 * Fall 2015
 * KNOWN ERRORS: PRINTING THE ERROR MESSAGES CAUSES A SHIFTING OF THE TEXTFIELDS BECAUSE OF AN EXPANSION OF THE GRID
 *  SPACE AT WHICH THE ERROR MESSAGE IS PRINTED -- WE SHORTENED THE ERROR MESSAGES AS MUCH AS POSSIBLE TO COMPENSATE.
 */
package project5;

import java.util.*;
import java.lang.reflect.Constructor;
import java.util.List;
import javafx.scene.shape.*;
import javafx.scene.Group;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {
	
	//for auto-stepping purposes
	public static boolean simulating = false;
	public static int i = 0;
	
	//shape enumerations
	public enum CritterShape {
		CIRCLE,
		SQUARE,
		TRIANGLE,
		DIAMOND,
		STAR
	}
	
	/* the default color is white, which I hope makes critters invisible by default
	 * If you change the background color of your View component, then update the default
	 * color to be the same as you background 
	 * 
	 * critters must override at least one of the following three methods, it is not 
	 * proper for critters to remain invisible in the view
	 * 
	 * If a critter only overrides the outline color, then it will look like a non-filled 
	 * shape, at least, that's the intent. You can edit these default methods however you 
	 * need to, but please preserve that intent as you implement them. 
	 */
	public javafx.scene.paint.Color viewColor() { 
		return javafx.scene.paint.Color.WHITE; 
	}
	
	public javafx.scene.paint.Color viewOutlineColor() { return viewColor(); }
	public javafx.scene.paint.Color viewFillColor() { return viewColor(); }
	
	public abstract CritterShape viewShape(); 

	private static String [] validCritters = { "project5.Algae", "project5.Craig", "project5.ReeceCritter1", "project5.ReeceCritter2", "project5.AjayCritter1", "project5.AjayCritter2", "project5.Craig2" };

	private static java.util.Random rand = new java.util.Random();
	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}
	
	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}
	
	/* a one-character long string that visually depicts your critter in the ASCII interface */
	public String toString() { return ""; }

	private boolean alive;
	public boolean isAlive() {
		return alive;
	}
	
	private int energy = 0;
	protected int getEnergy() { return energy; }
	
	private int x_coord;
	private int y_coord;
	private boolean hasMoved;

	//These two functions exist to implement the 2-D torus projection property of the world
	private final int wrapX(int steps) {
		if ((x_coord + steps) < 0){
			return (Params.world_width - steps);
		} 
		else if ((x_coord + steps) > (Params.world_width - 1)){
			return (steps - 1);
		}
		else {return x_coord += steps;}
	}

	private final int wrapY(int steps) {
		if ((y_coord + steps) < 0){
			return (Params.world_height - steps);
		} 
		else if ((y_coord + steps) > (Params.world_height - 1)){
			return (steps - 1);
		}
		else {return y_coord += steps;}
	}


	// TODO: Fix the directionality of our critters
	// Implemented per critter	
	protected final void walk(int direction) {
		if (this.hasMoved) { return; }
		switch (direction) {
			case 0: //move east one unit
				x_coord = wrapX(1);
			case 1: //move northeast one unit
				x_coord = wrapX(1);
				y_coord = wrapY(-1);
			case 2: //move north one unit
				y_coord = wrapY(-1);
			case 3: //move northwest one unit
				x_coord = wrapX(-1);
				y_coord = wrapY(-1);
			case 4: //move west one unit
				x_coord = wrapX(-1);
			case 5: //move southwest one unit
				x_coord = wrapX(-1);
				y_coord = wrapY(1);
			case 6: //move south one unit
				y_coord = wrapY(1);
			case 7: //southeast one unit
				x_coord = wrapX(1);
				y_coord = wrapY(1);	
		}
		energy -= Params.walk_energy_cost;
		this.hasMoved = true;
	}

	// Implemented per critter	
	protected final void run(int direction) {	
		if (this.hasMoved) { return; }
		switch (direction) {
			case 0: //move east two units
				x_coord = wrapX(2);
			case 1: //move northeast two units
				x_coord = wrapX(2);
				y_coord = wrapY(-2);
			case 2: //move north two units
				y_coord = wrapY(-2);
			case 3: //move northwest two units
				x_coord = wrapX(-2);
				y_coord = wrapY(-2);
			case 4: //move west two units
				x_coord = wrapX(-2);
			case 5: //move southwest two units
				x_coord = wrapX(-2);
				y_coord = wrapY(2);
			case 6: //move south two units
				y_coord = wrapY(2);
			case 7: //southeast two units
				x_coord = wrapX(2);
				y_coord = wrapY(2);	
		}
		energy -= Params.run_energy_cost;
		this.hasMoved = true;
	}

	//TODO: complete this method
	//Must look in direction either 1 or 2 steps and see if it is occupied.
	//If it's occupied, return the string of the class of the critter.
	protected String look(int direction, boolean steps) {
		int loc_x = 0;
		int loc_y = 0;
	    int	i = 0;		
		if (steps) { i = 1; } else { i = 2; } 	
		switch (direction) {
			case 0: //move east two units
				loc_x = wrapX(i);
			case 1: //move northeast two units
				loc_x = wrapX(i);
				loc_y = wrapY(-i);
			case 2: //move north two units
				loc_y = wrapY(-i);
			case 3: //move northwest two units
				loc_x = wrapX(-i);
				loc_y = wrapY(-i);
			case 4: //move west two units
				loc_x = wrapX(-i);
			case 5: //move southwest two units
				loc_x = wrapX(-i);
				loc_y = wrapY(i);
			case 6: //move south two units
				loc_y = wrapY(i);
			case 7: //southeast two units
				loc_x = wrapX(i);
				loc_y = wrapY(i);	
		}
		this.energy -= Params.look_energy_cost;
		for (Critter a : population) {
			if ((a.x_coord == loc_x) && (a.y_coord == loc_y)) {
				return a.stringRepresentation();
			}
		}
		return null;	
	}

	// Implemented per critter	
	protected final void reproduce(Critter offspring, int direction) {
		if(this.energy < Params.min_reproduce_energy) {
			return;
		}

		offspring.energy = (this.energy / 2);
		this.energy = (int) Math.ceil(this.energy /2);
		switch (direction) {
			case 0: //move east one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.y_coord;
			case 1: //move northeast one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.wrapY(-1);
			case 2: //move north one unit
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.wrapY(-1);
			case 3: //move northwest one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.wrapY(-1);
			case 4: //move west one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.y_coord;
			case 5: //move southwest one unit
				offspring.x_coord = this.wrapX(-1);
				offspring.y_coord = this.wrapY(1);
			case 6: //move south one unit
				offspring.x_coord = this.x_coord;
				offspring.y_coord = this.wrapY(1);
			case 7: //southeast one unit
				offspring.x_coord = this.wrapX(1);
				offspring.y_coord = this.wrapY(1);	
		} 
		babies.add(offspring);
	}

	// Implemented per critter	
	public abstract void doTimeStep();

	// Implemented per critter	
	public abstract boolean fight(String oponent);

	public abstract String stringRepresentation();	
	/* create and initialize a Critter subclass
	 * critter_class_name must be the name of a concrete subclass of Critter, if not
	 * an InvalidCritterException must be thrown
	 */
	public static void makeCritter(String critter_class_name) throws InvalidCritterException {
		Class<?> newCritterClass = null; 

		// Check to make sure the critter class is valid
		boolean exists = false;
		for (String name : validCritters) {
			if (name.equals(critter_class_name)) { exists = true; }
		}
		if (!exists) { throw new InvalidCritterException(critter_class_name); }

		// If critter is valid, instantiate it and put it in the population
		try {
			newCritterClass = Class.forName(critter_class_name);	
			Constructor<?> constructor = null;
			constructor = newCritterClass.getConstructor();	
			Object newCritter = null;
			newCritter = constructor.newInstance();

			// Critter Initialization
			((Critter) newCritter).x_coord = Critter.getRandomInt(Params.world_width);
			((Critter) newCritter).y_coord = Critter.getRandomInt(Params.world_height);
			((Critter) newCritter).alive = true;
			((Critter) newCritter).energy = Params.start_energy;

			// Add critter to bulk population
			population.add((Critter) newCritter);
		} catch (Exception e) {
			// Lots of different exceptions can be thrown here. Just catch all of them.
			e.printStackTrace();
		}
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		Class<?> crit_class = null;
		try {
			crit_class = Class.forName(critter_class_name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Critter> result = new java.util.ArrayList<Critter>();

		for (Critter a : population) {
			if (crit_class.isInstance(a)) {
				result.add(a);
			}
		}
		return result;
	}
	
	public static String runStats(List<Critter> critters) {
		String output = "" + critters.size() + " critters as follows -- ";
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();
		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string,  1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			output += prefix + s + ":" + critter_count.get(s);
			prefix = ", ";
		}
		output += "\n";
		return output;
	}
	
	/* the TestCritter class allows some critters to "cheat". If you want to 
	 * create tests of your Critter model, you can create subclasses of this class
	 * and then use the setter functions contained here. 
	 * 
	 * NOTE: you must make sure that the setter functions work with your implementation
	 * of Critter. That means, if you're recording the positions of your critters
	 * using some sort of external grid or some other data structure in addition
	 * to the x_coord and y_coord functions, then you MUST update these setter functions
	 * so that they correctly update your grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		@Override 
		public boolean isAlive() {
			return (super.energy > 0);
		}

		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}
		
		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}
		
		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}
	
	private	static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();

	/*
	 * Tells if this space in the world is occupied by another critter
	 */
	private boolean isOccupied() {
		for (Critter a : population) {
			if (a == this) { continue; }
			if ((a.x_coord == this.x_coord) && (a.y_coord == this.y_coord)) {
				return true;
			}
		}
		return false;
	}
		
	public static void worldTimeStep() {
		// 1. call doTimeStep() for every critter
		for (Critter a: population) {
			a.doTimeStep();
			a.hasMoved = false;
			if (a.energy <= 0) { 
				a.alive = false; 
			}
		}	


		// 2. Orchestrate all fights
		// TODO: This is a terrible O(n^2) solution. Make it better.
		for (Critter a: population) {
			for (Critter b: population) {
				if (a == b) { continue; }
				if ((a.x_coord == b.x_coord ) && (a.y_coord == b.y_coord)) {
					if (a.isAlive() && b.isAlive()){
						// CONFLICT!
						int x = a.x_coord;
						int y = a.y_coord;
						boolean a_fight = a.fight(b.toString());
						if (a.energy <= 0) { a.alive = false; }
						if (a.isOccupied()) { a.x_coord = x; a.y_coord = y; }
						x = b.x_coord;
						y = b.y_coord;
						boolean b_fight = b.fight(a.toString());
						if (b.energy <= 0) { b.alive = false; }
						if (b.isOccupied()) { b.x_coord = x; b.y_coord = y; }
						// If running away, a or b may die, or a or b may get away.
						if ((a.x_coord == b.x_coord ) && (a.y_coord == b.y_coord)) {
							if (a.isAlive() && b.isAlive()){
							// If there's still a conflict, time to duke it out.
								int a_roll, b_roll;
								if (a_fight) { a_roll = Critter.getRandomInt(a.energy);}
								else { a_roll = 0; }
								if (b_fight) {b_roll = Critter.getRandomInt(b.energy);}
								else { b_roll = 0; }

								if (a_roll > b_roll) {
									// A wins
									a.energy = a.energy + (b.energy)/2;
									b.energy = 0;
									b.alive = false;
								} else if (b_roll > a_roll) {
									// B wins
									b.energy = b.energy + (a.energy)/2;
									a.energy = 0;
									a.alive = false;
								}
								// Coin flip.
								else {
									int flip = Critter.getRandomInt(2);
									if (flip == 1) {
										// A wins
										a.energy = a.energy + (b.energy)/2;
										b.energy = 0;
										b.alive = false;
									}
									else { 
										// B wins
										b.energy = b.energy + (a.energy)/2;
										a.energy = 0;
										a.alive = false;
									}
								}
							}
						}
					}
				}
			}
		} 

		// 3. Deduct rest energy costs
		for (Critter a: population) {
			a.energy = a.energy - Params.rest_energy_cost;
		}	
		// 4. Generate new algae
		for (int i  = 0; i < Params.refresh_algae_count; i += 1) {
			try {
				Critter.makeCritter("project5.Algae");
			} catch (InvalidCritterException e) {
				e.printStackTrace();
			}
		}	
		// 5. Add new babies into critter collection
		for (Critter a : babies) {
			a.alive = true;
			population.add(a);
		}
		java.util.ArrayList<Critter> temp = new java.util.ArrayList<Critter>(babies);
		babies.removeAll(temp);
		
		// 6. Remove dead critters
		java.util.ArrayList<Critter> toRemove = new java.util.ArrayList<Critter>();
		for (Critter a : population) {
			if (!a.isAlive() || a.energy <= 0) {
				toRemove.add(a);
			}	
		}
		for (Critter a : toRemove) {
			population.remove(a);
		}
	}

	public static void displayWorld() {
		/* CLI FORMAT 
		String[][] output = new String[Params.world_height+2][Params.world_width+2]; // Draw Grid
		for (int i = 0; i < Params.world_height+2; i +=1 ) {
			for (int j = 0; j < Params.world_width+2; j +=1 ) {
				if (i == 0 || i == Params.world_height+1) {
					if (j == 0 || j == Params.world_width+1) {
						output[i][j] = "+";
						continue;
					}
					else {
						output[i][j] = "-";
					}
				}
				else if (j == 0 || j == Params.world_width+1) {
					output[i][j] = "|";
				}
				else {
					output[i][j] = " ";
				}
			}
		}
		// Draw Critters
		for (Critter a : population) {
			output[a.y_coord + 1][a.x_coord + 1] = a.toString();			
		}
		for (int i = 0; i < Params.world_height+2; i += 1) {
			for (int j = 0; j < Params.world_width+2; j += 1) {
				System.out.print(output[i][j]);
			}
			System.out.print("\n");
		}*/
		// GUI FORMAT
		// TODO: work in progress, correctly draw the shapes.
		double window_w = Main.critterStage.getWidth();
		double window_h = Main.critterStage.getHeight();
		double width, height;
		// If the window is first being created
		/*
		if (Double.isNaN(Main.critterStage.getWidth())) {
			width = 500.0;
			height = 500.0;
		// Otherwise scale the grid to the current size of the window
		} else {
			width = Main.crit_grid.getWidth();
			height = Main.crit_grid.getHeight();
		}*/
		width = 500.0;
		height = 500.0;
		GridPane master_grid = new GridPane();
		Main.crit_grid.getChildren().clear();
		System.out.printf("Width %f height %f\n",window_w, window_h);
		double min = 0.0; 
		if (width < height) { min = width/Params.world_width; }
		else {min = height/Params.world_height; }
		System.out.printf("minimum size: %f", min);
		min = Math.floor(min);
		
		for (int i = 0; i < Params.world_width; i += 1) {
			for (int j = 0; j < Params.world_height; j += 1) {
				javafx.scene.shape.Rectangle clear_rect = new javafx.scene.shape.Rectangle(0,0,min-1,min-1);
				clear_rect.setFill(Color.WHITE);
				clear_rect.setStroke(Color.WHITE);
				Main.crit_grid.add(clear_rect,j,i);
			}
		}
			
		min = min - 1;
		double mid = Math.floor(min/2);
		for (Critter a : population) {
			CritterShape shape = a.viewShape();
			javafx.scene.shape.Shape crit_shape = null;
			switch(shape){
				case CIRCLE:
					crit_shape = new javafx.scene.shape.Circle(mid,mid,mid);
					break;
				case SQUARE:
					crit_shape = new javafx.scene.shape.Rectangle(0,0,min,min);
					break;
				case TRIANGLE: 
					crit_shape = new javafx.scene.shape.Polygon(new double[]{mid,2.0,min-1,min-1,2.0,min-1});
					break;
					//grid.add(new javafx.scene.shape.Polygon(), a.x_coord, a.y_coord);
				case DIAMOND:
					crit_shape = new javafx.scene.shape.Polygon(new double[]{mid,1.0,min-1,mid,mid,min-1, 1.0,mid});
					break;
					//grid.add(new javafx.scene.shape.Rectangle(), a.x_coord, a.y_coord);
				case STAR:
					//crit_shape = new javafx.scene.shape.Rectangle(0,0,min,min);
					crit_shape = new javafx.scene.shape.Polygon(new double[]{mid,2.0,(mid+mid/2),(mid/2),min-2,mid,(mid+mid/2),(mid+mid/2),min-2,min-2,mid,(mid+mid/2),2.0,min-2,2.0, (mid+mid/2), 2.0, mid, (mid/2), (mid/2)});
					break;
					//grid.add(new javafx.scene.shape.Rectangle(), a.x_coord, a.y_coord);
			}
			crit_shape.setStroke(a.viewOutlineColor());
			crit_shape.setFill(a.viewFillColor());
			Main.crit_grid.add(crit_shape, a.x_coord, a.y_coord);
		}	


		// World Canvas
		Main.critterStage.setTitle("Critter World");
		//Group root = new Group();
		//root.getChildren().add(grid);
		//grid.setGridLinesVisible(true);
		//Main.critterStage.setScene(new Scene(grid,Math.ceil(width+ 50.0),Math.ceil(height + 50.0)));
		master_grid.add(Main.crit_grid,0,0);

		// Controller
		Main.controlStage.setTitle("Controller");
		GridPane control_grid = new GridPane();
		control_grid.setAlignment(Pos.CENTER);
		control_grid.setHgap(10);
		control_grid.setVgap(10);
		control_grid.setPadding(new Insets(25,25,25,25));
		int row = 0;

		// Add Field for Critter type.
		Label critName = new Label("Critter Name (e.g. Craig):");
		control_grid.add(critName, 0, row);
		//TextField critNameField = new TextField();
		//control_grid.add(critNameField, 1, row);
		row += 1;
		// Add Radio Buttons for Critter Classes
		RadioButton rdo_rc1 = new RadioButton("ReeceCritter1");
		RadioButton rdo_rc2 = new RadioButton("ReeceCritter2");
		RadioButton rdo_ac1 = new RadioButton("AjayCritter1");
		RadioButton rdo_ac2 = new RadioButton("AjayCritter2");
		RadioButton rdo_craig = new RadioButton("Craig");
		RadioButton rdo_algae = new RadioButton("Algae");
		ToggleGroup group = new ToggleGroup();
		rdo_ac1.setToggleGroup(group);
		rdo_ac2.setToggleGroup(group);
		rdo_rc1.setToggleGroup(group);
		rdo_rc2.setToggleGroup(group);
		rdo_craig.setToggleGroup(group);
		rdo_algae.setToggleGroup(group);
		rdo_ac1.setSelected(true);
		VBox vbox1 = new VBox(20,rdo_ac1,rdo_ac2,rdo_rc1);
		vbox1.setPadding(new Insets(10));
		VBox vbox2 = new VBox(20,rdo_rc2,rdo_craig,rdo_algae);
		vbox2.setPadding(new Insets(10));
		control_grid.add(vbox1,0,row);
		control_grid.add(vbox2,1,row);

		// Add Field for Number of Critters
		Label numCrits = new Label("No of critters:");
		row += 1;
		control_grid.add(numCrits, 0, row);
		TextField critNumField = new TextField();
		control_grid.add(critNumField, 1, row);

		// Add Field for Number of Steps
		Label step = new Label("No of steps:");
		row += 1;
		control_grid.add(step, 0, row);
		TextField stepNumberField = new TextField();
		control_grid.add(stepNumberField, 1, row);
	
		// Add Field for Critter stats
		Label stats = new Label("Display stats for:");
		row += 1;
		control_grid.add(stats, 0, row);
		TextField statsField = new TextField();
		control_grid.add(statsField, 1, row);
		
		//Make button
		Button makeBtn = new Button("Make critters");
		HBox hbMakeBtn = new HBox(10);
		hbMakeBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbMakeBtn.getChildren().add(makeBtn);
		control_grid.add(hbMakeBtn, 3, 2);

		//Step button
		Button stepBtn = new Button("Execute steps");
		HBox hbStepBtn = new HBox(10);
		hbStepBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbStepBtn.getChildren().add(stepBtn);
		control_grid.add(hbStepBtn, 3, 3);

		//AutoStep button
		Button astepBtn = new Button("Begin stepping");
		HBox hbaStepBtn = new HBox(10);
		hbaStepBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbaStepBtn.getChildren().add(astepBtn);
		control_grid.add(hbaStepBtn, 3, 5);

		//QuitStep button
		Button qstepBtn = new Button("Stop stepping");
		HBox hbqStepBtn = new HBox(10);
		hbqStepBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbqStepBtn.getChildren().add(qstepBtn);
		control_grid.add(hbqStepBtn, 3, 6);

		//SoftReset button
		Button srBtn = new Button("Reset");
		HBox hbsrBtn = new HBox(10);
		hbsrBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbsrBtn.getChildren().add(srBtn);
		control_grid.add(srBtn, 3, 7);

		//Stats button
		Button statsBtn = new Button("Display stats");
		HBox hbStatsBtn = new HBox(10);
		hbStatsBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbStatsBtn.getChildren().add(statsBtn);
		control_grid.add(hbStatsBtn, 3, 4);

		//Quit button
		Button quitBtn = new Button("Quit");
		HBox hbQuitBtn = new HBox(10);
		hbQuitBtn.setAlignment(Pos.TOP_RIGHT);
		row += 1;
		hbQuitBtn.getChildren().add(quitBtn);
		control_grid.add(hbQuitBtn, 4, 7);

		// Action when Make Critters Button is pressed.
		final Text actionTarget = new Text();
		row += 2;
		control_grid.add(actionTarget, 0, row);

		master_grid.add(control_grid,1,0);
		Main.critterStage.setScene(new Scene(master_grid,window_w, window_h-22));
		Main.critterStage.show();
		makeBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//String name = critNameField.getText();
				String name = "";
				String numString = critNumField.getText();
				if (rdo_ac1.isSelected()) name = "AjayCritter1";
				if (rdo_ac2.isSelected()) name = "AjayCritter2";
				if (rdo_rc1.isSelected()) name = "ReeceCritter1";
				if (rdo_rc2.isSelected()) name = "ReeceCritter2";
				if (rdo_craig.isSelected()) name = "Craig";
				if (rdo_algae.isSelected()) name = "Algae";
					try{ 
						for (int i = 0; i < Integer.parseInt(numString); i += 1) {
							try {
								Critter.makeCritter("project5.".concat(name));
							} catch (InvalidCritterException e) {
								actionTarget.setFill(Color.FIREBRICK);
								actionTarget.setText("Please enter a valid critter class.");
								return;	
							}
						}
					} catch (NumberFormatException e) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Enter a valid number of critters.");	
						return;
					}
				//actionTarget.setFill(Color.FIREBRICK);
				//actionTarget.setText("TODO: message to display how many Critters added etc.");	
				Critter.displayWorld();	
			}			
		});
		
		stepBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				String numSteps = stepNumberField.getText();
				//TODO: make a more graceful error message if someone inputs an invalid critter type
				if (numSteps == null) {
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Enter a valid number of steps");
					return;
				}
				else {
					try{ 
						if (Integer.parseInt(numSteps) < 1) {
						//	throw new NumberFormatException("Please enter a number larger than 1");
							stepNumberField.setText("");			
							actionTarget.setFill(Color.FIREBRICK);
							actionTarget.setText("Enter a number larger than 1");
							return;
						}
						for (int i = 0; i < Integer.parseInt(numSteps); i += 1) {
							Critter.worldTimeStep();
						}
					} catch (NumberFormatException e) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Enter an integer number of steps.");	
						return;
					}
				}
				//actionTarget.setFill(Color.FIREBRICK);
				//actionTarget.setText("TODO: message to display how many Critters added etc.");	
				Critter.displayWorld();	
			}			
		});

		statsBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override	
			public void handle(ActionEvent event) {
				String name = statsField.getText();
				List<Critter> instances = null;  
				//TODO: make a more graceful error message if someone inputs an invalid critter type
				if ((name == null)) {
					actionTarget.setFill(Color.FIREBRICK);
					actionTarget.setText("Please enter a critter class.");	
					return;
				}
				else {
					name = "project5."+name;
					try{
						instances = Critter.getInstances(name);	
					} catch (InvalidCritterException e) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Please enter a valid critter class.");	
					} catch (NullPointerException e) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Please enter a valid critter class.");	
					}

					// No instances of the class are alive.
					if (instances.size() <= 0) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("None of these critters are left.");		
					}

					Class<?> critter_class = null;
					Class [] paramList = new Class[1];
					paramList[0] = java.util.List.class;
					String output = "";
					try{
						critter_class = Class.forName(name);
						java.lang.reflect.Method runStats = critter_class.getMethod("runStats", paramList);
						Object retval = runStats.invoke(critter_class, instances);
						output = (String) retval;
						actionTarget.setFill(Color.GREEN);
						actionTarget.setText(output);
					} catch (Exception e) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Please try again.");	
						e.printStackTrace();
						return;
					}
				}
				//actionTarget.setText("TODO: message to display how many Critters added etc.");	
				//Critter.displayWorld();	
			}			
		});
		quitBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Main.critterStage.close(); //quit the display pane
				Main.initStage.close(); //quit the init pane
			}			
		});
		srBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				population.clear();
				babies.clear();
				Critter.displayWorld();
				Main.initStage.toFront(); //bring the initialization screen back to the front
			}			
		});

	/*	astepBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//	while(i < 100000000) {
				//		i++;
				//	}
				//	i = 0;
				simulating = true;
				new Thread() {
					public void run () {
					try {
						while (simulating == true) {
							Critter.worldTimeStep();
							Critter.displayWorld();
						        Thread.sleep(1000);
							System.out.println("a");
						}
					} catch(InterruptedException ex) {
						    ex.printStackTrace();
					}
					}
				}.start(); 
				
			}			
		});
		qstepBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				simulating = false;
			}			
		}); */
	}
}
