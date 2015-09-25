package com.seven10.nfs_mounter.parameters;

public class NfsMountVolumesParameter
{
	private String mountPoint;
	private String location;
	private String shareName;
	
	public NfsMountVolumesParameter(String mountPoint, String location, String shareName)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateShareName(shareName);
		this.mountPoint = mountPoint;
		this.location = location;
		this.shareName = shareName;
	}
	public String getMountPoint() { return mountPoint; }
	public String getLocation() { return location; }
	public String getLinuxShareName() { return shareName; }
	public String getWindowsShareName()
	{
		// TODO Auto-generated method stub
		return shareName.replace("/", "\\");
	}
}