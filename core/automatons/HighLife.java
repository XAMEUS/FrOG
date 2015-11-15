package core.automatons;

/**
 * HighLife rules
 * see : https://fr.wikipedia.org/wiki/HighLife_%28automate_cellulaire%29
 * @author Gourgoulhon
 */
public class HighLife extends Isotrop {
	
	public HighLife() {
		this.surviveRules = new int[]{2, 3};
		this.bornRules = new int[]{3, 6};
	}
	
}
