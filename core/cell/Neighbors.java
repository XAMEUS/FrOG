package core.cell;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import core.states.State;

/**
 * Neighors wrapping class
 * @author Gourgoulhon
 * @param <N> : Direction
 * @param <S> : State
 */
public class Neighbors<N extends Enum<N>, S extends State> {

	private Map<N, Cell<?, N>> neighbors;
	private final int size;
	
	public Neighbors(int size) {
		this.neighbors = new HashMap<>();
		this.size = size;
	}
	
	/**
	 * Return a list of neighboring cells
	 * @return Collection of neighboring cells
	 */
	public Collection<Cell<?, N>> getList() {
		return this.neighbors.values();
	}
	
	/**
	 * Set a neighboring cell in a given direction
	 * @param direction, direction of the neighboring cell
	 * @param cell, neighboring cell
	 */
	public void setNeighbor(N direction, Cell<?, N> cell) {
		if (this.neighbors.size() < this.size)
			this.neighbors.put(direction, cell);
	}
	
	/**
	 * @return neighbors.size()
	 */
	public int howMany() {
		return this.neighbors.size();
	}
	
	/**
	 * Get the neighboring cell in a given direction
	 * @param direction, direction of the neighboring cell
	 * @return Cell, the neighboring cell
	 */
	@SuppressWarnings("unchecked")
	public Cell<S, N> getNeighbor(N direction) {
		return (Cell<S, N>) this.neighbors.get(direction);
	}

}
