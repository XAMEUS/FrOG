package ui.drawing;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import ui.controller.Controller;

/**
 * Info system
 * @author Gourgoulhon
 */
public class GInfo extends GridPane {
	
	private Text text;
	
	public GInfo() {
		this.setAlignment(Pos.TOP_LEFT);
		this.setHgap(10);
		this.setVgap(8);
		this.setPadding(new Insets(4, 6, 4, 6));
		this.text = new Text("Generation: " + Controller.grid.generation);
		this.add(text, 0, 0);
	}
	
	public void setText(String text) {
		this.text.setText(text);
	}
}
