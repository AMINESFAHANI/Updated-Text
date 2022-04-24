package com.apolis.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.apolis.entity.User;


public class DeviceUserManager {
    
     Set<User> deviceUserListUpdated;	
     List<User> portalUserList;
     List<User> deviceUserList;
     
	public void updateUsers() {
		
		portalUserList.stream().forEach(x->deviceUserListUpdated.add(x));
		deviceUserList.stream().forEach(x->deviceUserListUpdated.add(x));
	}
    
	public void readTabDelimitedFile(String filePath,List<User> array)throws FileNotFoundException{
	    Scanner scan = new Scanner(new File(filePath));
	   
	    while(scan.hasNext()){
	        User user = new User();
	    	String curLine = scan.nextLine();
	        String[] splitted = curLine.split("\t");
	        user.setId(Long.parseLong(splitted[0].trim()));
	        user.setDeviceId(Long.parseLong(splitted[1].trim()));
	        user.getStatus().setAuthorisation(Integer.parseInt(splitted[2].trim()));
	        user.getStatus().setTraining(Integer.parseInt(splitted[3].trim()));
	        user.getStatus().setAdmin(Integer.parseInt(splitted[3].trim()));
	        array.add(user);
	    }
	    
	    scan.close();
	}
    
     
	public void TabDelimitedDataFile() throws IOException{
		
		
	    try (PrintWriter writer = new PrintWriter(
	       Files.newBufferedWriter(Paths.get("deviceUserListUpdated.dat")))){
	            for (User user : deviceUserListUpdated) {
	                
	            	String auth;
	        		String adm;
	        		String train;
	        		
	        		if(user.getStatus().getAuthorisation()==1)  auth="AuthorisedAdmin";
	        		else auth="AuthorisedOperator";
	        		if(user.getStatus().getAdmin()==1) adm="DisabledOperator";
	        		else adm="DisabledAdmin";
	        		if(user.getStatus().getTraining()==1) train="Trained";
	        		else train="Untrained";
	            	
	            	
	            	writer.printf("%1$d\t%2$d\t\t%3$s\t\t%4$s\t\t%5$s",
	                        user.getId(), user.getDeviceId(), auth, adm, train);
	                writer.println();
	            }
	        }
	    }
	     
	
     public static void main(String[] args) throws IOException {
    	 
    	 DeviceUserManager manager = new DeviceUserManager();
    	 manager.readTabDelimitedFile("portalUserLis.dat", manager.portalUserList);
    	 manager.readTabDelimitedFile("deviceUserLis.dat", manager.deviceUserList);
    	 manager.updateUsers();
    	 manager.TabDelimitedDataFile();
     }

	}


