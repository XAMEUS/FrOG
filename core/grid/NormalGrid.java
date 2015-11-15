package core.grid;

import core.automatons.RulesWrapper;
import core.cell.Cell;
import core.cell.NormalCell;
import core.states.State;

/**
 * Abstract normal grid
 * @author Gourgoulhon
 *
 * @param <S> State
 * @param <N> Direction
 * @param <C> Cell
 */
public abstract class NormalGrid<S extends State, N extends Enum<N>, C extends Cell<?, N>> implements Grid<S, N, C> {

	protected Cell<?, ?>[][] grid;
	public final int ROWS;
	public final int COLUMNS;
	public int generation;
	public final State defaultState;
	protected final double ratio;

	/**
	 * Build a grid.
	 * @param rows size
	 * @param columns size
	 * @param state, type of state
	 * @param ratio ratio of living cells
	 */
	public NormalGrid(int rows, int columns, State state, double ratio) {
		this.ROWS = rows;
		this.COLUMNS = columns;
		this.grid = new NormalCell[rows][columns];
		this.defaultState = state;
		this.ratio = ratio;
		this.init();
	}
	
	/**
	 * Fill the grid with cells
	 */
	protected abstract void fillCells();
	/**
	 * Get the neighbord of a cell at the given coordinates
	 * @param i rows
	 * @param j columns
	 * @param direction neighbor direction
	 * @return neighoring cell
	 */
	protected abstract C getNeighbor(int i, int j, N direction);
	/**
	 * Create neigbneighbors list of the cell at the given position
	 * @param i rows
	 * @param j columns
	 * @param cell at the given position
	 */
	protected abstract void createNeighbors(int i, int j, NormalCell<S, N> cell);
	/**
	 * Clear the grid
	 */
	public abstract void clear();
	/**
	 * Clear and reset the generation counter
	 */
	public abstract void reset();
	
	/**
	 * initialize the grid
	 */
	protected void init() {
		this.fillCells();
		this.createNeighbors();
	}
	
	/**
	 * Get the cell at the give position
	 * @param i row
	 * @param j column
	 * @return Cell
	 */
	@SuppressWarnings("unchecked")
	public C getCell(int i, int j) {
		return (C) this.grid[i][j];
	}
	
	/**
	 * Create all neighborhoods
	 */
	@SuppressWarnings("unchecked")
	protected void createNeighbors() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				this.createNeighbors(i, j, (NormalCell<S, N>) this.grid[i][j]);
			}
		}
	}
	
	/**
	 * Clean all next states.
	 */
	public void cleanNextStates() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				this.grid[i][j].cleanNextState();
	}
	
	/**
	 * Simulate all next states.
	 */
	public void nextStates() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				this.grid[i][j].nextState();
	}
	
	/**
	 * Iter all
	 */
	public void nextGeneration() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				this.grid[i][j].iter();
		this.generation++;
	}
	
	@Override
	public int getRows() {
		return this.ROWS;
	}
	
	@Override
	public int getColumns() {
		return this.COLUMNS;
	}
	
	@Override
	public void update() {
		this.nextStates();
		this.nextGeneration();
	}

	@Override
	public String stateAsString() {
		String r = "";
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < COLUMNS; j++) {
				r += this.grid[i][j].getState().toChar() + " ";
			}
			r += System.lineSeparator();
		}
		return r;
	}
	
	@Override
	public int getGeneration() {
		return this.generation;
	}

	@Override
	public void setRules(RulesWrapper rules) {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++)
				this.grid[i][j].setRules(rules);
	}

}
