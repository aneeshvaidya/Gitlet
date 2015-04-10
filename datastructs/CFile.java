package datastructs;

import java.util.HashMap;
import java.io.Serializable;

public class CFile implements Serializable{

	private String name;
	private HashMap<Integer, Integer> trackerMap;

	public CFile(String name){
		this.name = name;
		this.trackerMap = new HashMap<Integer, Integer>();
	}

	public String getName(){
		return this.name;
	}

	public int getCommitAtBranch(int key){
		return this.trackerMap.get(key);
	}

}