package core.grid;

import core.cell.NormalCell;
import core.directions.Direction;
import core.states.State;

public class TriGrid extends DefaultGrid {

	public TriGrid(int rows, int columns, State state, double ratio) {
		super(rows, columns, state, ratio);
	}
	
	@Override @SuppressWarnings("unchecked")
	protected NormalCell<State, Direction> getNeighbor(int i, int j, Direction direction) {
		switch (direction) {
		case NORTH:
			i = ((i+j) % 2 == 0)? ((i==0)? COLUMNS-1:i-1):((i==COLUMNS-1)? 0:i+1);
			return (NormalCell<State, Direction>) this.grid[i][j];
		case EAST:
			return (NormalCell<State, Direction>) ((j==ROWS-1)? this.grid[i][0]:this.grid[i][j+1]);
		case WEST:
			return (NormalCell<State, Direction>) ((j==0)? this.grid[i][ROWS-1]:this.grid[i][j-1]);
		default:
			return null;
		}
	}

}
