package com.seven10.nfs_mounter.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import org.junit.Test;

public class MountTest 
{
	@Test
	public void mountTest() throws Exception
	{
	    // Mount part
		String strExportMount = "192.168.21.111:/home/mintserver1/Src";
		
		String strMountDir = "/mnt/HydraDevices/NFS_Test";
	    boolean blnMakeDir = new File(strMountDir).mkdirs();
	    
	    if (!blnMakeDir)
	    	if (!new File(strMountDir).exists())
	    		throw new RuntimeException("Could not make Mount Dir");
		
		String strMountCommand = "mount -o v3 " + strExportMount + " " + strMountDir;	    
	    System.out.println("\nMount Command: " + strMountCommand);   
	    
	    Runtime r = Runtime.getRuntime();
	    Process p = r.exec(strMountCommand);
	    p.waitFor();
	    
	    System.out.println("Mount Command Return Code: " + p.exitValue());
	    
	    BufferedReader b = new BufferedReader(new InputStreamReader(p.getErrorStream()));
	    String line = "";

	    while ((line = b.readLine()) != null) {
	      System.out.println(line);
	    }

	    b.close();
	    
	    File f = new File(strMountDir);
	    
	    String[] fList = f.list();
	    for (String strFileName : fList)
	    {
	    	System.out.println("Found: " + strFileName);
	    }
	}
}
