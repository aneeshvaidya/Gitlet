package datastructs;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.nio.file.Files;

public class CNode implements Serializable{

	private static int commit = 0;
	private int id;
	private String message;
	private Date date;
	private int prevID;
	private HashMap<String, String> allFiles;
	private HashMap<String, String> newFiles;

	public CNode(String message, CNode previous){
		this.id = CNode.commit;
		CNode.commit+=1;
		this.message = message;
		this.date = new Date();
		this.prevID = previous.id;
		this.allFiles = new HashMap<String,String>(previous.allFiles);
		for (String key: previous.newFiles.keySet()){
			this.allFiles.put(key, previous.newFiles.get(key));
		}
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
	
	private static void copyFiles(File source, File destination){
	    //This method copies the file in source to destination.
		try{
	    	if(!destination.getParentFile().exists()){
	    		destination.getParentFile().mkdirs();
	    	}
			Files.copy(source.toPath(), destination.toPath());
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

	public Date getDate(){
		return this.date;
	}

//	//edit everything from here onwards
//	public LinkedList<String> getFiles(){
//		return this.files;
//	}
//	
//	public CNode(CNode, LinkedList<File> ){
//		//used for creating a new CNode from a added linked list
//	}
//	
//	public stage(LinkedList<File>)
	
}