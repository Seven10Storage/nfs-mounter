package com.seven10.nfs_mounter.parameters;

import java.util.regex.Pattern;

import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class NfsMountParamsValidator
{
	private static Pattern shareNamePattern;
	static 
	{
		String uncPattern = "([A-Z|a-z]:\\\\[^*|\"<>?\\n]*)|(\\\\\\\\.*?\\\\.*)";
		shareNamePattern = Pattern.compile(uncPattern);
	}
	
	public static void validateMountPoint(String mountPoint) throws IllegalArgumentException
	{
		if(mountPoint == null || mountPoint.isEmpty())
		{
			throw new IllegalArgumentException(".validateMountPoint(): mountPoint cannot be null or empty");
		}
	}
	public static void validateLocation(String location)
	{
		if(location == null || location.isEmpty())
		{
			throw new IllegalArgumentException(".validateLocation(): location cannot be null or empty");
		}
		if( InetAddressValidator.getInstance().isValid(location) == false ||
				DomainValidator.getInstance(true).isValid(location))
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