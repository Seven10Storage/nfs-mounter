package com.seven10.nfs_mounter.parameters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NfsMountExportParameter
{
	private static final Logger m_logger = LogManager.getFormatterLogger(NfsMountExportParameter.class.getName());
	
	private String mountPoint;
	private String location;
	private String linuxExportName;
	
	public NfsMountExportParameter(String mountPoint, String location, String exportName)
	{
		NfsMountParamsValidator.validateMountPoint(mountPoint);
		NfsMountParamsValidator.validateLocation(location);
		NfsMountParamsValidator.validateExportName(exportName);
		m_logger.debug(".ctor(): mountPoint='%s', location='%s', shareName='%s'", mountPoint, location, exportName);
		this.mountPoint = mountPoint;
		this.location = location;
		String lsn = exportName.replace("\\", "/"); //convert windows to linux if necessary
		m_logger.debug(".ctor(): linuxExportName='%s'", lsn);
		this.linuxExportName = lsn;
	}
	public String getMountPoint() { return mountPoint; }
	public String getLocation() { return location; }
	public String getLinuxExportName() { return linuxExportName; }
	public String getWindowsExportName()
	{
		String rval = linuxExportName.replace("/", "\\");
		m_logger.debug(".ctor(): windowsExportName='%s'", rval);
		return rval;
	}
}