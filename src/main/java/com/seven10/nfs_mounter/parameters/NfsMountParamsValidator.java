package com.seven10.nfs_mounter.parameters;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;

public class NfsMountParamsValidator
{
	private static final Pattern exportNamePattern;
	private static final Pattern hostNamePattern;
	private static final Pattern mountPattern;
	private static final Pattern afsEntryPattern;
	static 
	{
		String pathRegex = "(^/[a-zA-Z0-9_]+(/[a-zA-Z0-9_]*)*$)|(^\\\\[a-zA-Z0-9_]+(\\\\[a-zA-Z0-9_]*)*)";
		String hostNameRegEx = "^[a-zA-Z0-9\\-]+(\\.?[a-zA-Z0-9\\-]+(\\-[a-zA-Z0-9\\-])*)*(\\:?\\d+)*$";
		String mountRegEx = "^[a-zA-Z0-9_.]+$";
		String afsEntryRegEx = "^\\-(ro|rw),(soft|hard),(intr,|)rsize=\\d+,wsize=\\d+$";
		exportNamePattern = Pattern.compile(pathRegex);
		hostNamePattern = Pattern.compile(hostNameRegEx);
		mountPattern = Pattern.compile(mountRegEx);
		afsEntryPattern = Pattern.compile(afsEntryRegEx);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * &lt;mount-name&gt;
	 * @param mountPoint the mountPoint string to validate
	 * @throws IllegalArgumentException if the mountPoint parameter is invalid
	 */
	public static void validateMountPoint(String mountPoint) throws IllegalArgumentException
	{
		if(mountPoint == null || mountPoint.isEmpty())
		{
			throw new IllegalArgumentException(".validateMountPoint(): mountPoint cannot be null or empty");
		}
		if(mountPattern.matcher(mountPoint).find() == false )
		{
			throw new IllegalArgumentException(
					String.format(".validateMountPoint(): '%s' does not appear to be a valid mount point", mountPoint));
		}
	}
	public static void validateLocation(String location) throws IllegalArgumentException
	{
		if(location == null || location.isEmpty())
		{
			throw new IllegalArgumentException(".validateLocation(): location cannot be null or empty");
		}
		if( InetAddressValidator.getInstance().isValid(location) == false &&
				hostNamePattern.matcher(location).find() == false)
		{
			throw new IllegalArgumentException(String.format(".validateLocation(): '%s' does not appear to be a valid network location",
					location));
		}
	}
	
	public static void validateExportName(String exportName) throws IllegalArgumentException
	{
		if(exportName == null || exportName.isEmpty())
		{
			throw new IllegalArgumentException(".validateExportName(): exportName cannot be null or empty");
		}
		if( exportNamePattern.matcher(exportName).find() == false)
		{
			throw new IllegalArgumentException(".validateExportName(): exportName does not appear to be a valid UNC path");
		}
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * &lt;mountPoint&gt; -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm &lt;location&gt;:&lt;export-name&gt;
	 * @param afsEntry the autofs template file entry to validate. must conform to the expected format
	 */
	public static void validateAutoFsEntry(String afsEntry) throws IllegalArgumentException
	{
		if(afsEntry == null || afsEntry.isEmpty())
		{
			throw new IllegalArgumentException(".validateAutoFsEntry(): afsEntry cannot be null or empty");
		}
		String[] groups = afsEntry.split("\\s+");
		if(groups.length != 3)
		{
			throw new IllegalArgumentException(".validateAutoFsEntry(): afsEntry does not appear to be a valid AutoFs template entry");
		}
		validateMountPoint(groups[0]);
		validateExportOptions(groups[1]);
		validateExportResource(groups[2]);		
	}
	/**
	 * This validates that a exportResource is in the form
	 * &lt;location&gt;:&lt;export-name&gt;
	 * @param exportResource the export path to validate. must conform to the expected format
	 */
	public static void validateExportResource(String exportResource) throws IllegalArgumentException
	{
		if(exportResource == null || exportResource.isEmpty())
		{
			throw new IllegalArgumentException(".validateExportResource(): exportResource cannot be null or empty");
		}
		String[] groups = exportResource.split(":");
		if(groups.length != 2)
		{
			throw new IllegalArgumentException(
					String.format(".validateExportResource(): exportResource='%s' does not appear to be a valid AutoFs export resource", exportResource));
		}
		validateLocation(groups[0]);
		validateExportName(groups[1]);
		
	}
	/**
	 * This validates that a exportOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 * @param exportOptions the export options to validate. must conform to the expected format
	 */
	public static void validateExportOptions(String exportOptions) throws IllegalArgumentException
	{
		if(exportOptions == null || exportOptions.isEmpty())
		{
			throw new IllegalArgumentException(".validateExportOptions(): exportOptions cannot be null or empty");
		}
		if(afsEntryPattern.matcher(exportOptions).find() == false)
		{
			throw new IllegalArgumentException(
					String.format(".validateExportOptions(): '%s' does not appear to be a valid mount point option string", exportOptions));
		}
		
	}
}