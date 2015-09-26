/**
 * 
 */
package com.seven10.nfs_mounter.linux.test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import com.seven10.nfs_mounter.linux.AutoFsMgr;

/**
 * @author kmm
 *		
 */
public class AutoFsMgrTest
{
	
	private static final String templateFileName = "auto.template";
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();
	
	private AutoFsMgr createValidMgr(String funcName) throws IOException
	{
		String afsTemplatePath = tempFolder.newFolder(funcName).getAbsolutePath() + "/" +templateFileName;
		AutoFsMgr autoFsMgr = new AutoFsMgr(afsTemplatePath);
		assertNotNull(autoFsMgr);
		return autoFsMgr;
	}
	
	private List<String> createValidMountPointList()
	{
		// java generic types are stupid so we can't do things the proper way
		// here
		List<String> rval = new ArrayList<String>();
		rval.add("/foo");
		rval.add("/bar");
		rval.add("/fizz");
		rval.add("/fuzz");
		rval.add("/butt");
		
		return rval;
	}
	
	private List<String> createinvalidMountPointList()
	{
		// java generic types are stupid so we can't do things the proper way
		// here
		List<String> rval = new ArrayList<String>();
		rval.add("sda sda");
		return rval;
	}
	
	private void checkFile(int expectedLines, String funcName) throws IOException
	{
		String path = String.format("%s/%s/%s", tempFolder.getRoot().getAbsolutePath(), funcName, templateFileName);
		File file = new File(path);
		assertTrue(file.exists());
		BufferedReader reader = new BufferedReader(new FileReader(path));
		int actualLines = 0;
		while (reader.readLine() != null) actualLines++;
		reader.close();
		assertEquals(expectedLines, actualLines);		
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#AutoFsMgr(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testAutoFsMgr_Valid() throws IOException
	{
		createValidMgr("testAutoFsMgr_Valid");
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#AutoFsMgr(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAutoFsMgr_invalid() throws IOException
	{
		
		String afsTemplatePath = "/dev/null";
		new AutoFsMgr(afsTemplatePath);
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#AutoFsMgr(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAutoFsMgr_empty() throws IOException
	{
		
		String afsTemplatePath = "";
		new AutoFsMgr(afsTemplatePath);
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#AutoFsMgr(java.lang.String)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testAutoFsMgr_null() throws IOException
	{
		
		String afsTemplatePath = null;
		new AutoFsMgr(afsTemplatePath);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#setMountPointsList(java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSetMountPointsList_valid() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		List<String> expected = createValidMountPointList();
		autoFsMgr.setMountPointsList(expected);
		List<String> actual = autoFsMgr.getMountPointList();
		assertEquals(expected, actual);
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#setMountPointsList(java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetMountPointsList_invalid() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		List<String> expected = createinvalidMountPointList();
		autoFsMgr.setMountPointsList(expected);
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#setMountPointsList(java.util.List)}
	 * .
	 * An empty list is ok, so no exception is expected.
	 * @throws IOException
	 */
	@Test
	public void testSetMountPointsList_empty() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		List<String> expected = new ArrayList<String>(0);
		autoFsMgr.setMountPointsList(expected);
		List<String> actual = autoFsMgr.getMountPointList();
		assertEquals(0, actual.size());
		assertEquals(expected, actual);
		
	}
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#setMountPointsList(java.util.List)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSetMountPointsList_null() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		List<String> expected = null;
		autoFsMgr.setMountPointsList(expected);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#getMountPointList()}.
	 * @throws IOException 
	 */
	@Test
	public void testGetMountPointList_default() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testGetMountPointList_default");
		List<String> expected = new ArrayList<String>(0);
		List<String> actual = autoFsMgr.getMountPointList();
		assertNotNull(actual);
		assertEquals(0,actual.size());
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.datamover.object.nfs.AutoFsMgr#updateFile()}.
	 * @throws IOException 
	 */
	@Test
	public void testUpdateFile() throws IOException
	{
		String funcName = "testUpdateFile";
		AutoFsMgr autoFsMgr = createValidMgr(funcName);
		autoFsMgr.updateFile();
		int expectedLines = 0;
		checkFile(expectedLines, funcName);
		List<String> expected = createValidMountPointList();
		autoFsMgr.setMountPointsList(expected);
		autoFsMgr.updateFile();
		expectedLines = expected.size();
		checkFile(expectedLines, funcName);
		autoFsMgr.setMountPointsList(new ArrayList<String>(0)); // set the list to empty, but do NOT update file
		List<String> actual = autoFsMgr.getMountPointList(); // this should read the list from the file
		assertEquals(expected, actual);
	}

	
	
}
