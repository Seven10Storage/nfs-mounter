package com.seven10.nfs_mounter.parameters;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;

public class NfsMountParamsValidator
{
	private static final Pattern shareNamePattern;
	private static final Pattern hostNamePattern;
	private static final Pattern mountPattern;
	private static final Pattern afsEntryPattern;
	static 
	{
		String pathRegex = "(^/[a-zA-Z0-9]+(/[a-zA-Z0-9]*)*$)|(^\\\\[a-zA-Z0-9]+(\\\\[a-zA-Z0-9]*)*)";
		String hostNameRegEx = "^[a-zA-Z0-9]+(\\.?[a-zA-Z0-9]+(\\-[a-zA-Z0-9])*)*(\\:?\\d+)*$";
		String mountRegEx = "^[a-zA-Z0-9_.]+$";
		String afsEntryRegEx = "^\\-(ro|rw),(soft|hard),(intr,|)rsize=\\d+,wsize=\\d+$";
		shareNamePattern = Pattern.compile(pathRegex);
		hostNamePattern = Pattern.compile(hostNameRegEx);
		mountPattern = Pattern.compile(mountRegEx);
		afsEntryPattern = Pattern.compile(afsEntryRegEx);
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * /<mount-name>
	 * @param mountPoint
	 * @throws IllegalArgumentException
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
	public static void validateLocation(String location)
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
	
	public static void validateShareName(String shareName)
	{
		if(shareName == null || shareName.isEmpty())
		{
			throw new IllegalArgumentException(".validateShareName(): shareName cannot be null or empty");
		}
		if( shareNamePattern.matcher(shareName).find() == false)
		{
			throw new IllegalArgumentException(".validateShareName(): shareName does not appear to be a valid UNC path");
		}
	}
	/**
	 * validates if the mountPoint provided is acceptable and of the form
	 * <mountPoint> -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm <location>:<share-name>
	 * @param mp
	 */
	public static void validateAutoFsEntry(String afsEntry)
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
		validateShareOptions(groups[1]);
		validateShareResource(groups[2]);		
	}
	/**
	 * This validates that a shareResource is in the form
	 * <location>:<share-name>
	 * @param shareResource
	 */
	public static void validateShareResource(String shareResource)
	{
		if(shareResource == null || shareResource.isEmpty())
		{
			throw new IllegalArgumentException(".validateShareResource(): shareResource cannot be null or empty");
		}
		String[] groups = shareResource.split(":");
		if(groups.length != 2)
		{
			throw new IllegalArgumentException(
					String.format(".validateShareResource(): shareResource='%s' does not appear to be a valid AutoFs share resource", shareResource));
		}
		validateLocation(groups[0]);
		validateShareName(groups[1]);
		
	}
	/**
	 * This validates that a shareOptions string is in the form
	 * -(ro|rw),(hard|soft),[intr,] rsize=nnnn,wsize=mmmm
	 * @param shareOptions
	 */
	public static void validateShareOptions(String shareOptions)
	{
		if(shareOptions == null || shareOptions.isEmpty())
		{
			throw new IllegalArgumentException(".validateShareOptions(): shareOptions cannot be null or empty");
		}
		if(afsEntryPattern.matcher(shareOptions).find() == false)
		{
			throw new IllegalArgumentException(
					String.format(".validateShareOptions(): '%s' does not appear to be a valid mount point option string", shareOptions));
		}
		
	}
}