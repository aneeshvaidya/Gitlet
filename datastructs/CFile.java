package datastructs;

import java.util.HashMap;
import java.io.Serializable;

public class CFile implements Serializable{

	private HashMap<String, Integer> trackerMap; //hashmap of <branch name, commit id>

	public CFile(){
		this.trackerMap = new HashMap<String, Integer>();
	}

	public int getCommitAtBranch(String key){
		return this.trackerMap.get(key);
	}

}