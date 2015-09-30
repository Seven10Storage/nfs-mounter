/**
 * 
 */
package com.seven10.nfs_mounter.linux;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;

/**
 * @author kmm
 *		
 */
public class AutoFsMgr
{
	/**
	 * Sometimes the jvm takes a bit of time to transfer the new settings to the autoFs demon.
	 * This delay is meant to provide a pause during which time the jvm and filesystem can catch up
	 */
	private static final int autoFsFlushDelay = 1000;
	private static final Logger m_logger = LogManager.getFormatterLogger(AutoFsMgr.class.getName());
	String autoFsTemplatePath;
	HashSet<String> mountPointLines;
	
	private void createFileIfNeeded() throws IOException
	{
		File templateFile = new File(autoFsTemplatePath);
		if(templateFile.exists() == false)
		{
			m_logger.debug(".createFileIfNeeded(): autofs template file path '%s' doesn't exist. Creating", autoFsTemplatePath);
			templateFile.createNewFile();
		}		
	}
	/**
	 * constructs the autofs manager
	 * @param afsTemplatePath the path where the template file resides. You must have write privilages to this file
	 */
	public AutoFsMgr(String afsTemplatePath)
	{
		if(afsTemplatePath == null || afsTemplatePath.isEmpty())
		{
			throw new IllegalArgumentException(".ctor(): afsTemplatePath cannot be null or empty");
		}
		if( afsTemplatePath == "/dev/null")
		{
			throw new IllegalArgumentException(".ctor(): templatePath cannot be /dev/null");
		}
		m_logger.debug(".ctor(): autofs template file path = %s", afsTemplatePath);
		autoFsTemplatePath = afsTemplatePath;
		mountPointLines = new HashSet<String>();
	}
	/**
	 * sets the current mount point list to the contents of the string
	 * @param mpList list of entries to be added to the autofs template file
	 */ 
	public void setAutoFsEntryList(Set<String> mpList)
	{
		if(mpList == null)
		{
			throw new IllegalArgumentException(".setMountPointsList(): mpList must not be null");
		}
		for(String mp:mpList)
		{
			NfsMountParamsValidator.validateAutoFsEntry(mp);
		}
		m_logger.debug(".setMountPointsList(): setting mountPointList = '%s'", StringUtils.join(mpList, File.pathSeparator));
		mountPointLines.clear();
		mountPointLines.addAll(mpList);
	}
	/**
	 * gets the list of all entries currently in the autoFS template file
	 * @return the list of mountpoint entries
	 * @throws FileNotFoundException if the template file can't be found
	 * @throws IOException if the template file can't be accessed
	 */
	public Set<String> getAutoFsEntryList() throws FileNotFoundException, IOException
	{
		String line;
		createFileIfNeeded();
		try (
		    FileReader reader = new FileReader(autoFsTemplatePath);
		    BufferedReader br = new BufferedReader(reader);
		) 
		{
		    while ((line = br.readLine()) != null)
		    {
		        if( line.isEmpty() == false)
		        {
		        	m_logger.debug(".getMountPointList(): adding line '%s' to mountPointList", line);
		        	mountPointLines.add(line);
		        }		        	
		    }
		}
		m_logger.debug(".getMountPointList(): returning mountPointList='%s'", StringUtils.join(mountPointLines, File.pathSeparator));
		return new HashSet<String>(mountPointLines);
	}
	
	/**
	 * Updates the autoFS template file with the current list of mount point lines
	 * @throws IOException Thrown if the file can't be open
	 */
	public void updateFile() throws IOException
	{
		m_logger.debug(".updateFile(): opening file '%s' for update", autoFsTemplatePath);
		BufferedWriter writer = new BufferedWriter(new FileWriter(autoFsTemplatePath, false));
		m_logger.debug(".updateFile(): updating with '%d' lines", mountPointLines.size());
		for (String line : mountPointLines)
		{
			writer.write(line);
			writer.newLine();
		}
		writer.newLine();
		writer.close();
		try
		{
			// we need to give autofs time to get the changes from the jvm
			Thread.sleep(autoFsFlushDelay);
		}
		catch (InterruptedException e)
		{
			// I don't care about this exception
		}
		m_logger.debug(".updateFile(): update completed");
	}
	
	/**
	 * retrieves the number of lines in the autoFsTemplate file
	 * @return the number of lines excluding blank lines
	 * @throws IOException
	 */
	public int getFileLineCount() throws IOException
	{
		BufferedReader reader = new BufferedReader(new FileReader(autoFsTemplatePath));
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
}
