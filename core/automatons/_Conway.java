package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.LifeState;
import core.states.State;

@Deprecated
public class _Conway implements Automaton<Cell<State, Direction>, State> {

	@Override
	public State getNextState(Cell<State, Direction> cell) {
		int c = 0;

		for (Direction dir : Direction.values()) {
			if (cell.getNeighbor(dir).getState() == LifeState.ALIVE)
				c++;
		}

		LifeState s;
		if (cell.getState() == LifeState.ALIVE)
			s = (c == 2 || c == 3) ? LifeState.ALIVE : LifeState.DEAD;
		else
			s = (c == 3) ? LifeState.ALIVE : LifeState.DEAD;

		return s;
	}

}
