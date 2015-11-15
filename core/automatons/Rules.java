package core.automatons;

import core.cell.Cell;
import core.directions.Direction;
import core.states.State;

/**
 * Rules collection
 * @author Gourgoulhon
 */
public enum Rules {


	CONWAY("Conway", new Conway()),
	DAYnNIGHT("Day & Night", new DayAndNight()),
	HIGHLIFE("HighLife", new HighLife()),//new HighLife()),
	FREDKIN("Fredkin", new Fredkin()),
	IMMIGRATION("Immigration", new Immigration()),
    QUADLIFE("QuadLife", new QuadLife());


	private String name;
	private Automaton<Cell<State, Direction>, State> automaton;

	private Rules(String name, Automaton<Cell<State, Direction>, State> automaton) {
		this.name = name;
		this.automaton = automaton;
	}
	
	/**
	 * @return String, name of the rules
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * @return rules Automaton
	 */
	public Automaton<Cell<State, Direction>, State> getAutomaton() {
		return this.automaton;
	}
	
	/**
	 * Get a rule from a given name
	 * @param name the name of the rules
	 * @return the corresponding rules
	 */
	public static Rules getRules(String name) {
		for (Rules r : Rules.values())
			if (r.getName().equals(name))
				return r;
		return null;
	}
	
}
