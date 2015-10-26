/**
 * 
 */
package com.seven10.nfs_mounter.linux.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.mockito.Mockito;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.NfsMounterFactory;
import com.seven10.nfs_mounter.linux.AutoFsMgr;
import com.seven10.nfs_mounter.linux.LinuxNfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class LinuxNfsMounterTest
{
	private AutoFsMgr afsMgr = Mockito.mock(AutoFsMgr.class);
	private String mountPoint1 = "mnt1";
	private String mountPoint2 = "mnt2";
	private String mountPoint3 = "mnt3";
	private String mountPoint4 = "mnt4";
	
	private List<NfsMountExportParameter> createValidParametersList()
	{
		String location = "valid.location.com";
		String shareName = "/validShare";
		List<NfsMountExportParameter> rval = new ArrayList<NfsMountExportParameter>();

		rval.add(new NfsMountExportParameter(mountPoint1, location, shareName));
		rval.add(new NfsMountExportParameter(mountPoint2, location, shareName));
		rval.add(new NfsMountExportParameter(mountPoint3, location, shareName));
		rval.add(new NfsMountExportParameter(mountPoint4, location, shareName));
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
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test
	public void testLinuxNfsMounter_valid()
	{
		LinuxNfsMounter mounter = createValidMounter();
		assertNotNull(mounter);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLinuxNfsMounter_nullSettings()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = null;
		new LinuxNfsMounter(nfsMounterFactorySettings, afsMgr);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#LinuxNfsMounter(com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testLinuxNfsMounter_nullMgr()
	{
		NfsMounterFactorySettings nfsMounterFactorySettings = new NfsMounterFactorySettings();
		AutoFsMgr nullAfsMgr = null;
		new LinuxNfsMounter(nfsMounterFactorySettings, nullAfsMgr);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#mountExports(List)}.
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMountVolumes_valid() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountExportParameter> parameterObjects = createValidParametersList();
		
		List<File> actual = mounter.mountExports(parameterObjects);
		
		assertNotNull(actual);
		
		Mockito.verify(afsMgr, Mockito.times(1)).setAutoFsEntryList(Mockito.anySet());
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	@Test
	public void testMountVolumes_emptyList() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountExportParameter> parameterObjects = new ArrayList<NfsMountExportParameter>(0);
		Set<String> parameterStrings = new HashSet<String>(0);
		
		mounter.mountExports(parameterObjects);

		Mockito.verify(afsMgr, Mockito.times(1)).setAutoFsEntryList(parameterStrings);
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	@Test(expected=IllegalArgumentException.class)
	public void testMountVolumes_nullList() throws IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<NfsMountExportParameter> parameterObjects = null;
		
		mounter.mountExports(parameterObjects);
	}
	
	/**
	 * Test method for {@link com.seven10.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUnMountVolumes_valid() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = createValidParametersStrings();
		
		mounter.unMountExports(parameterStrings);

		Mockito.verify(afsMgr, Mockito.times(1)).setAutoFsEntryList(Mockito.anySet());
		Mockito.verify(afsMgr, Mockito.times(1)).updateFile();
	}
	/**
	 * Test method for {@link com.seven10.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testUnMountVolumes_emptyParameter() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = new ArrayList<String>(0);
		
		mounter.unMountExports(parameterStrings); // should quietly return

		Mockito.verify(afsMgr, Mockito.times(0)).setAutoFsEntryList(Mockito.anySet()); // should not call this
		Mockito.verify(afsMgr, Mockito.times(0)).updateFile(); // should not call this
	}
	/**
	 * Test method for {@link com.seven10.LinuxNfsMounter#unMountVolumes(List<String>)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testUnMountVolumes_nullParameter() throws FileNotFoundException, IOException
	{
		LinuxNfsMounter mounter = createValidMounter();
		List<String> parameterStrings = null;
		
		mounter.unMountExports(parameterStrings);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#isMounted(java.lang.String)}.
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@Test
	public void testIsMounted_valid() throws FileNotFoundException, IOException
	{
		String includedMountPoint = "includedMount";
		String notIncludedMountPoint = "notIncluded";		
		Set<String> listWithMountPoint = new HashSet<String>();		
		listWithMountPoint.add(includedMountPoint);
		Mockito.when(afsMgr.getAutoFsEntryList()).thenReturn(listWithMountPoint);
		
		LinuxNfsMounter mounter = createValidMounter();
		String mountPoint = includedMountPoint;
		
		boolean actual = mounter.isMounted(mountPoint);
		assertTrue(actual);
		actual = mounter.isMounted(notIncludedMountPoint);
		assertFalse(actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#isMounted(java.lang.String)}.
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
	 * Test method for {@link com.seven10.nfs_mounter.linux.LinuxNfsMounter#isMounted(java.lang.String)}.
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
	
	@Test
	public void passMultiMountTest() throws Exception
	{
		// Empty out file
		FileWriter writer = new FileWriter("/usr/local/etc/auto.hydra", false);
		writer.flush();
		writer.close();
		
		//String deviceName = "Src";
		String resourceName = "Resource_Source";
		String exportPath = "/home/mintserver1/Src";
		String address = "192.168.21.111";
		
		String strMountBase = "/mnt/hydra";
		String strMountOptions = "-rw,hard,intr,rsize=8192,wsize=8192";
		
		// Source
		StringBuilder sb = new StringBuilder(resourceName);
		sb.append(" ");
		sb.append(strMountOptions);
		sb.append(" ");
		sb.append(address);
		sb.append(":");
		sb.append(exportPath);
		String strSourceMount = sb.toString();
		
		// Dest
		resourceName = "Resource_Destination";
		exportPath = "/home/mintserver1/Dest";		
		sb = new StringBuilder(resourceName);
		sb.append(" ");
		sb.append(strMountOptions);
		sb.append(" ");
		sb.append(address);
		sb.append(":");
		sb.append(exportPath);
		String strDestMount = sb.toString();
		
		// Create writer
		writer = new FileWriter("/usr/local/etc/auto.hydra", false);
		BufferedWriter bufferedWriter = new BufferedWriter(writer);
		File mountFile;
		
		// Write line
		//bufferedWriter.write("\n");
		bufferedWriter.write(strSourceMount);
		bufferedWriter.write("\n");
		bufferedWriter.write(strDestMount);
		
		// Flush and close all writer buffers
		bufferedWriter.flush();
		bufferedWriter.close();
		
		mountFile = new File(strMountBase + File.separator + "Resource_Source");
		assertTrue(mountFile.exists());		
		for (String strFile : mountFile.list())
		{
			System.out.println("\tSource Found: " + strFile);
		}
		
		mountFile = new File(strMountBase + File.separator + "Resource_Destination");
		assertTrue(mountFile.exists());
		for (String strFile : mountFile.list())
		{
			System.out.println("\tDest Found: " + strFile);
		}
		
		// Empty out file
		writer = new FileWriter("/usr/local/etc/auto.hydra", false);
		writer.flush();
		writer.close();
	}
	
	/**
	 * @param deviceName 
	 * @param addresses 
	 * @param deviceObject
	 * @param resourceType
	 * @param strPath
	 * @return the path to the mounted volume
	 * @throws IOException
	 * @throws BadRequestException
	 */
	@Test
	public void failedMultiMountTest() throws Exception
	{
		NfsMounter mounter = NfsMounterFactory.getMounter();
		
		String deviceName = "Src";
		String resourceId = "resource_Source";
		String exportName = "/home/mintserver1/Src";
		String[] addresses = new String[]{"192.168.21.111"};
		
		NfsMountExportParameter exportParams;
		File file;
		
		exportParams = createExportParams(deviceName, resourceId, exportName, addresses);
		file = mounter.mountExport(exportParams);
		assertTrue(file.exists());
		
		for(String strFile : file.list())
		{
			System.out.println(strFile);
		}
		
		deviceName = "Dest";
		resourceId = "resource_Destination";
		exportName = "/home/mintserver1/Dest";
		
		exportParams = createExportParams(deviceName, resourceId, exportName, addresses);
		file = mounter.mountExport(exportParams);
		assertTrue(file.exists());
		
		for(String strFile : file.list())
		{
			System.out.println(strFile);
		}
		//rval = file.getAbsolutePath();
		
		// Empty out file
		FileWriter writer = new FileWriter("/usr/local/etc/auto.hydra", false);
		writer.flush();
		writer.close();
	}
	
	private static NfsMountExportParameter createExportParams(String deviceName, String resourceId, String exportName, String []addresses)
	{
		String mountPoint = createUniqId(deviceName, resourceId);
		String location = getValidAddress(deviceName, addresses);
		NfsMountExportParameter exportParams = new NfsMountExportParameter(mountPoint, location, exportName);
				return exportParams;
	}
	
	private static String sanatizeName(String oldName)
	{
		String rval = oldName.replaceAll("[^\\w\\d]", "_");
		//m_logger.trace(".sanatizeName(): sanatized name=%s", rval);
		return rval;
	}
	/**
	 * @param deviceName TODO
	 * @param resourceId TODO
	 * @param deviceResource
	 * @return
	 */
	private static String createUniqId(String deviceName, String resourceId)
	{
		String name = sanatizeName(deviceName);
		String mountPoint = new StringBuilder(name).append("_").append(resourceId).toString();
		//m_logger.trace(".createUniqId(): new UniqueId=%s", mountPoint);
		return mountPoint;
	}
	/**
	 * @param device
	 * @return
	 */
	private static String getValidAddress(String deviceName, String []addresses)
	{
		if(addresses.length <= 0)
		{
			throw new IllegalArgumentException(String.format(".getValidAddress(): requires a valid address. None found for device '%s'", deviceName));
		}		
		return addresses[0];
	}
}
