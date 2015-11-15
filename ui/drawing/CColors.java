package ui.drawing;
import javafx.scene.paint.Color;

/**
 * Custom colors
 * @author Gourgoulhon
 */
public class CColors {
	
	public static double r = Math.random() * 360;
	public static Color c1 = Color.hsb(CColors.r, 0.6, 0.97);
	public static Color c2 = Color.hsb(CColors.r + 90, 0.6, 0.97);
	public static Color c3 = Color.hsb(CColors.r + 180, 0.6, 0.97);
	public static Color c4 = Color.hsb(CColors.r + 270, 0.6, 0.97);
	
}
