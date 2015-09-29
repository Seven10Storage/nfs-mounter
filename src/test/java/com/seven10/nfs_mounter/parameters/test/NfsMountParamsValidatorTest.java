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
		String mountPoint = "hydra";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.datamover.object.nfs.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_invalid()
	{
		String mountPoint = "/asdf:\"a?sdf";
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
	
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name> 
	 */
	@Test
	public void testValidateAutoFsEntry_valid()
	{
		// <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name>
		String testString = "mnt -ro,hard,intr,rsize=3,wsize=4 test.location:/share/with/folders";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
		
		testString = "mnt -rw,soft,rsize=344444,wsize=4 test.location:/share";
		NfsMountParamsValidator.validateAutoFsEntry(testString);		
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_invalidString()
	{
		String testString = "/mnt -rw,soft,rsize=344444,wsize=4test.location:/share";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_emptyString()
	{
		String testString = "";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_nullString()
	{
		String testString = null;
		NfsMountParamsValidator.validateAutoFsEntry(testString);		
	}
	
	/**
	 * This validates that a shareResource is in the form
	 * <location>:<share-name>
	 */
	@Test
	public void testValidateShareResource_valid()
	{
		String testString = "valid.address:/valid/path";
		NfsMountParamsValidator.validateShareResource(testString);
	}
	/**
	 * This validates that a shareResource is in the form
	 * <location>:<share-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareResource_invalid()
	{
		String testString = "valid.address$/valid/path";
		NfsMountParamsValidator.validateShareResource(testString);
	}
	/**
	 * This validates that a shareResource is in the form
	 * <location>:<share-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareResource_emptyParam()
	{
		String testString = "";
		NfsMountParamsValidator.validateShareResource(testString);
	}
	/**
	 * This validates that a shareResource is in the form
	 * <location>:<share-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateShareResource_nullParam()
	{
		String testString = null;
		NfsMountParamsValidator.validateShareResource(testString);
	}
	
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test
	public void validateShareOptions_valid()
	{
		String testString = "-ro,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,soft,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,hard,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,soft,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,soft,rsize=99999,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
		testString = "-rw,soft,rsize=1,wsize=85814";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_perm()
	{
		String testString = "-pl,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_missing_perm()
	{
		String testString = "-hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_resetType()
	{
		String testString = "-ro,poo,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_missing_resetType()
	{
		String testString = "-ro,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_interruptable()
	{
		String testString = "-ro,hard,gurgle,rsize=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_rsize_name()
	{
		String testString = "-ro,hard,intr,rsizzlee=1,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_rsize_value()
	{
		String testString = "-ro,hard,intr,rsize=aasd,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_empty_rsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_missing_rsize()
	{
		String testString = "-ro,hard,intr,wsize=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_wsize_name()
	{
		String testString = "-ro,hard,intr,rsize=,wsizzle=2";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_invalid_wsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=aaaa";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_empty_wsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateShareOptions_missing_wsize()
	{
		String testString = "-ro,hard,intr,rsize=";
		NfsMountParamsValidator.validateShareOptions(testString);
	}
}
