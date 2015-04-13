package datastructs;

import java.util.Date;
import java.util.HashSet;
import java.io.IOException;
import java.io.Serializable;
import java.io.File;
import java.nio.file.Files;

public class CNode implements Serializable{

	private int id;
	private String message;
	private boolean head;
	private Date date;
	private int prevName;
	private HashSet<String> oldFiles;
	private HashSet<String> newFiles;
	private CNode prev, next;

	public CNode(String message, CNode previous){
		this.message = message;
		this.head = true;
		this.id = this.hashCode();
		this.date = new Date();
		this.oldFiles = new HashSet<String>();
		this.newFiles = new HashSet<String>();
		this.prev = previous;
		previous.next = this;
		if (prev != null){
			this.prevName = prev.getID();
		}
	}
	
	public void addFiles(HashSet staged){
		newFiles = staged;
		if (this.prev != null){
			for(String previous: this.prev.getOlds()){
				if (!staged.contains(previous)){
					oldFiles.add(previous);
				}
			}
			for(String previous: this.prev.getNews()){
				if (!staged.contains(previous)){
					oldFiles.add(previous);
				}
			}
		}
		//add support for copying the files here
		for(String fileName: this.newFiles){
			copyFiles(new File(fileName), new File(".gitlet/"+this.id+"/"+fileName));
		}
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
	
	public HashSet<String> getNews(){
		return this.newFiles;
	}
	
	public HashSet<String> getOlds(){
		return this.newFiles;
	}

	public int getID(){
		return this.id;
	}

	public String getMessage(){
		return this.message;
	}

	public int getPrevious(){
		return this.prevName;
	}

	public boolean head(){
		return this.head;
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