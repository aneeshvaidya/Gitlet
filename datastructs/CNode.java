package datastructs;

import java.util.Date;
import java.util.LinkedList;


public class CNode{

	private int id;
	private String message;
	private CNode prev;
	private boolean head;
	private Date date;
	private LinkedList<CFile> files;

	public CNode(String message, CNode prev){
		this.message = message;
		this.prev = prev;
		this.head = true;
		this.id = this.hashCode();
		this.date = new Date();
		this.files = new LinkedList<CFile>();
	}

	public int getID(){
		return this.id;
	}

	public String getMessage(){
		return this.message;
	}

	public CNode getPrevious(){
		return this.prev;
	}

	public boolean head(){
		return this.head;
	}

	public Date getData(){
		return this.date;
	}

	public LinkedList<CFile> getFiles(){
		return this.files;
	}	
}