package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.LifeState;
import core.states.State;

/**
 * Isotropic rules
 * @author Gourgoulhon
 */
public abstract class Isotrop implements Automaton<Cell<State, Direction>, State> {
	
	protected int[] surviveRules;
	protected int[] bornRules;
	
	@Override
	public State getNextState(Cell<State, Direction> cell) {
		int c = 0;
		
		for (Direction dir : Direction.values()) {
			Cell<State, Direction> n = cell.getNeighbor(dir);
			if (n != null && n.getState() == LifeState.ALIVE)
				c++;
		}
		
		if (cell.getState() == LifeState.ALIVE)
			for (int n : surviveRules) {
				if (c == n)
					return LifeState.ALIVE;
			}
		else
			for (int n : bornRules) {
				if (c == n)
					return LifeState.ALIVE;
			}

		return LifeState.DEAD;
	}

}