package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.LifeState;
import core.states.State;

/**
 * Fredkin rules
 * @author Gourgoulhon
 */
public class Fredkin implements Automaton<Cell<State, Direction>, State> {

	@Override
	public State getNextState(Cell<State, Direction> cell) {
		int c = 0;
		for (Direction dir : Direction.values()) {
			Cell<State, Direction> n = cell.getNeighbor(dir);
			if (n != null && n.getState() == LifeState.ALIVE)
				c++;
		}
		return (c%2==1)? LifeState.ALIVE:LifeState.DEAD;
	}

}
