package datastructs;

import java.util.ArrayList;
import java.util.HashMap;

public class CTree{

	private HashMap<String, CNode> heads;
	private ArrayList<String> branchNames;
	private CNode currentBranch;

	/*
	We'll talk about how to implement this fully later
	**/

	public CTree(){
		this.heads = new HashMap<String, CNode>();
		this.branchNames = new ArrayList<String>();
		this.currentBranch = new CNode("initial commit", null);
	}

	public CNode getCurrentBranch(){
		return this.currentBranch;
	}

	public CNode getHeadAtBranch(String branchName){
		return this.heads.get(branchName);
	}

}