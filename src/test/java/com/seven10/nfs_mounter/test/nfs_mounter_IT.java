/**
 * 
 */
package com.seven10.nfs_mounter.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.SystemUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.seven10.nfs_mounter.NfsMounter;
import com.seven10.nfs_mounter.NfsMounterFactory;
import com.seven10.nfs_mounter.exceptions.NfsClientException;
import com.seven10.nfs_mounter.parameters.NfsMountExportParameter;
import com.seven10.nfs_mounter.parameters.NfsMounterFactorySettings;

/**
 * @author kmm
 * 		
 */
public class nfs_mounter_IT
{
	private static final String templateFileName = "auto.template";
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	private int testIterationsCount = 6;
	private int timeBetweenRuns = 31 * 1000;
	private int timeInfoIncrements = 10 * 1000;
	
	private List<NfsMountExportParameter> createValidParamObjects(int iteration)
	{
		List<NfsMountExportParameter> rval = new ArrayList<NfsMountExportParameter>();
		rval.add(new NfsMountExportParameter(String.format("nfs_test_%d", iteration), "192.168.21.60", "/ifs"));
		rval.add(new NfsMountExportParameter(String.format("paat_sS_%d", iteration), "192.168.21.111",
				"/home/mintserver1/Src"));
		rval.add(new NfsMountExportParameter(String.format("paat_sD_%d", iteration), "192.168.21.111",
				"/home/mintserver1/Dest"));
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
	
	public int getFileLineCount(String templatePath) throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(templatePath));
		int actualLines = 0;
		String line;
		while ((line = reader.readLine()) != null)
		{
			if (line.isEmpty() == false)
			{
				actualLines++;
			}
		}
		reader.close();
		return actualLines;
	}
	
	@Test
	public void test_LinuxMounter() throws NfsClientException, IOException
	{
		if (SystemUtils.IS_OS_LINUX)
		{
			for (int i = 0; i < testIterationsCount; i++)
			{
				// make sure the settings are the default
				NfsMounterFactorySettings testMounterConfig = new NfsMounterFactorySettings(); 
				NfsMounterFactory.setMounterConfig(testMounterConfig);
				NfsMounter mounter = NfsMounterFactory.getMounter();
				List<NfsMountExportParameter> parameterObjects = createValidParamObjects(i);
				System.out.printf("test linuxMounter iteration %d\n", i);
				
				List<File> actualVolumes = mounter.mountExports(parameterObjects);
				for (File f : actualVolumes)
				{
					assertTrue(f.exists());
					int expectedCount = calculateFileCount(f);
					int actualCount = getFileCountFromPath(f.getAbsolutePath());
					assertTrue(actualCount > 0);
					assertEquals(expectedCount, actualCount);
				}
				mounter.unMountAll();
				for (int time = timeBetweenRuns; time > timeInfoIncrements; time -= timeInfoIncrements)
				{
					System.out.printf("waiting  %d seconds\n", time/1000);
					try
					{
						Thread.sleep(timeInfoIncrements);
					}
					catch (InterruptedException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}
		else
		{
			System.out.println("OS is not linux, skipping test");
		}
	}
	
	@Test
	public void test_LinuxUnMounter() throws NfsClientException, IOException
	{
		if (SystemUtils.IS_OS_LINUX)
		{
			String afsTemplatePath = tempFolder.newFolder("test_LinuxUnMounter").getAbsolutePath() + "/"
					+ templateFileName;
			new File(afsTemplatePath).createNewFile(); // ensure file exists
			
			NfsMounterFactorySettings testMounterConfig = new NfsMounterFactorySettings();
			testMounterConfig.linuxAutoFsTemplatePath = afsTemplatePath;
			NfsMounterFactory.setMounterConfig(testMounterConfig);
			NfsMounter mounter = NfsMounterFactory.getMounter();
			
			List<NfsMountExportParameter> parameterObjects = createValidParamObjects(0);
			int original = getFileLineCount(afsTemplatePath);
			mounter.mountExports(parameterObjects);
			int afterMount = getFileLineCount(afsTemplatePath);
			assertEquals(original + parameterObjects.size(), afterMount);
			for (NfsMountExportParameter mep : parameterObjects)
			{
				mounter.unMountExport(mep.getMountPoint());
				int afterUnMount = getFileLineCount(afsTemplatePath);
				
				assertEquals(--afterMount, afterUnMount);
			}
		}
		else
		{
			System.out.println("OS is not linux, skipping test");
		}
		
	}
	
}
