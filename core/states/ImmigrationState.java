package core.states;

import javafx.scene.paint.Color;
import ui.drawing.CColors;

/**
 * Immigration, 3 states
 * @author Gourgoulhon
 */
public enum ImmigrationState implements State {
	
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
	ALIVE1 {
		@Override
		public char toChar() {
			return 'O';
		}
		@Override
		public Color toColor() {
			return CColors.c1;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	},
	ALIVE2 {
		@Override
		public char toChar() {
			return 'X';
		}
		@Override
		public Color toColor() {
			return CColors.c3;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	};
	
	private static ImmigrationState[] states = values();
	
	/**
	 * Return a State corresponding to the given char.
	 * @param c name
	 * @return State
	 */
	public static ImmigrationState getState(char c) {
		for (ImmigrationState r : ImmigrationState.values())
			if (r.toChar() == c)
				return r;
		return null;
	}
	
}
