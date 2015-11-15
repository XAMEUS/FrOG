import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.controller.Controller;
import ui.drawing.GDrawing;
import ui.drawing.GInfo;
import ui.menu.GMenu;

/**
 * Main program
 * compile : javac Main.java
 * run : java Main
 * @author Gourgoulhon
 */
public class FrOG extends Application {
	
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = new BorderPane();
			MenuBar menuBar = new GMenu();
			GInfo bot = new GInfo();
			Controller.GOFDrawing = new GDrawing();
			Controller.infos = bot;
			root.setTop(menuBar);
			root.setCenter(Controller.GOFDrawing);
			root.setBottom(bot);
			Scene scene = new Scene(root, 800, 600);
			scene.getStylesheets().add(getClass().getResource("style/application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.getIcons().add(new Image("pic/frog.png"));
			primaryStage.setTitle("FrOG");
			primaryStage.show();
			
			AnimationTimer timer = new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	if (!Controller.isOnPause() || Controller.getTimer() != 0) {
	            		Controller.time();
	            		if (Controller.getTimer() > Controller.delay) {
	            			Controller.resetTimer();
            				Controller.grid.update();
            				Controller.drawInfos();
	            		}
	            		Controller.GOFDrawing.draw();
	            	}
	            }
	        };
	        timer.start();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		
		// Fix Windows 10 ComboBox
		System.setProperty("glass.accessible.force", "false");
		launch(args);

	}
}
