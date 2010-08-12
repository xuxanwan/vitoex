package org.vito.dummy;

/**
 * Simulation class for test need.
 * 
 * Test code need not to be as efficient as production code, since it runs on testing
 * environment not production environment. The two environments have very different
 * needs - Dual Standard.
 * 
 * From Clean Code listing 9-6.
 * @author fire
 *
 */
public class MockControlHardware {
	boolean heater = false;
	boolean blower = false;
	boolean cooler = false;
	boolean hiTempAlarm = false;  //high temperature
	boolean loTempAlarm = false;  //low temp
	
	/**
	 * This's not very efficent. StringBuffer is more efficent,but it's ugly,hence
	 * the cost here is small.
	 * On the other hand, the test environment may not be constrained by production
	 * environment limits.
	 * @return
	 */
	public String getState(){	
		String state = "";
		state += heater ? "H" : "h";
		state += blower ? "H" : "h";
		state += cooler ? "H" : "h";
		state += hiTempAlarm ? "H" : "h";
		state += loTempAlarm ? "H" : "h";
		return state;
	}

}
