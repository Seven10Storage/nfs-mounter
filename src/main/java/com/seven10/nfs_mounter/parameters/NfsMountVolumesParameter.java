package com.seven10.nfs_mounter.parameters;

public class NfsMountVolumesParameter
{
	private String mountPoint;
	private String location;
	private String linuxShareName;
	
	public NfsMountVolumesParameter(String mountPoint, String location, String shareName)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateShareName(shareName);
		this.mountPoint = mountPoint;
		this.location = location;
		this.linuxShareName = shareName.replace("\\", "/"); //convert windows to linux if necessary
	}
	public String getMountPoint() { return mountPoint; }
	public String getLocation() { return location; }
	public String getLinuxShareName() { return linuxShareName; }
	public String getWindowsShareName()
	{
		String rval = linuxShareName.replace("/", "\\");
		return rval;
	}
}