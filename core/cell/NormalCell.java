package core.cell;

import core.automatons.RulesWrapper;
import core.directions.Direction;
import core.states.State;

/**
 * Normal Cell implementation
 * @author Gourgoulhon
 * @param <S> State
 * @param <N> Direction
 */
public class NormalCell<S extends State, N extends Enum<N>> implements Cell<S, N> {

	protected Neighbors<N, S> neighbors;
	protected S state;
	protected S nextState;
	protected RulesWrapper rules;
	
	/**
	 * Build a cell with a given state and neighbors
	 * @param state current state of the cell
	 * @param neighbors size of the neighborhood
	 */
	public NormalCell(S state, int neighbors) {
		this.state = state;
		this.nextState = null;
		this.neighbors = new Neighbors<>(neighbors);
	}
	
	@Override
	public S getState() {
		return this.state;
	}

	@Override @SuppressWarnings("unchecked")
	public void setState(State state) {
		this.state = (S) state;
	}

	@Override
	public Cell<S, N> getNeighbor(N direction) {
		return this.neighbors.getNeighbor(direction);
	}
	
	@Override
	public int getHowManyNeighbors() {
		return this.neighbors.howMany();
	};
	
	@Override
	public void setNeighbor(N direction, Cell<S, N> cell) {
		this.neighbors.setNeighbor(direction, cell);
	}
	
	@Override @SuppressWarnings("unchecked")
	public S nextState() {
		if (this.nextState != null)
			return this.nextState;
		this.nextState = (S) this.rules.rules.getAutomaton().getNextState((Cell<State, Direction>) this);
		return this.nextState;
	}

	@Override
	public void iter() {
		if (this.nextState == null)
			this.nextState();
		this.state = this.nextState;
		this.nextState = null;
	}
	
	@Override
	public void cleanNextState() {
		this.nextState = null;
	}
	
	@Override
	public void setRules(RulesWrapper rules) {
		this.rules = rules;
	}
}
