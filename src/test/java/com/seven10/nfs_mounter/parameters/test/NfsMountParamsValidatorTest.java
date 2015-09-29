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
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test
	public void testValidateMountPoint_valid()
	{
		String mountPoint = "hydra";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_invalid()
	{
		String mountPoint = "/asdf:\"a?sdf";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_empty()
	{
		String mountPoint = "";
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateMountPoint(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateMountPoint_null()
	{
		String mountPoint = null;
		NfsMountParamsValidator.validateMountPoint(mountPoint);
	}
	
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateLocation(java.lang.String)}.
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
	* Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateLocation(java.lang.String)}.
	*/
	@Test(expected=IllegalArgumentException.class)
	public void testValidateLocation_invalid_domain()
	{
		String location = "asdfa sdf";
		NfsMountParamsValidator.validateLocation(location);
	}
	 
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateExportName(java.lang.String)}.
	 */
	@Test
	public void testValidateExportName_valid()
	{
		String exportName = "/myExport";
		NfsMountParamsValidator.validateExportName(exportName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateExportName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportName_invalid()
	{
		String exportName = "inv@lidExportName#?";
		NfsMountParamsValidator.validateExportName(exportName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateExportName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportName_empty()
	{
		String exportName = "";
		NfsMountParamsValidator.validateExportName(exportName);
	}
	/**
	 * Test method for {@link com.seven10.nfs_mounter.parameters.NfsMountParamsValidator#validateExportName(java.lang.String)}.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportName_null()
	{
		String exportName = null;
		NfsMountParamsValidator.validateExportName(exportName);
	}
	
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<export-name> 
	 */
	@Test
	public void testValidateAutoFsEntry_valid()
	{
		// <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<export-name>
		String testString = "mnt -ro,hard,intr,rsize=3,wsize=4 test.location:/export/with/folders";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
		
		testString = "mnt -rw,soft,rsize=344444,wsize=4 test.location:/export";
		NfsMountParamsValidator.validateAutoFsEntry(testString);		
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<export-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_invalidString()
	{
		String testString = "/mnt -rw,soft,rsize=344444,wsize=4test.location:/export";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<export-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_emptyString()
	{
		String testString = "";
		NfsMountParamsValidator.validateAutoFsEntry(testString);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<export-name> 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateAutoFsEntry_nullString()
	{
		String testString = null;
		NfsMountParamsValidator.validateAutoFsEntry(testString);		
	}
	
	/**
	 * This validates that a exportResource is in the form
	 * <location>:<export-name>
	 */
	@Test
	public void testValidateExportResource_valid()
	{
		String testString = "valid.address:/valid/path";
		NfsMountParamsValidator.validateExportResource(testString);
	}
	/**
	 * This validates that a exportResource is in the form
	 * <location>:<export-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportResource_invalid()
	{
		String testString = "valid.address$/valid/path";
		NfsMountParamsValidator.validateExportResource(testString);
	}
	/**
	 * This validates that a exportResource is in the form
	 * <location>:<export-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportResource_emptyParam()
	{
		String testString = "";
		NfsMountParamsValidator.validateExportResource(testString);
	}
	/**
	 * This validates that a exportResource is in the form
	 * <location>:<export-name>
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testValidateExportResource_nullParam()
	{
		String testString = null;
		NfsMountParamsValidator.validateExportResource(testString);
	}
	
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test
	public void validateExportOptions_valid()
	{
		String testString = "-ro,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,soft,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,hard,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,soft,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,soft,rsize=99999,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
		testString = "-rw,soft,rsize=1,wsize=85814";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_perm()
	{
		String testString = "-pl,hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_missing_perm()
	{
		String testString = "-hard,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_resetType()
	{
		String testString = "-ro,poo,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_missing_resetType()
	{
		String testString = "-ro,intr,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_interruptable()
	{
		String testString = "-ro,hard,gurgle,rsize=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_rsize_name()
	{
		String testString = "-ro,hard,intr,rsizzlee=1,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_rsize_value()
	{
		String testString = "-ro,hard,intr,rsize=aasd,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_empty_rsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_missing_rsize()
	{
		String testString = "-ro,hard,intr,wsize=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_wsize_name()
	{
		String testString = "-ro,hard,intr,rsize=,wsizzle=2";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_invalid_wsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=aaaa";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_empty_wsize_value()
	{
		String testString = "-ro,hard,intr,rsize=,wsize=";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 */
	@Test(expected=IllegalArgumentException.class)
	public void validateExportOptions_missing_wsize()
	{
		String testString = "-ro,hard,intr,rsize=";
		NfsMountParamsValidator.validateExportOptions(testString);
	}
}
