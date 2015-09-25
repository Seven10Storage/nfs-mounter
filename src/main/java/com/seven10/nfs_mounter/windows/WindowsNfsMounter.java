package com.seven10.nfs_mounter.windows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountVolumesParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

public class WindowsNfsMounter extends NfsMounter
{

	public WindowsNfsMounter(NfsMounterFactorySettings nfsFactorySettings)
	{
		super(nfsFactorySettings);
	}

	@Override
	public	List<File> mountVolumes(List<NfsMountVolumesParameter> parameterObject)
	{
		if(parameterObject == null)
		{
			throw new IllegalArgumentException(".mountVolumes(): parameterObject must not be null");
		}
		ArrayList<File> rval = new ArrayList<File>();
		for(NfsMountVolumesParameter parameter: parameterObject)
		{
			String path = NfsMounterFormater.formatMountAsUnc(parameter);
			File file = new File(path);
			if(file.exists())
			{
				rval.add(file);
			}
		}
		return rval;
	}

	@Override
	public	void unMountVolumes(List<String> mountPoints)
	{
		if(mountPoints == null)
		{
			throw new IllegalArgumentException(".unMountVolumes(): mountPoints must not be null");
		}
		// This function does nothing right now because we don't actually "mount" the folder.
		return;
		
	}

	@Override
	public	boolean isMounted(String mountPoint)
	{
		if(mountPoint == null || mountPoint.isEmpty())
		{
			throw new IllegalArgumentException(".isMounted(): parameterObjects must not be null or empty");
		}
		File file = new File(mountPoint);
		return file.exists();
	}
	
}