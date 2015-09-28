package com.seven10.nfs_mounter.parameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NfsMountVolumesParameter
{
	private static final Logger m_logger = LogManager.getFormatterLogger(NfsMountVolumesParameter.class.getName());
	
	private String mountPoint;
	private String location;
	private String linuxShareName;
	
	public NfsMountVolumesParameter(String mountPoint, String location, String shareName)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateShareName(shareName);
		m_logger.debug(".ctor(): mountPoint='%s', location='%s', shareName='%s'", mountPoint, location, shareName);
		this.mountPoint = mountPoint;
		this.location = location;
		String lsn = shareName.replace("\\", "/"); //convert windows to linux if necessary
		m_logger.debug(".ctor(): linuxShareName='%s'", lsn);
		this.linuxShareName = lsn;
	}
	public String getMountPoint() { return mountPoint; }
	public String getLocation() { return location; }
	public String getLinuxShareName() { return linuxShareName; }
	public String getWindowsShareName()
	{
		String rval = linuxShareName.replace("/", "\\");
		m_logger.debug(".ctor(): windowsShareName='%s'", rval);
		return rval;
	}
}