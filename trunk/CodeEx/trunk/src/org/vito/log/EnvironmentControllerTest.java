package org.vito.log;


/**
 * Illustrating Dual Standard: test/production environment have different
 * needs.
 * 
 * Upper case means "ON", lower case means "OFF" in the specific string,
 * and the letter sequence is always following this order:
 * {heater,blower,cooler,hiTempAlarm,loTempAlarm}
 * 
 * From Clean Code listing 9-5.
 * @author fire
 *
 */
public class EnvironmentControllerTest {
	
	MockControlHardware hw = new MockControlHardware();
	
	public void turnOnCoolerAndBlowerIfTooHot() throws Exception{
		tooHot();
		assertEquals("hBChl",hw.getState());
	}

	public void turnOnHeaterAndBlowerIfTooCold() throws Exception{
		tooCold();
		assertEquals("HBchl",hw.getState());
	}
	
	public void turnOnHiTempAlarmAtThreshold() throws Exception{
		wayTooHot();
		assertEquals("hBCHl",hw.getState());
	}
	
	public void turnOnLoTempAlarmAtThreshold() throws Exception{
		wayTooCold();
		assertEquals("HBchL",hw.getState());
	}
	
	public void assertEquals(String string, String state) {}
	public void tooHot(){}
	public void tooCold(){}
	public void wayTooHot(){}
	public void wayTooCold(){}
	
}
