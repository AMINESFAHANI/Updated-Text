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
     Set<User> portalUserList;
     Set<User> deviceUserList;
     
	public void updateUsers() {
		
		portalUserList.stream().filter(x-> deviceUserList.contains(x)).
		forEach(x->deviceUserListUpdated.add(x));
		deviceUserList.stream().forEach(x->deviceUserListUpdated.add(x));
	}
    
	public Set<User> readTabDelimitedFile(String filePath,Set<User> array)throws FileNotFoundException{
	    Scanner scan = new Scanner(new File(filePath));
	   
	    while(scan.hasNext()){
	        User user = new User();
	    	String curLine = scan.nextLine();
	        String[] splitted = curLine.split("\t");
	        user.setId(splitted[0].trim());
	        user.setDeviceId(splitted[1].trim());
	        Integer bit7 = Integer.parseInt(splitted[2].trim(),16) & (1<<7);
	        byte bite7 = bit7.byteValue();
	        user.getStatus().setAuthorisation(bite7);
	        Integer bit6 = Integer.parseInt(splitted[2].trim(),16) & (1<<6);
	        byte bite6 = bit6.byteValue();
	        user.getStatus().setTraining(bite6);
	        Integer bit5 = Integer.parseInt(splitted[2].trim(),16) & (1<<5);
	        byte bite5 = bit5.byteValue();
	        user.getStatus().setAdmin(bite5);
	        array.add(user);
	    }
	    
	    scan.close();
	    return array;
	}
    
     
	public void writeTabDelimitedDataFile(String filePath) throws IOException{
		
		
	    try (PrintWriter writer = new PrintWriter(
	       Files.newBufferedWriter(Paths.get(filePath)))){
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
    	 manager.readTabDelimitedFile("main/resources/PortalUserList.txt", manager.portalUserList);
    	 manager.readTabDelimitedFile("main/resources/DeviceUserList.txt", manager.deviceUserList);
    	 manager.updateUsers();
    	 manager.writeTabDelimitedDataFile("deviceUserListUpdated.dat");
     }

	}


