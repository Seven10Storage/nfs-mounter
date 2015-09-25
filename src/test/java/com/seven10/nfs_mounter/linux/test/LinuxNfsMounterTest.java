/**
 * 
 */
package com.seven10.nfs_mounter.linux.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mockito;

import com.seven10.nfs_mounter.linux.AutoFsMgr;
import com.seven10.nfs_mounter.linux.LinuxNfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class LinuxNfsMounterTest
{
	private AutoFsMgr afsMgr = Mockito.mock(AutoFsMgr.class);
	private String mountPoint1 = "/mnt1";
	private String mountPoint2 = "/mnt2";
	private String mountPoint3 = "/mnt3";
	private String mountPoint4 = "/mnt4";
	
	private List<NfsMountVolumesParameter> createValidParametersList()
	{
		String location = "valid.location.com";
		String shareName = "/validShare";
		List<NfsMountVolumesParameter> rval = new ArrayList<NfsMountVolumesParameter>();

		rval.add(new NfsMountVolumesParameter(mountPoint1, location, shareName));
		rval.add(new NfsMountVolumesParameter(mountPoint2, location, shareName));
		rval.add(new NfsMountVolumesParameter(mountPoint3, location, shareName));
		rval.add(new NfsMountVolumesParameter(mountPoint4, location, shareName));
		return rval;
	}
	
	private List<String> createValidParametersStrings()
	{
		List<String> rval = new ArrayList<String>();
		rval.add(mountPoint1);
		rval.add(mountPoint2);
		rval.add(mountPoint3);
		rval.add(mountPoint4);
		return rval;
	}
	
	private LinuxNfsMounter createValidMounter()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = new NfsMounterFactorySettings();
		LinuxNfsMounter mounter = new LinuxNfsMounter(nfsMounterFactorySettings, afsMgr);
		return mounter;
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test
	public void testLinuxNfsMounter_valid()
	{
		LinuxNfsMounter mounter = createValidMounter();
		assertNotNull(mounter);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLinuxNfsMounter_nullSettings()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = null;
		new LinuxNfsMounter(nfsMounterFactorySettings, afsMgr);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLinuxNfsMounter_nullMgr()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = new NfsMounterFactorySettings();
		AutoFsMgr nullAfsMgr = null;
		new LinuxNfsMounter(nfsMounterFactorySettings, nullAfsMgr);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#mountVolumes(List)}.
	 * @throws IOException 
	 */
	@Test
	public void testMountVolumes_valid() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountVolumesParameter> parameterObjects = createValidParametersList();
		List<String> parameterStrings = createValidParametersStrings();
		
		List<File> actual = mounter.mountVolumes(parameterObjects);
		assertNotNull(actual);
		Mockito.verify(afsMgr, Mockito.times(1)).setMountPointsList(parameterStrings);
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	@Test
	public void testMountVolumes_emptyList() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountVolumesParameter> parameterObjects = new ArrayList<NfsMountVolumesParameter>(0);
		List<String> parameterStrings = new ArrayList<String>(0);
		
		mounter.mountVolumes(parameterObjects);

		Mockito.verify(afsMgr, Mockito.times(1)).setMountPointsList(parameterStrings);
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	@Test(expected=IllegalArgumentException.class)
	public void testMountVolumes_nullList() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountVolumesParameter> parameterObjects = null;
		
		mounter.mountVolumes(parameterObjects);
	}
	
	/**
	 * Test method for {@link com.seven10.datamover.object.nfs.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testUnMountVolumes_valid() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = createValidParametersStrings();
		
		mounter.unMountVolumes(parameterStrings);

		Mockito.verify(afsMgr, Mockito.times(1)).setMountPointsList(parameterStrings);
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	/**
	 * Test method for {@link com.seven10.datamover.object.nfs.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testUnMountVolumes_emptyParameter() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = new ArrayList<String>(0);
		
		mounter.unMountVolumes(parameterStrings); // should quietly return

		Mockito.verify(afsMgr, Mockito.times(0)).setMountPointsList(parameterStrings); // should not call this
		Mockito.verify(afsMgr, Mockito.times(0)).updateFile(); // should not call this
	}
	/**
	 * Test method for {@link com.seven10.datamover.object.nfs.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testUnMountVolumes_nullParameter() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = null;
		
		mounter.unMountVolumes(parameterStrings);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#isMounted(java.lang.String)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testIsMounted_valid() throws FileNotFoundException, IOException
	{
		String includedMountPoint = "/includedMount";
		String notIncludedMountPoint = "/notIncluded";		
		List<String> listWithMountPoint = new ArrayList<String>();		
		listWithMountPoint.add(includedMountPoint);
		Mockito.when(afsMgr.getMountPointList()).thenReturn(listWithMountPoint);
		
		LinuxNfsMounter mounter = createValidMounter();
		String mountPoint = includedMountPoint;
		
		boolean actual = mounter.isMounted(mountPoint);
		assertTrue(actual);
		actual = mounter.isMounted(notIncludedMountPoint);
		assertFalse(actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#isMounted(java.lang.String)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsMounted_emptyMountPoint() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		String mountPoint = "";
		
		mounter.isMounted(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.LinuxNfsMounter#isMounted(java.lang.String)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIsMounted_nullMountPoint() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		String mountPoint = null;
		
		mounter.isMounted(mountPoint);
	}
	
	
}
