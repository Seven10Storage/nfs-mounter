/**
 * 
 */
package com.seven10.nfs_mounter.linux;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.seven10.nfs_mounter.parameters.NfsMountParamsValidator;

/**
 * @author kmm
 *		
 */
public class AutoFsMgr
{
	
	String autoFsTemplatePath;
	List<String> mountPointLines;
	
	/**
	 * constructs the autofs manager
	 * @param afsTemplatePath
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
		autoFsTemplatePath = afsTemplatePath;
		mountPointLines = new ArrayList<String>();
	}
	/**
	 * sets the current mount point list to the contents of the string
	 * @param mpList
	 */
	public void setMountPointsList(List<String> mpList)
	{
		if(mpList == null)
		{
			throw new IllegalArgumentException(".setMountPointsList(): mpList must not be null");
		}
		for(String mp:mpList)
		{
			NfsMountParamsValidator.validateMountPoint(mp);
		}
		mountPointLines.clear();
		mountPointLines.addAll(mpList);
	}
	/**
	 * gets the list of all entries currently in the autoFS template file
	 * @return the list of mountpoint entries
	 * @throws FileNotFoundException if the template
	 * @throws IOException
	 */
	public List<String> getMountPointList() throws FileNotFoundException, IOException
	{
		mountPointLines.clear();
		String line;
		try (
		    FileReader reader = new FileReader(autoFsTemplatePath);
		    BufferedReader br = new BufferedReader(reader);
		) 
		{
		    while ((line = br.readLine()) != null)
		    {
		        if( line.isEmpty() == false)
		        {
		        	mountPointLines.add(line);
		        }		        	
		    }
		}
		return mountPointLines;
	}
	/**
	 * Updates the autoFS template file with the current list of mount point lines
	 * @throws IOException Thrown if the file can't be open
	 */
	public void updateFile() throws IOException
	{
		BufferedWriter writer = new BufferedWriter(new FileWriter(autoFsTemplatePath, false));
		for (String line : mountPointLines)
		{
			writer.write(line);
			writer.newLine();
		}
		writer.close();
	}
}
