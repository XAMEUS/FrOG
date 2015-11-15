package ui.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.imageio.ImageIO;

import core.automatons.Rules;
import core.automatons.RulesWrapper;
import core.cell.NormalCell;
import core.directions.Direction;
import core.grid.DefaultGrid;
import core.grid.Grid;
import core.grid.HexGrid;
import core.grid.NormalGrid;
import core.states.ImmigrationState;
import core.states.LifeState;
import core.states.QuadLifeState;
import core.states.State;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
import ui.drawing.GDrawing;
import ui.drawing.GInfo;
import ui.utils.Dialogs;

/**
 * Controller System
 * @author Gourgoulhon
 */
public class Controller {
	
	public static Application application;
	public static Stage primaryStage;
	public static RulesWrapper rules = new RulesWrapper(Rules.QUADLIFE);
	public static  NormalGrid<?, ?, ?> grid = new DefaultGrid(50, 80, QuadLifeState.DEAD, 0.5);
	//public static NormalGrid<?, ?, ?> grid = new HexGrid(4, 4, Rules.CONWAY);
	//public static NormalGrid<?, ?, ?> grid = new TriGrid(60, 60);
	public static GDrawing GOFDrawing;
	public static Menu rulesMenu;
	public static GInfo infos;
	private static boolean pause = true;
	public static long delay = 500;
	private static long lastTime = System.currentTimeMillis();
	private static long timer = 0;
	
	public static void pause() {
		pause = true;
	}
	
	public static void unpause() {
		pause = false;
	}
	
	public static boolean isOnPause() {
		return pause;
	}
	
	public static void refresh() {
		GOFDrawing.draw();
	}
	
	public static void drawInfos() {
		infos.setText("Generation: " + Controller.grid.getGeneration());
	}
	
	public static long getTimer() {
		return timer;
	}
	
	public static void resetTimer() {
		timer = 0;
		lastTime = System.currentTimeMillis();
	}
	
	public static void time() {
		timer += System.currentTimeMillis() - ui.controller.Controller.lastTime;
		lastTime = System.currentTimeMillis();
	}
	
	@SuppressWarnings("unchecked")
	public static void open(String filepath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(filepath)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
    		Dialogs.showErrorMessage(primaryStage, "Error: file not found", e.getMessage());
		}
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();
	        String[] size = br.readLine().split(" ");
	        int rows = Integer.valueOf(size[0]);
	        int columns = Integer.valueOf(size[1]);
	        String rules = br.readLine();
	        Controller.setRules(Rules.getRules(rules));
	        State state;
	        if (Controller.rules.rules == Rules.IMMIGRATION)
	        	state = ImmigrationState.DEAD;
	        else if (Controller.rules.rules == Rules.QUADLIFE)
	        	state = QuadLifeState.DEAD;
	        else
	        	state = LifeState.DEAD;
	        if (line.contains(HexGrid.class.getName()))
	        	Controller.grid = new HexGrid(rows, columns, state, 0);
	        else Controller.grid = new DefaultGrid(rows, columns, state, 0);
    		int i = 0;
    		
    		line = br.readLine();
	        while (line != null) {
	            sb.append(line);
	            sb.append(System.lineSeparator());
	            String[] l = line.split(" ");
	            if (state instanceof ImmigrationState)
	            	for (int j = 0; j < l.length; j++)
	            		((NormalCell<State, Direction>)(Controller.grid.getCell(i, j))).setState(ImmigrationState.getState(l[j].charAt(0)));
	            else if (state instanceof QuadLifeState)
	            	for (int j = 0; j < l.length; j++)
	            		((NormalCell<State, Direction>)(Controller.grid.getCell(i, j))).setState(QuadLifeState.getState(l[j].charAt(0)));
	            else
	            	for (int j = 0; j < l.length; j++)
	            		((NormalCell<State, Direction>)(Controller.grid.getCell(i, j))).setState(LifeState.getState(l[j].charAt(0)));
	            i++;
	            line = br.readLine();
	        }
	        System.out.println(sb.toString());
	        grid.nextStates();
	        Controller.refresh();
	    } catch (IOException e) {
			e.printStackTrace();
    		Dialogs.showErrorMessage(primaryStage, "Error: IOException", e.getMessage());
		} finally {
	        try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
	    		Dialogs.showErrorMessage(primaryStage, "Error: cannot close this file...", e.getMessage());
			}
	    }
	}
	
	public static void save(Grid<?, ?, ?> grid, String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.println(grid.getClass().getName());
			writer.println(grid.getRows() + " " + grid.getColumns());
			writer.println(Controller.rules.rules.getName());
			writer.print(grid.stateAsString());
			writer.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
    		Dialogs.showErrorMessage(primaryStage, "File creation error", e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static void setRules(Rules r) {
		Controller.rules.rules = r;
		for (MenuItem e : Controller.rulesMenu.getItems())
			if (e.getText().equals(r.getName()))
				((RadioMenuItem)e).setSelected(true);
	}
	
	public static void toImage(File file) {
		WritableImage wi = new WritableImage((int)Controller.GOFDrawing.getWidth(), (int)Controller.GOFDrawing.getHeight());
		Controller.GOFDrawing.getCanvas().snapshot(null, wi);
		BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);
		try {
			ImageIO.write(bi, "png", file);
		} catch (IOException e) {
			Dialogs.showErrorMessage(primaryStage, "Error: IOException", e.getMessage());
			e.printStackTrace();
		}
	}
	
}
