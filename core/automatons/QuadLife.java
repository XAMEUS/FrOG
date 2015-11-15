package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.QuadLifeState;
import core.states.State;

/**
 * Qualife Automaton, see : http://www.conwaylife.com/wiki/QuadLife#Quadlife
 * @author Gourgoulhon Maxime
 */
public class QuadLife implements Automaton<Cell<State, Direction>, State>{

	@Override
	public State getNextState(Cell<State, Direction> cell) {
		int[] cc = new int[4];
		
		for (Direction dir : Direction.values()) {
			Cell<State, Direction> n = cell.getNeighbor(dir);
			if (n != null) {
				if (n.getState() == QuadLifeState.ALIVE1)
					cc[0]++;
				else if (n.getState() == QuadLifeState.ALIVE2)
					cc[1]++;
				else if (n.getState() == QuadLifeState.ALIVE3)
					cc[2]++;
				else if (n.getState() == QuadLifeState.ALIVE4)
					cc[3]++;
			}
		}
		int c = cc[0] + cc[1] + cc[2] + cc[3];
		
		if (c == 3 && cell.getState() == QuadLifeState.DEAD) {
			if (cc[0] == 0 && cc[1] != 0 && cc[2] != 0 && cc[3] != 0) return QuadLifeState.ALIVE1;
			if (cc[1] == 0 && cc[0] != 0 && cc[2] != 0 && cc[3] != 0) return QuadLifeState.ALIVE2;
			if (cc[2] == 0 && cc[1] != 0 && cc[0] != 0 && cc[3] != 0) return QuadLifeState.ALIVE3;
			if (cc[3] == 0 && cc[1] != 0 && cc[2] != 0 && cc[0] != 0) return QuadLifeState.ALIVE4;
			int m = 0;
			for (int i = 0; i < cc.length - 1; i++)
				if (cc[i] < cc[i+1])
					m = i+1;
			return QuadLifeState.values()[m + 1]; // Each Enum type has a static
													// values method that
													// returns an array
													// containing all of the
													// values of the enum type
													// in the order they are
													// declared.
		}
		else
			return (c == 3 || c == 2)? cell.getState():QuadLifeState.DEAD;
	}
	
}
