package com.seven10.nfs_mounter.parameters.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;

public class NfsMountVolumesParameterTest
{
	
	private String validMountPoint = "/mnt";
	private String validLocation = "valid.location.com";
	private String validLinuxShareName = "/validShare/withMoreThanOnePath";
	private String validWindowsShareName = "\\valid\\Windows\\share";
	private String invalidMountPoint = "#$@";
	private String invalidLocation = "fasd3$!";
	private String invalidShareName = "dsldas#$";

	@Test
	public void testNfsMountVolumesParameter_valid()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = validLinuxShareName;
		String shareNameWin = validWindowsShareName;
		
		NfsMountVolumesParameter mvp1 = new NfsMountVolumesParameter(mountPoint, location, shareName);
		NfsMountVolumesParameter mvp2 = new NfsMountVolumesParameter(mountPoint, location, shareNameWin);
		assertNotNull(mvp1);	
		assertNotNull(mvp2);	
		
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_invalidMountPoint()
	{
		String mountPoint = invalidMountPoint;
		String location = validLocation;
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_emptyMountPoint()
	{
		String mountPoint = "";
		String location = validLocation;
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_nullMountPoint()
	{
		String mountPoint = null;
		String location = validLocation;
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_invalidLocation()
	{
		String mountPoint = validMountPoint;
		String location = invalidLocation;
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_emptyLocation()
	{
		String mountPoint = validMountPoint;
		String location = "";
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_nullLocation()
	{
		String mountPoint = validMountPoint;
		String location = null;
		String shareName = validLinuxShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_invalidShareName()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = invalidShareName;
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_emptyShareName()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = "";
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testNfsMountVolumesParameter_nullShareName()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = "";
		new NfsMountVolumesParameter(mountPoint, location, shareName);
	}
	
	@Test
	public void testGetMountPoint()
	{
		String expected = validMountPoint;
		String location = validLocation;
		String shareName = validLinuxShareName;
		NfsMountVolumesParameter mvp = new NfsMountVolumesParameter(expected, location, shareName);
		
		String actual = mvp.getMountPoint();
		
		assertEquals(expected, actual);
	}
	
	@Test
	public void testGetLocation()
	{
		String mountPoint = validMountPoint;
		String expected = validLocation;
		String shareName = validLinuxShareName;
		NfsMountVolumesParameter mvp = new NfsMountVolumesParameter(mountPoint, expected, shareName);
		
		String actual = mvp.getLocation();
		
		assertEquals(mountPoint, actual);
	}
	
	@Test
	public void testGetLinuxShareName()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String expectedLin = validLinuxShareName;
		String expectedWin = validWindowsShareName;
		
		NfsMountVolumesParameter mvp1 = new NfsMountVolumesParameter(mountPoint, location, expectedLin);
		NfsMountVolumesParameter mvp2 = new NfsMountVolumesParameter(mountPoint, location, expectedWin);
		
		String actualLin = mvp1.getLinuxShareName();
		String actualWin = mvp2.getLinuxShareName();
		
		assertEquals(expectedLin, actualLin);
		assertEquals(expectedWin.replace("\\", "/"), actualWin);
	}
	
	@Test
	public void testGetWindowsShareName()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String expectedLin = validLinuxShareName;
		String expectedWin = validWindowsShareName;
		
		NfsMountVolumesParameter mvp1 = new NfsMountVolumesParameter(mountPoint, location, expectedLin);
		NfsMountVolumesParameter mvp2 = new NfsMountVolumesParameter(mountPoint, location, expectedWin);
		
		String actualLin = mvp1.getWindowsShareName();
		String actualWin = mvp2.getLinuxShareName();
		
		assertEquals(expectedLin.replace("\\", "/"), actualLin);
		assertEquals(expectedWin, actualWin);
	}
	
}
