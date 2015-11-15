package core.automatons;

/**
 * DayAndNight rules
 * see : https://fr.wikipedia.org/wiki/Day_%26_Night
 * @author Gourgoulhon
 */
public class DayAndNight extends Isotrop {

	public DayAndNight() {
		this.surviveRules = new int[] { 3, 4, 6, 7, 8 };
		this.bornRules = new int[] { 3, 6, 7, 8 };
	}

}
