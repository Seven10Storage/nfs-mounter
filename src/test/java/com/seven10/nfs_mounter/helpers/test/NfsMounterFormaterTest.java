/**
 * 
 */
package com.seven10.nfs_mounter.helpers.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 *
 */
public class NfsMounterFormaterTest
{
	
	private String validMountPoint = "mountpoint";
	private String validLocation = "valid.location.com";
	private String validExportName = "/validExport";
	private String validParameterAsUnc = "\\\\valid.location.com\\validExport";

	/**
	 * Creates a valid parameter object
	 * @return the parameter object
	 */
	private NfsMountExportParameter createValidParameter()
	{
		String mountPoint = validMountPoint;
		String location = validLocation;
		String shareName = validExportName;
		NfsMountExportParameter parameter = new NfsMountExportParameter(mountPoint, location, shareName);
		return parameter;
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.NfsMountExportParameter.object.nfs.NfsMountExportParameter, com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test
	public void testFormatParamterForLine_valid()
	{
		NfsMountExportParameter parameter = createValidParameter();
		NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
		String expected = String.format("%s %s %s:%s", validMountPoint, nfsFactorySettings.getLinuxOptionsString(),	validLocation, validExportName);	
		
		String actual = NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
		
		assertEquals(expected, actual);	
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.NfsMountExportParameter.object.nfs.NfsMountVolumesParameter, com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatParamterForLine_nullParameter()
	{
		NfsMountExportParameter parameter = null;
		NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
		
		NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.NfsMounterFormater#formatParamterForLine(com.seven10.nfs_mounter.parameters.NfsMountExportParameter.object.nfs.NfsMountVolumesParameter, com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatParamterForLine_nullFactortySettings()
	{
		NfsMountExportParameter parameter = createValidParameter();
		NfsMounterFactorySettings nfsFactorySettings = null;
		
		NfsMounterFormater.formatParamterForLine(parameter, nfsFactorySettings);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.NfsMounterFormater#formatMountAsUnc(com.seven10.nfs_mounter.parameters.NfsMountExportParameter.object.nfs.NfsMountVolumesParameter)}.
	 */
	@Test
	public void testFormatMountAsUnc_valid()
	{
		NfsMountExportParameter parameter = createValidParameter();
		String expected = validParameterAsUnc;
		String actual = NfsMounterFormater.formatMountAsUnc(parameter);
		assertEquals(expected, actual);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.helpers.NfsMounterFormater#formatMountAsUnc(com.seven10.nfs_mounter.parameters.NfsMountExportParameter.object.nfs.NfsMountVolumesParameter)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testFormatMountAsUnc_nullParameter()
	{
		NfsMountExportParameter parameter = null;
		NfsMounterFormater.formatMountAsUnc(parameter);
	}
	
	
}
