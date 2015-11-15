package test;

import ui.controller.Controller;

/**
 * Testing, no graphics
 * @author Gourgoulhon
 */
public class Test {
	
	public static void main(String[] args) {
		
		Controller.grid.update();
		long time = System.currentTimeMillis();
		Controller.grid.update();
		System.out.println(System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
		Controller.grid.update();
		System.out.println(System.currentTimeMillis() - time);
		time = System.currentTimeMillis();
		Controller.grid.update();
		System.out.println(System.currentTimeMillis() - time);
		
		
	}
	
}
