/**
 * 
 */
package com.seven10.nfs_mounter.parameters.test;

import org.junit.Test;

import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;

/**
 * @author kmm
 *
 */
public class NfsMountParamsValidatorTest
{
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test
	public void testValidateMountPoint_valid()
	{
		String mountPoint = "/mnt/hydra";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_invalid()
	{
		String mountPoint = "asdfasdf";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_empty()
	{
		String mountPoint = "";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_null()
	{
		String mountPoint = null;
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateLocation(java.lang.String)}.
	 */
	@Test
	public void testValidateLocation_valid()
	{
		String location = "valid.domain-name.com";
		NfsMountParamsValidator.validateLocation(location);
		location = "192.168.5.5";
		NfsMountParamsValidator.validateLocation(location);
		location = "localhost";
		NfsMountParamsValidator.validateLocation(location);
	}
	/**
	* Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateLocation(java.lang.String)}.
	*/
	@Test(expected=IllegalArgumentException.class)
	public void testValidateLocation_invalid_domain()
	{
		String location = "asdfa sdf";
		NfsMountParamsValidator.validateLocation(location);
	}
	 
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateShareName(java.lang.String)}.
	 */
	@Test
	public void testValidateShareName_valid()
	{
		String shareName = "/myShare";
		NfsMountParamsValidator.validateShareName(shareName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateShareName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareName_invalid()
	{
		String shareName = "inv@lidShareName#?";
		NfsMountParamsValidator.validateShareName(shareName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateShareName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareName_empty()
	{
		String shareName = "";
		NfsMountParamsValidator.validateShareName(shareName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateShareName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareName_null()
	{
		String shareName = null;
		NfsMountParamsValidator.validateShareName(shareName);
	}
}
