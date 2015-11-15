package core.grid;

import core.cell.NormalCell;
import core.directions.Direction;
import core.states.State;

public class HexGrid extends DefaultGrid {

	public HexGrid(int rows, int columns, State state) {
		super((rows % 2 != 0) ? ((rows == 1) ? 1 : rows - 1) : rows, columns, state);
	}

	@Override @SuppressWarnings("unchecked")
	protected NormalCell<State, Direction> getNeighbor(int i, int j, Direction direction) {
		switch (direction) {
		case NORTH_WEST:
			j = (i % 2 == 0) ? ((j == 0) ? COLUMNS - 1 : j - 1) : j;
			return (NormalCell<State, Direction>) ((i == 0) ? this.grid[ROWS - 1][j] : this.grid[i - 1][j]);
		case NORTH_EAST:
			j = (i % 2 != 0) ? ((j == COLUMNS - 1) ? 0 : j + 1) : j;
			return (NormalCell<State, Direction>) ((i == 0) ? this.grid[ROWS - 1][j] : this.grid[i - 1][j]);
		case EAST:
			return (NormalCell<State, Direction>) ((j == COLUMNS - 1) ? this.grid[i][0] : this.grid[i][j + 1]);
		case SOUTH_EAST:
			j = (i % 2 != 0) ? ((j == COLUMNS - 1) ? 0 : j + 1) : j;
			return (NormalCell<State, Direction>) ((i == ROWS - 1) ? this.grid[0][j] : this.grid[i + 1][j]);
		case SOUTH_WEST:
			j = (i % 2 == 0) ? ((j == 0) ? COLUMNS - 1 : j - 1) : j;
			return (NormalCell<State, Direction>) ((i == ROWS - 1) ? this.grid[0][j] : this.grid[i + 1][j]);
		case WEST:
			return (NormalCell<State, Direction>) ((j == 0) ? this.grid[i][COLUMNS - 1] : this.grid[i][j - 1]);
		default:
			return null;
		}
	}

}
