package core.automatons;

/**
 * Conway rules
 * @author Gourgoulhon
 */
public class Conway extends Isotrop {

	public Conway() {
		this.surviveRules = new int[] { 2, 3 };
		this.bornRules = new int[] { 3 };
	}

}
