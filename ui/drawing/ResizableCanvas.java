package ui.drawing;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

/**
 * Resizable Canvas
 * @author Gourgoulhon
 */
public abstract class ResizableCanvas extends Pane {

	protected final Canvas canvas = new Canvas();

	public ResizableCanvas() {
		getChildren().add(canvas);
	}

	@Override
	protected void layoutChildren() {
		final int top = (int) snappedTopInset();
		final int right = (int) snappedRightInset();
		final int bottom = (int) snappedBottomInset();
		final int left = (int) snappedLeftInset();
		final int width = (int) getWidth() - left - right;
		final int height = (int) getHeight() - top - bottom;
		canvas.setLayoutX(left);
		canvas.setLayoutY(top);
		if (width != canvas.getWidth() || height != canvas.getHeight()) {
			canvas.setWidth(width);
			canvas.setHeight(height);
			this.draw();
		}
	}

	public abstract void draw();

	@Override
	public boolean isResizable() {
		return true;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

}
