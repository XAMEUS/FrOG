package core.states;

import javafx.scene.paint.Color;
import ui.drawing.CColors;

/**
 * LifeState enum, 2 states
 * @author Gourgoulhon
 */
public enum LifeState implements State {
	DEAD {
		@Override
		public char toChar() {
			return '.';
		}
		@Override
		public Color toColor() {
			return Color.WHITE;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	},
	ALIVE {
		@Override
		public char toChar() {
			return 'O';
		}
		@Override
		public Color toColor() {
			return CColors.c1.darker();
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	};
	
	private static LifeState[] states = values();

	/**
	 * Return a State corresponding to the given char.
	 * @param c name
	 * @return State
	 */
	public static LifeState getState(char c) {
		for (LifeState r : LifeState.values())
			if (r.toChar() == c)
				return r;
		return null;
	}
}
	
