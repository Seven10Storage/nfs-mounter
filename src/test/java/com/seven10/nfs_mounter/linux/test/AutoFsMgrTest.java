/**
 * 
 */
package com.seven10.nfs_mounter.linux.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

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
	private int fileLinesCountTestCount = 10;
	
	private AutoFsMgr createValidMgr(String funcName) throws IOException
	{
		String afsTemplatePath = tempFolder.newFolder(funcName).getAbsolutePath() + "/" + templateFileName;
		AutoFsMgr autoFsMgr = new AutoFsMgr(afsTemplatePath);
		assertNotNull(autoFsMgr);
		return autoFsMgr;
	}
	
	private Set<String> createValidMountPointList()
	{
		// java generic types are stupid so we can't do things the proper way
		// here
		Set<String> rval = new HashSet<String>();
		rval.add("foo -ro,soft,rsize=3,wsize=3 valid.address:/foo/mnt");
		rval.add("bar -ro,soft,rsize=3,wsize=3 valid.address:/bar/mnt");
		rval.add("fizz -ro,soft,rsize=3,wsize=3 valid.address:/fizz/mnt");
		rval.add("fuzz -ro,soft,rsize=3,wsize=3 valid.address:/fuzz/mnt");
		rval.add("butt -ro,soft,rsize=3,wsize=3 valid.address:/butt/mnt");
		
		return rval;
	}
	
	private Set<String> createinvalidMountPointList()
	{
		// java generic types are stupid so we can't do things the proper way
		// here
		Set<String> rval = new HashSet<String>();
		rval.add("sda sda");
		return rval;
	}
	
	
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#AutoFsMgr(java.lang.String)}
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
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#AutoFsMgr(java.lang.String)}
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
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#AutoFsMgr(java.lang.String)}
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
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#AutoFsMgr(java.lang.String)}
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
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#setAutoFsEntryList(Set)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSetMountPointsList_valid() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		Set<String> expected = createValidMountPointList();
		autoFsMgr.setAutoFsEntryList(expected);
		autoFsMgr.updateFile();
		
		Set<String> actual = autoFsMgr.getAutoFsEntryList();
		
		for(String a:actual)
		{
			assertTrue(expected.contains(a));
		}
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#setAutoFsEntryList(Set)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMountPointsList_invalid() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		Set<String> expected = createinvalidMountPointList();
		autoFsMgr.setAutoFsEntryList(expected);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#setAutoFsEntryList(Set)}
	 * . An empty list is ok, so no exception is expected.
	 * 
	 * @throws IOException
	 */
	@Test
	public void testSetMountPointsList_empty() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		Set<String> expected = new HashSet<String>(0);
		autoFsMgr.setAutoFsEntryList(expected);
		Set<String> actual = autoFsMgr.getAutoFsEntryList();
		assertEquals(0, actual.size());
		assertEquals(expected, actual);
		
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#setAutoFsEntryList(Set)}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetMountPointsList_null() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testSetMountPointsList");
		Set<String> expected = null;
		autoFsMgr.setAutoFsEntryList(expected);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#getAutoFsEntryList()}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetMountPointList_default() throws IOException
	{
		AutoFsMgr autoFsMgr = createValidMgr("testGetMountPointList_default");
		Set<String> expected = new HashSet<String>(0);
		Set<String> actual = autoFsMgr.getAutoFsEntryList();
		assertNotNull(actual);
		assertEquals(0, actual.size());
		assertEquals(expected, actual);
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#updateFile()}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testUpdateFile() throws IOException
	{
		String funcName = "testUpdateFile";
		AutoFsMgr autoFsMgr = createValidMgr(funcName);
		autoFsMgr.updateFile();
		int expectedLines = 0;
		int actualLines = autoFsMgr.getFileLineCount();
		assertEquals(expectedLines, actualLines);	//verify there are no entries in the list
		
		Set<String> expected = createValidMountPointList();
		autoFsMgr.setAutoFsEntryList(expected);
		autoFsMgr.updateFile();
		expectedLines = expected.size();
		
		actualLines = autoFsMgr.getFileLineCount();
		assertEquals(expectedLines, actualLines);	//verify there are no entries in the list
		
		autoFsMgr.setAutoFsEntryList(new HashSet<String>(0)); // set the list
																// to empty, but
																// do NOT update
																// file
		Set<String> actual = autoFsMgr.getAutoFsEntryList(); // this should
																// read the list
																// from the file
		for(String a:actual)
		{
			assertTrue(expected.contains(a));
		}
	}
	
	/**
	 * Test method for
	 * {@link com.seven10.nfs_mounter.linux.AutoFsMgr#getFileLineCount()}
	 * .
	 * 
	 * @throws IOException
	 */
	@Test
	public void testGetFileLineCount() throws IOException
	{
		String afsTemplatePath = tempFolder.newFolder("testGetFileLineCount").getAbsolutePath() + "/" + templateFileName;
		AutoFsMgr autoFsMgr = new AutoFsMgr(afsTemplatePath);
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(afsTemplatePath, false));
		for(int index = 0; index< fileLinesCountTestCount; index++)
		{
			writer.write(String.format("%d", index));
			writer.newLine();
		}
		writer.newLine();
		writer.close();
		
		int expected = fileLinesCountTestCount;
		int actual = autoFsMgr.getFileLineCount();
		assertEquals(expected, actual);
	}
	
}
