package org.jfree.date.junit;

import junit.framework.*;
import org.jfree.date.*;
import static org.jfree.date.SerialDate.*;

import java.util.*;

/**
 * Intercepted from Clean Code.
 * Author's junit test class for SerialDate, with greater test coverage.
 * 
 * @author 
 *
 */
public class BobsSerialDateTest extends TestCase{
	public void testIsValidWeekdayCode() throws Exception {
		for(int day = 1; day <= 7; day++){
			assertTrue(isValidWeekdayCode(day));			
		}
		assertFalse(isValidWeekdayCode(0));
		assertFalse(isValidWeekdayCode(8));
	}
	
	public void testStringToWeekdayCode() throws Exception {
		assertEquals(-1, stringToWeekdayCode("Hello"));
		if(Locale.getDefault().toString().equals("zh_CN")){
			
		}else if(Locale.getDefault().toString().equals("en_US")){
			assertEquals(MONDAY, stringToWeekdayCode("Monday"));
		}
		
		
	}
	
	//...
}
