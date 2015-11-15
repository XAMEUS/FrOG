package ui.menu;

import java.util.Optional;

import core.automatons.Rules;
import core.grid.DefaultGrid;
import core.grid.HexGrid;
import core.states.ImmigrationState;
import core.states.LifeState;
import core.states.QuadLifeState;
import core.states.State;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import ui.controller.Controller;
import ui.utils.NumberField;

/**
 * New file popup
 * @author Gourgoulhon
 */
public class GNewFile {

	public GNewFile() {

		Dialog<String[]> dialog = new Dialog<>();
		dialog.setTitle("Create a new Grid");
		dialog.initOwner(Controller.primaryStage);
		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		stage.getIcons().add(new Image("pic/frog.png"));

		ButtonType createType = new ButtonType("Create", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(createType, ButtonType.CANCEL);

		GridPane grid = new GridPane();
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(12));
		grid.setAlignment(Pos.CENTER);

		Node create = dialog.getDialogPane().lookupButton(createType);
		create.setDisable(true);

		ObservableList<String> options = FXCollections.observableArrayList();
		for (Rules a : Rules.values())
			options.add(a.getName());
		ComboBox<String> comboBox = new ComboBox<>(options);
		comboBox.setPromptText("Rules");
		grid.add(comboBox, 1, 0);

		ToggleGroup type = new ToggleGroup();

		RadioButton squares = new RadioButton("Squares");
		squares.setToggleGroup(type);
		squares.setSelected(true);

		RadioButton hexagons = new RadioButton("Hexagons");
		hexagons.setToggleGroup(type);
		
		grid.add(squares, 0, 0);
		grid.add(hexagons, 0, 1);

		NumberField rows = new NumberField();
		rows.setPromptText("rows");
		NumberField columns = new NumberField();
		columns.setPromptText("columns");

		grid.add(rows, 0, 2);
		grid.add(columns, 1, 2);
		
		Slider ratio = new Slider(0, 1, 0.5);
		Text ratioText = new Text("Life amount : 50%");
		grid.add(ratioText, 0, 3);
		grid.add(ratio, 1, 3);
		
		ratio.valueProperty().addListener((observable, oldValue, newValue) -> {
			ratioText.setText("Life amount : " + (((int)(newValue.doubleValue()*1000))/1000.0) + "%");
		});
		
		ChangeListener<String> check = new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				create.setDisable(comboBox.getValue() == null || rows.getText().trim().isEmpty() || columns.getText().trim().isEmpty());
			}
		};
		
//		comboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
//			create.setDisable(newValue.trim().isEmpty() || rows.getText().trim().isEmpty() || columns.getText().trim().isEmpty());
//		});
		comboBox.valueProperty().addListener(check);
		rows.textProperty().addListener(check);
		columns.textProperty().addListener(check);
		
		dialog.getDialogPane().setContent(grid);

		dialog.setResultConverter(dialogButton -> {
			if (dialogButton == createType) {
				return new String[] {
						rows.getText(),
						columns.getText(),
						comboBox.getValue(),
						((RadioButton)(type.getSelectedToggle())).getText(),
						""+ratio.getValue()
						};
			}
			return null;
		});

		Optional<String[]> result = dialog.showAndWait();
		result.ifPresent(d -> {
			Controller.setRules(Rules.getRules(d[2]));
			State state;
	        if (Controller.rules.rules == Rules.IMMIGRATION)
	        	state = ImmigrationState.DEAD;
	        else if (Controller.rules.rules == Rules.QUADLIFE)
	        	state = QuadLifeState.DEAD;
	        else
	        	state = LifeState.DEAD;
			if (d[3].equals("Squares"))
				Controller.grid = new DefaultGrid(Integer.valueOf(d[0]), Integer.valueOf(d[1]), state, Double.valueOf(d[4]));
			else Controller.grid = new HexGrid(Integer.valueOf(d[0]), Integer.valueOf(d[1]), state, Double.valueOf(d[4]));
			Controller.refresh();
		});
		
	}

}
