package core.states;

import javafx.scene.paint.Color;

/**
 * State interface
 * @author Gourgoulhon
 */
public interface State {
	
	/**
	 * default color
	 */
	final Color color = Color.WHITE;

	/**
	 * @return char, state
	 */
	default char toChar() { return '.'; }

	/**
	 * @return Color of the state
	 */
	default Color toColor() { return color; }
	
	/**
	 * @return the next state (in the list)
	 */
	public State next();
	
}
