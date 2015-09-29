/**
 * 
 */
package com.seven10.nfs_mounter.helpers.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class NfsMounterFormaterTest
{
	
	private String validMountPoint = "mountpoint";
	private String validLocation = "valid.location.com";
	private String validShareName = "/validshare";
	private String validParameterAsUnc = "\\\\valid.location.com\\validshare";

	/**
	 * Creates a valid parameter object
	 * @return the parameter object
	 */
	private NfsMountVolumesParameter createValidParameter()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = validShareName;
		NfsMountVolumesParameter parameter = new NfsMountVolumesParameter(mountPoint, location, shareName);
		return parameter;
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountVolumesParameter, com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test
	public void testFormatParamterForLine_valid()
	{
		NfsMountVolumesParameter parameter = createValidParameter();
		NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
		String expected = String.format("%s %s %s:%s", validMountPoint, nfsFactorySettings.getLinuxOptionsString(),	validLocation, validShareName);	
		
		String actual = NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
		
		assertEquals(expected, actual);	
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountVolumesParameter, com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatParamterForLine_nullParameter()
	{
		NfsMountVolumesParameter parameter = null;
		NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
		
		NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountVolumesParameter, com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatParamterForLine_nullFactortySettings()
	{
		NfsMountVolumesParameter parameter = createValidParameter();
		NfsMounterFactorySettings nfsFactorySettings = null;
		
		NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.NfsMounterFormater#formatMountAsUnc(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountVolumesParameter)}.
	 */
	@Test
	public void testFormatMountAsUnc_valid()
	{
		NfsMountVolumesParameter parameter = createValidParameter();
		String expected = validParameterAsUnc;
		String actual = NfsMounterFormater.formatMountAsUnc(parameter);
		assertEquals(expected, actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.datamover.object.nfs.NfsMounterFormater#formatMountAsUnc(com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountVolumesParameter)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatMountAsUnc_nullParameter()
	{
		NfsMountVolumesParameter parameter = null;
		NfsMounterFormater.formatMountAsUnc(parameter);
	}
	
	
}
