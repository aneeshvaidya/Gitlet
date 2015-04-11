package datastructs;

import java.util.Date;
import java.util.LinkedList;
import java.io.Serializable;

public class CNode implements Serializable{

	private int id;
	private String message;
	private boolean head;
	private Date date;
	private LinkedList<String> files;
	private int prevName;

	public CNode(String message, CNode prev){
		this.message = message;
		this.head = true;
		this.id = this.hashCode();
		this.date = new Date();
		this.files = new LinkedList<String>();
		if (prev != null){
			this.prevName = prev.getID();
		}
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

	public LinkedList<String> getFiles(){
		return this.files;
	}	
}