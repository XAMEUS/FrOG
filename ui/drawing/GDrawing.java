package ui.drawing;

import core.cell.NormalCell;
import core.grid.HexGrid;
import core.grid.TriGrid;
import core.states.State;
import javafx.event.EventHandler;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import ui.controller.Controller;

/**
 * Drawing system
 * @author Gourgoulhon
 */
public class GDrawing extends ResizableCanvas {
	
	private int xCursorPosition = -1;
	private int yCursorPosition = -1;
	
	public GDrawing() {
		
		this.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (Controller.isOnPause()) {
					final int ROWS = Controller.grid.ROWS;
					final int COLUMNS = Controller.grid.COLUMNS;
					final double border = 20;
					final double w = GDrawing.this.canvas.getWidth();
					final double h = GDrawing.this.canvas.getHeight();
					final double size = Math.min((h - 2 * border) / ROWS, (w - 2 * border) / COLUMNS);
					final double xmargin = (w - size * COLUMNS) / 2;
					final double ymargin = (h - size * ROWS) / 2;
					final int i = (int) ((event.getY() - ymargin) / size);
					final int j = (int) ((event.getX() - xmargin - ((Controller.grid instanceof HexGrid)?((i%2==0)? 0:size/2):0)) / size);
					if (i < ROWS && i >= 0 && j >= 0 && j < COLUMNS) {
						NormalCell<?, ?> cell = (NormalCell<?, ?>) Controller.grid.getCell(i, j);
						cell.setState(cell.getState().next());
					}

					yCursorPosition = i;
					xCursorPosition = j;
					
					GDrawing.this.draw();
				}
			}
		});
		
		this.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (Controller.isOnPause()) {
					final int ROWS = Controller.grid.ROWS;
					final int COLUMNS = Controller.grid.COLUMNS;
					final double border = 20;
					final double w = GDrawing.this.canvas.getWidth();
					final double h = GDrawing.this.canvas.getHeight();
					final double size = Math.min((h - 2 * border) / ROWS, (w - 2 * border) / COLUMNS);
					final double xmargin = (w - size * COLUMNS) / 2;
					final double ymargin = (h - size * ROWS) / 2;
					int i = (int) ((event.getY() - ymargin) / size);
					int j = (int) ((event.getX() - xmargin - ((Controller.grid instanceof HexGrid)?((i%2==0)? 0:size/2):0)) / size);
					if (i != yCursorPosition || j != xCursorPosition) {
						if (i < ROWS && i >= 0 && j >= 0 && j < COLUMNS) {
							NormalCell<?, ?> cell = (NormalCell<?, ?>) Controller.grid.getCell(i, j);
							cell.setState(cell.getState().next());
						}
						yCursorPosition = i;
						xCursorPosition = j;
					}
					
					GDrawing.this.draw();
				}
			}
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				xCursorPosition = -1;
				yCursorPosition = -1;
			}
		});
		
	}
	
	public void clear() {
		
		final double width = this.canvas.getWidth();
    	final double height = this.canvas.getHeight();

    	GraphicsContext gc = this.canvas.getGraphicsContext2D();
		gc.clearRect(0, 0, width, height);
		
	}
	
	@Override
	public void draw() {
		
		this.clear();
		
		final int ROWS = Controller.grid.ROWS;
		final int COLUMNS = Controller.grid.COLUMNS;
		double border = 20;
		double w = this.canvas.getWidth();
		double h = this.canvas.getHeight();
		double size = Math.min((h - 2 * border) / ROWS, (w - 2 * border) / COLUMNS);
		double xmargin = (w - size * COLUMNS) / 2;
		double ymargin = (h - size * ROWS) / 2;
		double padding = 0.2 * size - 0.5;
		
    	GraphicsContext gc = this.canvas.getGraphicsContext2D();

		if (Controller.grid instanceof HexGrid) {
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					State current = Controller.grid.getCell(i, j).getState();
					State next = Controller.grid.getCell(i, j).nextState();
					double fade = Math.tanh(3*(2 * (double) Controller.getTimer() / Controller.delay - 1));
					if (next == null)
						gc.setFill(current.toColor());
					else
						gc.setFill(current.toColor().interpolate(next.toColor(), fade));
					
					gc.fillOval(j * size + padding + xmargin + ((i%2==0)? 0:size/2), i * size + padding + ymargin, size - 2 * padding, size - 2 * padding);

				}
			}
		}
		else if (Controller.grid instanceof TriGrid) {
			size *=2;
			xmargin += size/4;
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					State current = Controller.grid.getCell(i, j).getState();
					State next = Controller.grid.getCell(i, j).nextState();
					double fade = Math.tanh(3*(2 * (double) Controller.getTimer() / Controller.delay - 1));
					if (next == null)
						gc.setFill(current.toColor());
					else
						gc.setFill(current.toColor().interpolate(next.toColor(), fade));
					
					double x = j/2 * size + xmargin;
					double y = i/2 * size + ymargin;
					double[] xPoints;
					double[] yPoints;
					if ((i + j) % 2 == 0) {
						xPoints = new double[]{x, x+size, x+size/2};
						yPoints = new double[]{y, y, y+size};
					}
					else {
						xPoints = new double[]{x-size/2, x+size/2, x};
						yPoints = new double[]{y+size, y+size, y};
					}
					gc.fillPolygon(xPoints, yPoints, 3);
					
				}
			}
		}
		else {
			for (int i = 0; i < ROWS; i++) {
				for (int j = 0; j < COLUMNS; j++) {
					State current = Controller.grid.getCell(i, j).getState();
					State next = Controller.grid.getCell(i, j).nextState();
					//double fade = (Math.sin((-Math.PI/2) + (Controller.time * Math.PI/ Controller.delay))+1)/2;
					double fade = Math.tanh(3*(2 * (double) Controller.getTimer() / Controller.delay - 1));
					if (next == null)
						gc.setFill(current.toColor());
					else
						gc.setFill(current.toColor().interpolate(next.toColor(), fade));
					
					gc.fillRect(j * size + padding + xmargin, i * size + padding + ymargin, size - 2 * padding, size - 2 * padding);
					
					// gc.setLineWidth(2);
					// gc.setStroke(new Color(0, 0, 0, 0.1));
					// gc.strokeRect(j*size+xmargin, i*size+ymargin, size, size);
				}
			}
		}
		
	}

}
