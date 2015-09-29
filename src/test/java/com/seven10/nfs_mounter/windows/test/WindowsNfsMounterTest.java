/**
 * 
 */
package com.seven10.nfs_mounter.windows.test;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;
import com.seven10.nfs_mounter.windows.WindowsNfsMounter;

/**
 * @author kmm
 *		
 */
public class WindowsNfsMounterTest
{
	private String validMountUnc = "\\\\192.168.21.111\\home\\mintserver1\\Src";
	private String invalidMountUnc = "\\\\somefakeaddress\\notarealmount";
	
	private WindowsNfsMounter createValidMounter()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = new NfsMounterFactorySettings();
		WindowsNfsMounter mounter = new WindowsNfsMounter(nfsMounterFactorySettings);
		return mounter;
	}
	
	private List<NfsMountExportParameter> createValidParametersList()
	{
		List<NfsMountExportParameter> rval = new ArrayList<NfsMountExportParameter>();
		rval.add(new NfsMountExportParameter("mnt", "192.168.21.60", "/ifs/data/Demo"));
		return rval;
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#WindowsNfsMounter(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}
	 * .
	 */
	@Test
	public void testWindowsNfsMounter_valid()
	{
		WindowsNfsMounter mounter = createValidMounter();
		assertNotNull(mounter);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#WindowsNfsMounter(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWindowsNfsMounter_nullParam()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = null;
		new WindowsNfsMounter(nfsMounterFactorySettings);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#mountExports(java.util.List)}
	 * .
	 */
	@Test
	public void testMountVolumes_valid()
	{
		if (SystemUtils.IS_OS_WINDOWS)
		{
			WindowsNfsMounter mounter = createValidMounter();
			List<NfsMountExportParameter> parameterObjects = createValidParametersList();
			
			List<File> actual = mounter.mountExports(parameterObjects);
			
			assertNotNull(actual);
			assertEquals(parameterObjects.size(), actual.size());
		}
		else
		{
			System.out.println(".testMountVolumes() not tested because it is not on a windows machine");
		}
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#mountExports(java.util.List)}
	 * .
	 */
	@Test
	public void testMountVolumes_invalid()
	{
		WindowsNfsMounter mounter = createValidMounter();
		List<NfsMountExportParameter> parameterObjects = createValidParametersList();
		
		List<File> actual = mounter.mountExports(parameterObjects);
		assertNotNull(actual);
		assertEquals(0, actual.size());
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#unMountExports(java.util.List)}
	 * . Testing for a null parameter so the behavior is consistent.
	 */
	@Test
	public void testUnMountVolumes_validParameter()
	{
		WindowsNfsMounter mounter = createValidMounter();
		List<String> mps = new ArrayList<String>();
		mps.add(validMountUnc);
		
		mounter.unMountExports(mps); // there should be no error
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#unMountExports(java.util.List)}
	 * . Testing for a null parameter so the behavior is consistent.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testUnMountVolumes_nullParameter()
	{
		WindowsNfsMounter mounter = createValidMounter();
		List<String> parameterObjects = null;
		
		mounter.unMountExports(parameterObjects);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#isMounted(java.lang.String)}
	 * .
	 */
	@Test
	public void testIsMounted()
	{
		if (SystemUtils.IS_OS_WINDOWS)
		{
			WindowsNfsMounter mounter = createValidMounter();
			boolean actual = mounter.isMounted(validMountUnc);
			assertTrue(actual);
			actual = mounter.isMounted(invalidMountUnc);
		}
		else
		{
			System.out.println(".testMountVolumes() not tested because it is not on a windows machine");
		}
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#isMounted(java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsMounted_emptyMountPoint()
	{
		WindowsNfsMounter mounter = createValidMounter();
		String mountPoint = "";
		mounter.isMounted(mountPoint);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.windows.WindowsNfsMounter#isMounted(java.lang.String)}
	 * .
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testIsMounted_nullMountPoint()
	{
		WindowsNfsMounter mounter = createValidMounter();
		String mountPoint = null;
		mounter.isMounted(mountPoint);
	}
	
}
