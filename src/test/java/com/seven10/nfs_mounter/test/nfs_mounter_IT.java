/**
 * 
 */
package com.seven10.nfs_mounter.test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.NfsMounterFactory;
import com.seven10.nfs_mounter.exceptions.NfsClientException;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;

/**
 * @author kmm
 *
 */
public class nfs_mounter_IT
{
	private List<NfsMountExportParameter> createValidParamObjects()
	{
		List<NfsMountExportParameter> rval = new ArrayList<NfsMountExportParameter>();
		rval.add(new NfsMountExportParameter("nfs_test","192.168.21.60", "/ifs"));
		return rval;
	}
	private int getFileCountFromPath(String absolutePath)
	{
		int rval = new File(absolutePath).list().length;
		return rval;
	}


	private int calculateFileCount(File f) throws IOException
	{
		int rval = f.list().length;
		return rval;
	}
	
	@Test
	public void test_LinuxMounter() throws NfsClientException, IOException
	{
		NfsMounter mounter = NfsMounterFactory.getMounter();
		List<NfsMountExportParameter> parameterObjects = createValidParamObjects();
		List<File> actualVolumes = mounter.mountExports(parameterObjects);
		for(File f: actualVolumes)
		{
			int expectedCount = calculateFileCount(f);
			int actualCount = getFileCountFromPath(f.getAbsolutePath());
			assertTrue(actualCount > 0);
			assertEquals(expectedCount, actualCount);
		}
		
	}	
}
