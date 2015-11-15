package ui.utils;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Dialogs, to show errors
 * @author Gourgoulhon
 */
public class Dialogs {
	
	public static void showErrorMessage(Stage primaryStage, String title, String msg) {
		final Stage dialog = new Stage();
		dialog.initStyle(StageStyle.UTILITY);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        BorderPane bp = new BorderPane();
        StackPane sp = new StackPane();
        sp.setPrefSize(0, 50);
        sp.setStyle("-fx-background-color: #ee3311");
        Text t = new Text(title);
        t.setStrokeWidth(4);
        sp.getChildren().add(t);
        bp.setTop(sp);
        TextArea ta = new TextArea();
        ta.setEditable(false);
        ta.setText(msg);
        bp.setCenter(ta);
        Button b = new Button("close");
        //bp.setBottom(b);
        BorderPane.setAlignment(b, Pos.BOTTOM_RIGHT);
        Scene dialogScene = new Scene(bp, 400, 120);
        dialog.setTitle("Error");
        dialog.setScene(dialogScene);
        dialog.setResizable(false);
        dialog.show();
	}
	
}
