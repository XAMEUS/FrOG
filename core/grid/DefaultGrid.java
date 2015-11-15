package core.grid;

import core.cell.NormalCell;
import core.directions.Direction;
import core.states.ImmigrationState;
import core.states.LifeState;
import core.states.QuadLifeState;
import core.states.State;
import ui.controller.Controller;

public class DefaultGrid extends NormalGrid<State, Direction, NormalCell<State, Direction>> {

	public DefaultGrid(int rows, int columns, State s) {
		super(rows, columns, s);
	}

	@Override
	protected void fillCells() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++) {
				if (this.defaultState instanceof LifeState)
					this.grid[i][j] = new NormalCell<LifeState, Direction>((Math.random()<0.5)? LifeState.ALIVE:LifeState.DEAD, Direction.values().length);
				else if (this.defaultState instanceof ImmigrationState)
					this.grid[i][j] = new NormalCell<ImmigrationState, Direction>((Math.random()<0.5)?
							((Math.random()<0.5)? ImmigrationState.ALIVE1:ImmigrationState.ALIVE2):ImmigrationState.DEAD, Direction.values().length);
				else if (this.defaultState instanceof QuadLifeState)
					this.grid[i][j] = new NormalCell<QuadLifeState, Direction>((Math.random()<0.5)?
							QuadLifeState.values()[(int)(Math.random() * 4 + 1)]:QuadLifeState.DEAD, Direction.values().length);
				this.grid[i][j].setRules(Controller.rules);
			}
		
	}

	@Override @SuppressWarnings("unchecked")
	protected NormalCell<State, Direction> getNeighbor(int i, int j, Direction direction) {
		switch (direction) {
		case NORTH:
			return (NormalCell<State, Direction>) 
					((i == 0)? this.grid[ROWS-1][j] : this.grid[i-1][j]);
		case NORTH_EAST:
			return (NormalCell<State, Direction>)
					((i == 0)? ((j == COLUMNS - 1)? this.grid[ROWS - 1][0]:this.grid[ROWS - 1][j+1]):
								((j == COLUMNS - 1)? this.grid[i-1][0]:this.grid[i-1][j+1]));
		case EAST:
			return (NormalCell<State, Direction>)
					((j == COLUMNS - 1)? this.grid[i][0]:this.grid[i][j+1]);
		case SOUTH_EAST:
			return (NormalCell<State, Direction>)
					((i == ROWS - 1)? ((j == COLUMNS - 1)? this.grid[0][0]:this.grid[0][j+1]):
										((j == COLUMNS - 1)? this.grid[i+1][0]:this.grid[i+1][j+1]));
		case SOUTH:
			return (NormalCell<State, Direction>)
					((i == ROWS - 1)? this.grid[0][j]:this.grid[i+1][j]);
		case SOUTH_WEST:
			return (NormalCell<State, Direction>)
					((i == ROWS - 1)? ((j == 0)? this.grid[0][COLUMNS-1]:this.grid[0][j-1]):
										((j == 0)? this.grid[i+1][COLUMNS-1]:this.grid[i+1][j-1]));
		case WEST:
			return (NormalCell<State, Direction>)
					((j == 0)? this.grid[i][COLUMNS - 1]:this.grid[i][j-1]);
		case NORTH_WEST:
			return  (NormalCell<State, Direction>)
					((i == 0)? ((j == 0)? this.grid[ROWS-1][COLUMNS-1]:this.grid[ROWS-1][j-1]):
								((j == 0)? this.grid[i-1][COLUMNS-1]:this.grid[i-1][j-1]));
		default:
			return null;
		}
	}

	@Override
	protected void createNeighbors(int i, int j, NormalCell<State, Direction> cell) {
		for (Direction d : Direction.values())
			cell.setNeighbor(d, (NormalCell<State, Direction>) this.getNeighbor(i, j, d));
	}

	@Override @SuppressWarnings("unchecked")
	public void clear() {
		for (int i = 0; i < ROWS; i++)
			for (int j = 0; j < COLUMNS; j++) {
				NormalCell<State, Direction> c = (NormalCell<State, Direction>) this.grid[i][j];
				c.setState((State) ((Enum<?>)c.getState()).getDeclaringClass().getEnumConstants()[0]);
			}
	}
	
	@Override
	public void reset() {
		this.clear();
		this.generation = 0;
	}

}
