package com.seven10.nfs_mounter;

import org.apache.commons.lang3.SystemUtils;

import com.seven10.nfs_mounter.exceptions.NfsClientException;
import com.seven10.nfs_mounter.linux.AutoFsMgr;
import com.seven10.nfs_mounter.linux.LinuxNfsMounter;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;
import com.seven10.nfs_mounter.windows.WindowsNfsMounter;

public class NfsMounterFactory
{
	private static NfsMounterFactorySettings nfsFactorySettings = new NfsMounterFactorySettings();
	
	public static NfsMounter getMounter() throws NfsClientException
	{
		NfsMounter rval;
		// test if OS is windows or linux
		if( SystemUtils.IS_OS_LINUX )
		{
			
			AutoFsMgr autoFsMgr = new AutoFsMgr(nfsFactorySettings.linuxAutoFsTemplatePath);
			rval = new LinuxNfsMounter(nfsFactorySettings, autoFsMgr);
		}
		else if( SystemUtils.IS_OS_WINDOWS)
		{
			rval = new WindowsNfsMounter(nfsFactorySettings);
		}
		else
		{
			throw new NfsClientException(".getMounter(): could not determine client OS");
		}
		return rval;
	}
	public static void setMounterConfig(NfsMounterFactorySettings settings)
	{
		if( settings == null)
		{
			throw new IllegalArgumentException(".setMounterConfig(): settings cannot be null");
		}
		nfsFactorySettings = settings;
	}
	public static NfsMounterFactorySettings getMounterConfig()
	{
		return nfsFactorySettings;
	}
}