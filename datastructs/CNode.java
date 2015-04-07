package datastructs;

import java.util.Date;

public class CNode{

	private int id;
	private String message;
	private CNode prev;
	private boolean head;
	private Date date;

	public CNode(String message, CNode prev){
		this.message = message;
		this.prev = prev;
		this.head = true;
		this.id = this.hashCode();
		this.date = new Date();
	}
	
}