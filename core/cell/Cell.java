package core.cell;

import core.automatons.RulesWrapper;
import core.states.State;

public interface Cell<S extends State, N extends Enum<N>> {
	/**
	 * @return current State of the cell.
	 */
	S getState();

	/**
	 * Change the state of the cell.
	 * @param state : new state of the cell.
	 */
	void setState(S state);

	/**
	 * @param direction : direction of the desired neighbor.
	 * @return ref to the neighbor.
	 */
	Cell<S, N> getNeighbor(N direction);
	
	/**
	 * @return int
	 */
	int getHowManyNeighbors();
	
	/**
	 * Define a neighbour in a given direction
	 * @param direction the direction
	 * @param cell the neighbour in that direction
	 */
	void setNeighbor(N direction, Cell<S, N> cell);
	
	/**
	 * Simulate the next state
	 * @return the next state
	 */
	S nextState();
	
	/**
	 * make a transition
	 */
	void iter();
	
	/**
	 * remove the simulate state.
	 */
	void cleanNextState();
	
	/**
	 * Set the rules
	 * @param rules of the cell
	 */
	void setRules(RulesWrapper rules);
	
}
