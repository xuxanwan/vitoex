package org.vito;

/**
 * From Clean Code listing 10-3, 
 * illustrating A single-responsibility class.
 * 
 * And it has a high potential for reuse in other applications.
 * @author vito
 *
 */
public class Version { // 做成template?
	public int getMajorVersionNumber(){return -1;}
	public int getMinorVersionNumber(){return -1;}
	public int getBuildNumber(){return -1;}
}
