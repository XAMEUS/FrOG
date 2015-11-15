package core.grid;

import core.automatons.RulesWrapper;
import core.cell.Cell;
import core.states.State;

public interface Grid<S extends State, N extends Enum<N>, C extends Cell<?, N>> {
	/**
	 * Perform a transition 
	 */
	void update();
	/**
	 * @return String, representing the grid
	 */
	String stateAsString();
	
	/**
	 * @return int, the current generation age.
	 */
	int getGeneration();
	
	/**
	 * Set the rules of the game.
	 * @param rules of the game
	 */
	void setRules(RulesWrapper rules);
	
	/**
	 * @return final grid.rows
	 */
	int getRows();
	
	/**
	 * @return final grid.columns
	 */
	int getColumns();
	
}
