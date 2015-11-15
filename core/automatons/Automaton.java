package core.automatons;

import core.cell.Cell;
import core.states.State;

/**
 * Automaton interface
 * @author Gourgoulhon
 * @param <C> Cell
 * @param <S> State
 */
public interface Automaton<C extends Cell<S, ?>, S extends State> {
	
	/**
	 * Calculate the next state of the given cell.
	 * @param cell Cell
	 * @return next state of the Cell
	 */
	public S getNextState(C cell);
	
}
