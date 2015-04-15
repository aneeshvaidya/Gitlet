package datastructs;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.nio.file.Files;

public class CNode implements Serializable{

	private int id;
	private String message;
	private String date;
	private int prevID;
	private HashMap<String, String> allFiles;
	private HashMap<String, String> newFiles;

	public CNode(String message, CNode previous, int idFromSize){
		this.message = message;
		this.id = idFromSize;
		this.date = calculateDate();
		this.prevID = previous.id;
		this.allFiles = new HashMap<String,String>(previous.allFiles);
		this.newFiles = new HashMap<String,String>();
	}
	
	public CNode(String message){
		this.id = 0;
		this.message = message;
		this.date = calculateDate();
		this.prevID = -1;
		this.allFiles = new HashMap<String,String>();
		this.newFiles = new HashMap<String,String>();
	}
	
	public void addFiles(HashSet<String> staged, HashSet<String> removal){
		//removes all the files tagged for removal
		for (String remove: removal){
			this.allFiles.remove(remove);
		}
		
		//adds all staged files to newFiles, and creates the path format for its value
		//adds this time around's staged files into allFiles for update purposes
		for (String stagedFile: staged){
			String path = gitPathFormat(stagedFile, this.id);
			this.newFiles.put(stagedFile, path);
			this.allFiles.put(stagedFile, path);
		}
		
		//add support for copying the files here
		for(String fileName: this.newFiles.keySet()){
			copyFiles(new File(fileName), new File(this.newFiles.get(fileName)));
		}
		
	}
	
	private static String gitPathFormat(String filename, int ID){
		return ".gitlet/"+ID+"/"+filename;
	}
	
	public void revertToCommit(){
		for (String fileLocation: this.allFiles.keySet()){
			revertFile(fileLocation);
		}
	}
	
	public void revertFile(String fileName){
		//you might need to change this to account for failure cases
		copyFiles(new File(this.allFiles.get(fileName)),new File(fileName));
	}
	
	private static boolean isGitPath(File fileName){
		return fileName.toPath().toString().startsWith(".");		
	}
	
	private static void copyFiles(File source, File destination){
	    //This method copies the file in source to destination.
		try{
			if (isGitPath(destination)){ //are you trying to copy into the git folder?
				if(!destination.getParentFile().exists()){
		    		destination.getParentFile().mkdirs();
		    	}
				Files.copy(source.toPath(), destination.toPath());
			}else{
				Files.delete(destination.toPath());
				Files.copy(source.toPath(), destination.toPath());
			}
		} catch (IOException e){
	    	System.out.println("You threw an exception copying files");
	    }	
	}
	
	public HashMap<String,String> getNews(){
		return this.newFiles;
	}
	
	public HashMap<String,String> getAll(){
		return this.allFiles;
	}

	public int getID(){
		return this.id;
	}

	public String getMessage(){
		return this.message;
	}

	public int getPreviousID(){
		return this.prevID;
	}

	private String calculateDate(){
		String tFormat=Calendar.YEAR+"-"+Calendar.MONTH+"-"+Calendar.DATE+" ";
		tFormat += Calendar.HOUR+":"+Calendar.MINUTE+":"+Calendar.SECOND;
		
		return tFormat;
	}
	
	public String getDate(){
		return this.date;
	}

	public boolean fileExistsInCommit(String file){
		return allFiles.containsKey(file);
	}
}