
import java.awt.Color;
import java.util.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

import javafx.stage.Stage;

// Author: Zack Sprague
// Date: 3-26-18
// This program creates swirls in a certain number of orders and also creates certain number of branches

public class RecursiveSwirls extends Application{
	public void start(Stage primaryStage) {
		SwirlPane pane = new SwirlPane(); 
		TextField tfOrder = new TextField();
		TextField tfBranch = new TextField();
		tfOrder.setPrefColumnCount(4);
		tfOrder.setAlignment(Pos.BOTTOM_LEFT);
		tfBranch.setOnAction(e -> pane.setDepth((Integer.parseInt(tfOrder.getText())), (Integer.parseInt(tfBranch.getText()))));
		tfBranch.setPrefColumnCount(4);

		// Pane to hold label and text field
		HBox hBox = new HBox(10);
		hBox.getChildren().addAll(new Label("Enter an order: "), tfOrder, new Label("Enter branches: "), tfBranch);
		hBox.setAlignment(Pos.CENTER);

		BorderPane borderPane = new BorderPane();
		borderPane.setCenter(pane);
		borderPane.setBottom(hBox);

		// Create a scene and place it in the stage
		Scene scene = new Scene(borderPane, 600, 600);
		primaryStage.setTitle("Recursive Swirls"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage

		scene.widthProperty().addListener(ov -> pane.paint());
		scene.heightProperty().addListener(ov -> pane.paint());

	}
	/** Pane for displaying swirls */
	static class SwirlPane extends Pane {
		private int depth = 0;
		private int branch = 0;

		public void setDepth(int depth, int branch) {
			this.depth = depth;
			this.branch = branch;
			paint();
		}

		public void paint() {
			getChildren().clear();	      
			paintSwirl(depth, getWidth() / 2, getHeight() / 2,
					getHeight() / 4, Math.PI / 12);
		}

		public void paintSwirl(int depth, double x1, double y1, double length, double angle) {

			Random rand = new Random();
			int r = rand.nextInt(256);
			int g = rand.nextInt(256);
			int b = rand.nextInt(256);
			Color color = new Color(r,g,b);

			for(int i = 0; i <= branch; i++) {    			
				// Calculate end points
				double x2 = x1 + Math.cos(angle) * length;
				double y2 = y1 - Math.sin(angle) * length;
				Line line = new Line(x1, y1, x2, y2);

				getChildren().add(line);

				// Recursive call
				if (depth > 0) {    	        		
					paintSwirl(depth - 1, x2, y2, length * 0.4, angle + Math.PI / 3);    	        		
				}    			    			
				// Calculate angle for next branch
				angle += 2 * Math.PI / branch;
			}
		}
	}
	public static void main(String[] args) {
		launch(args);
	}

}
