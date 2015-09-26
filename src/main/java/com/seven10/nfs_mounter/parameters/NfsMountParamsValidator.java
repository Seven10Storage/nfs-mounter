package com.seven10.nfs_mounter.parameters;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.InetAddressValidator;

public class NfsMountParamsValidator
{
	private static final Pattern shareNamePattern;
	private static final Pattern hostNamePattern;
	private static final Pattern mountPattern;
	static 
	{
		String pathRegex = "(^/[a-zA-Z0-9]+(/[a-zA-Z0-9]*)*$)|(^\\\\[a-zA-Z0-9]+(\\\\[a-zA-Z0-9]*)*)";
		String hostNameRegEx = "^[a-zA-Z0-9]+(\\.?[a-zA-Z0-9]+(\\-[a-zA-Z0-9])*)*(\\:?\\d+)*$";
		String mountRegEx = "^/[a-zA-Z0-9]+(/[a-zA-Z0-9]*)*$";
		shareNamePattern = Pattern.compile(pathRegex);
		hostNamePattern = Pattern.compile(hostNameRegEx);
		mountPattern = Pattern.compile(mountRegEx);
	}
	
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
}