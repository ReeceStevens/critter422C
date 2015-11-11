package project5;

import javafx.application.Application;
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
import javafx.stage.Stage;

public class Main extends Application {

	public static GridPane map = new GridPane();
	public static Stage initStage;
	public static Stage controlStage;
	public static Stage critterStage;
	public static GridPane crit_grid;
	public static Canvas crit_canvas;
	
	@Override
	public void start(Stage primaryStage) {
		
		try {
			initStage = primaryStage;
			primaryStage.setTitle("Initialization Settings");
			controlStage = new Stage();
			critterStage = new Stage();
			crit_grid = new GridPane();
			//crit_canvas = new Canvas(500,500);

			//crit_pane = new GridPane();
			//crit_pane.setGridLinesVisible(true);
			
			// Add a grid pane to lay out the buttons and text fields.
			GridPane grid = new GridPane();
			grid.setAlignment(Pos.CENTER);
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(25, 25, 25, 25));
			
			int row = 0;
			
			// Add Field for Critter type.
			Label critName = new Label("Critter Name (e.g. Algae):");
			grid.add(critName, 0, row);
			TextField critNameField = new TextField();
			//row++;
			grid.add(critNameField, 1, row);
			
			// Add Field for No. of Critters
			Label numCrits = new Label("No of critters:");
			row++;
			grid.add(numCrits, 0, row);
			TextField critNumField = new TextField();
			//row++;
			grid.add(critNumField, 1, row);
			
			// Add Button to add Critters.
			Button addBtn = new Button("Add critters");
			HBox hbAddBtn = new HBox(10);
			hbAddBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbAddBtn.getChildren().add(addBtn);
			row += 2;
			grid.add(hbAddBtn, 1, row);
			
			// Action when Add Critters Button is pressed.
			final Text actionTarget = new Text();
			row += 2;
			grid.add(actionTarget, 1, row);
			
			//grid.setGridLinesVisible(true);
			
			Scene scene = new Scene(grid, 500, 500);
			primaryStage.setScene(scene);
			primaryStage.show();
			
			// Action when add critters button is pressed. Call makeCritter.
			// Uses something called an anonymous class of type EventHandler<ActionEvent>, which is a class that is
			// defined inline, in the curly braces.
			addBtn.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					String name = critNameField.getText();
					String numString = critNumField.getText();
					//TODO: make a more graceful error message if someone inputs an invalid critter type
					if ((name == null)) {
						actionTarget.setFill(Color.FIREBRICK);
						actionTarget.setText("Please enter a critter class.");	
						return;
					}
					if (numString == null) {
						try {
							Critter.makeCritter("project5.".concat(name));
						} catch (InvalidCritterException e) {
							actionTarget.setFill(Color.FIREBRICK);
							actionTarget.setText("Please enter a valid critter class.");	
						}
					}
					else {
						try{ 
							for (int i = 0; i < Integer.parseInt(numString); i += 1) {
								try {
									Critter.makeCritter("project5.".concat(name));
								} catch (InvalidCritterException e) {
									actionTarget.setFill(Color.FIREBRICK);
									actionTarget.setText("Please enter a valid critter class.");	
								}
							}
						} catch (NumberFormatException e) {
							actionTarget.setFill(Color.FIREBRICK);
							actionTarget.setText("Invalid number of critters. Please type an integer number of critters to add.");	
							return;
						}
					}
					//actionTarget.setFill(Color.FIREBRICK);
					//actionTarget.setText("TODO: message to display how many Critters added etc.");	
					Critter.displayWorld();	
				}			
			});
			

		} catch(Exception e) {
			e.printStackTrace();		
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
