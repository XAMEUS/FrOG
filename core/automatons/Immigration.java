package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.ImmigrationState;
import core.states.State;

/**
 * Immigration rules
 * see : http://www.conwaylife.com/wiki/Immigration#Immigration
 * @author Gourgoulhon
 */
public class Immigration implements Automaton<Cell<State, Direction>, State>{

	@Override
	public State getNextState(Cell<State, Direction> cell) {
		int c1 = 0;
		int c2 = 0;
		
		for (Direction dir : Direction.values()) {
			Cell<State, Direction> n = cell.getNeighbor(dir);
			if (n != null) {
				if (n.getState() == ImmigrationState.ALIVE1)
					c1++;
				else if (n.getState() == ImmigrationState.ALIVE2)
					c2++;
			}
		}
		
		int c = c1 + c2;
		
		if (c == 3 && cell.getState() == ImmigrationState.DEAD)
			return (c1 > c2)? ImmigrationState.ALIVE1:ImmigrationState.ALIVE2;
		else
			return (c == 3 || c == 2)? cell.getState():ImmigrationState.DEAD;
	}

}
