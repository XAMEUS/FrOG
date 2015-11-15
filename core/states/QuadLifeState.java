package core.states;

import javafx.scene.paint.Color;
import ui.drawing.CColors;

/**
 * QuadLifeState enum, 5 states.
 * @author Gourgoulhon
 */
public enum QuadLifeState implements State {
	
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
			return 'A';
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
			return 'B';
		}
		@Override
		public Color toColor() {
			return CColors.c2;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	},
	ALIVE3 {
		@Override
		public char toChar() {
			return 'C';
		}
		@Override
		public Color toColor() {
			return CColors.c3;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	},
	ALIVE4 {
		@Override
		public char toChar() {
			return 'D';
		}
		@Override
		public Color toColor() {
			return CColors.c4;
		}
		@Override
		public State next() {
			return states[(this.ordinal()+1) % states.length];
		}
	};

	
	private static QuadLifeState[] states = values();
	
	/**
	 * Return a State corresponding to the given char.
	 * @param c name
	 * @return State
	 */
	public static QuadLifeState getState(char c) {
		for (QuadLifeState r : QuadLifeState.values())
			if (r.toChar() == c)
				return r;
		return null;
	}
	
}
