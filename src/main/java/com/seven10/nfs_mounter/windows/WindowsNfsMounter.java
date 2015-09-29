package com.seven10.nfs_mounter.windows;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.seven10.nfs_mounter.helpers.NfsMounterFormater;
import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

public class WindowsNfsMounter extends NfsMounter
{

	public WindowsNfsMounter(NfsMounterFactorySettings nfsFactorySettings)
	{
		super(nfsFactorySettings);
	}

	@Override
	public	List<File> mountExports(List<NfsMountExportParameter> parameterObject)
	{
		if(parameterObject == null)
		{
			throw new IllegalArgumentException(".mountVolumes(): parameterObject must not be null");
		}
		ArrayList<File> rval = new ArrayList<File>();
		for(NfsMountExportParameter parameter: parameterObject)
		{
			m_logger.debug(".mountVolumes(): attempting to mount volume '%s'", parameter.toString());
			String path = NfsMounterFormater.formatMountAsUnc(parameter);
			File file = new File(path);
			if(file.exists())
			{
				m_logger.debug(".mountVolumes(): file '%s' exists, adding to list", parameter.toString());
				rval.add(file);
			}
		}
		return rval;
	}
	@Override
	public	File mountExport(NfsMountExportParameter parameterObject)
	{
		List<NfsMountExportParameter> paramsList = new ArrayList<NfsMountExportParameter>(1);
		paramsList.add(parameterObject);
		List<File> files = mountExports(paramsList);
		File rval = (files.size() >= 1) ? files.get(0):new File("");
		return rval;
	}

	@Override
	public	void unMountExports(List<String> mountPoints)
	{
		if(mountPoints == null)
		{
			throw new IllegalArgumentException(".unMountVolumes(): mountPoints must not be null");
		}
		m_logger.debug(".unMountVolumes(): Windows nfs clients don't actually mount or unmount. doing nothing here");
		// This function does nothing right now because we don't actually "mount" the folder.
		return;
		
	}
	
	@Override
	public	void unMountExport(String mountPoints)
	{
		List<String> paramsList = new ArrayList<String>(1);
		paramsList.add(mountPoints);
		unMountExports(paramsList);		
	}

	@Override
	public	boolean isMounted(String mountPoint)
	{
		if(mountPoint == null || mountPoint.isEmpty())
		{
			throw new IllegalArgumentException(".isMounted(): parameterObjects must not be null or empty");
		}
		File file = new File(mountPoint);
		boolean rval = file.exists();
		m_logger.debug(".isMounted(): voulume mounted = %s", Boolean.toString(rval));
		return rval;
	}
	
}