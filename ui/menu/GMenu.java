package ui.menu;

import java.awt.Desktop;
import java.io.File;
import java.net.URI;
import java.util.Optional;

import core.automatons.Rules;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.Image;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ui.controller.Controller;
import ui.utils.NumberField;

/**
 * Menu bar
 * @author Gourgoulhon
 */
public class GMenu extends MenuBar {

	public GMenu() {

		this.createMenuFile();
		this.createMenuRun();
		this.createMenuGrid();
		this.createMenuHelp();

	}

	private void createMenuFile() {

		Menu menu = new Menu("File");

		MenuItem newFile = new MenuItem("New   ");
		newFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				new GNewFile();
			}
		});
		newFile.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		
		MenuItem load = new MenuItem("Load");
		load.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showOpenDialog(Controller.primaryStage);
				if (file != null)
					Controller.open(file.getAbsolutePath());
			}
		});
		load.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		MenuItem save = new MenuItem("Save");
		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				File file = fileChooser.showSaveDialog(Controller.primaryStage);
				if (file != null)
					Controller.save(Controller.grid, file.getAbsolutePath());
			}
		});
		save.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		MenuItem export = new MenuItem("Export");
		export.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter ext = new FileChooser.ExtensionFilter("png files", "*.png");
				fileChooser.getExtensionFilters().add(ext);
				File file = fileChooser.showSaveDialog(Controller.primaryStage);
				if (file != null)
					Controller.toImage(file);
			}
		});
		export.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.CONTROL_DOWN));
		MenuItem quit = new MenuItem("Quit");
		quit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.exit(0);
			}
		});
		quit.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.CONTROL_DOWN));

		menu.getItems().add(newFile);
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(load);
		menu.getItems().add(save);
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(export);
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(quit);

		this.getMenus().add(menu);

	}
	

	private void createMenuRun() {

		Menu menu = new Menu("Run");
		
		MenuItem run = new MenuItem("Run");
		run.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (Controller.isOnPause()) {
					Controller.resetTimer();
					Controller.grid.cleanNextStates();
					Controller.unpause();
				}
			}
		});
		run.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
		
		MenuItem pause = new MenuItem("Pause");
		pause.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.pause();
			}
		});
		pause.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
		
		MenuItem timer = new MenuItem("Delay");
		timer.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				
				Dialog<String> dialog = new Dialog<>();
				dialog.setTitle("Delay");
				Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
				stage.getIcons().add(new Image("pic/frog.png"));
				
				ButtonType okButtonType = new ButtonType("Ok", ButtonData.OK_DONE);
				dialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

				GridPane grid = new GridPane();
				grid.setHgap(10);
				grid.setVgap(10);
				grid.setPadding(new Insets(20, 150, 10, 10));

				TextField delay = new NumberField();
				delay.setText("" + Controller.delay);

				grid.add(new Label("Delay (ms) : "), 0, 0);
				grid.add(delay, 1, 0);

				Node okButton = dialog.getDialogPane().lookupButton(okButtonType);
				okButton.setDisable(true);

				delay.textProperty().addListener((observable, oldValue, newValue) -> {
					okButton.setDisable(newValue.trim().isEmpty());
				});

				dialog.getDialogPane().setContent(grid);

				Platform.runLater(() -> delay.requestFocus());
				
				dialog.setResultConverter(dialogButton -> {
				    if (dialogButton == okButtonType) {
				        return delay.getText();
				    }
				    return null;
				});
				
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(d -> {
					int a = Integer.valueOf(d);
					Controller.delay = (a > 0)? a:1;
				});
				
			}
		});
		timer.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCombination.CONTROL_DOWN));
		
		menu.getItems().add(run);
		menu.getItems().add(pause);
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(timer);
		
		this.getMenus().add(menu);
		
	}
	
	private void createMenuGrid() {

		Menu menu = new Menu("Grid");
		
		MenuItem clear = new MenuItem("Clear");
		clear.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Controller.grid.clear();
				Controller.grid.cleanNextStates();
				Controller.pause();
				Controller.refresh();
				Controller.drawInfos();
			}
		});
		clear.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));

		MenuItem clean = new MenuItem("Clean");
		clean.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (Controller.isOnPause()) {
					Controller.grid.reset();
					Controller.grid.cleanNextStates();
					Controller.pause();
					Controller.refresh();
					Controller.drawInfos();	
				}
			}
		});
		clean.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.SHIFT_DOWN));
		
		Menu rules = new Menu("Rules");
		ToggleGroup g = new ToggleGroup();
		for (Rules r : Rules.values()) {
			RadioMenuItem e = new RadioMenuItem(r.getName());
			e.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Controller.rules.rules = Rules.getRules(((RadioMenuItem)(g.getSelectedToggle())).getText());
				}
			});
			e.setToggleGroup(g);
			rules.getItems().add(e);
			e.setDisable(true);
		}
		((RadioMenuItem)rules.getItems().get(5)).setSelected(true);
		
		menu.getItems().add(clear);
		menu.getItems().add(clean);
		menu.getItems().add(new SeparatorMenuItem());
		menu.getItems().add(rules);
		Controller.rulesMenu = rules;
		
		this.getMenus().add(menu);
		
	}
	
	private void createMenuHelp() {

		Menu menu = new Menu("Help");
		
		MenuItem welcome = new MenuItem("Welcome");
		welcome.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(new URI("http://xameus.github.io/FrOG/welcome.html"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}
		});
		MenuItem about = new MenuItem("Quickstart");
		about.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			    if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE)) {
			        try {
			            desktop.browse(new URI("http://xameus.github.io/FrOG/quickstart.html"));
			        } catch (Exception e) {
			            e.printStackTrace();
			        }
			    }
			}
		});
		menu.getItems().add(welcome);
		//menu.getItems().add(new MenuItem("Documentation  "));
		menu.getItems().add(about);
		
		this.getMenus().add(menu);
		
	}

}
