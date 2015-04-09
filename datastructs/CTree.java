package datastructs;

import java.util.ArrayList;
import java.util.HashMap;

/**
*	This is a CTree class, created to maintain a record of all branch names, and current head pointers
*	at each branch. The name implies it is a tree, when it is more like a collection of pointers.
**/

public class CTree{

	private HashMap<String, CNode> heads;
	private ArrayList<String> branchNames;
	private String currentBranch;
	private int branches;

	public CTree(){
		this.heads = new HashMap<String, CNode>();
		this.branchNames = new ArrayList<String>();
		this.currentBranch = "master";
		this.branches = 1;
	}

	public CNode getCurrentBranch(){
		return this.currentBranch;
	}

	public CNode getHeadAtBranch(String branchName){
		return this.heads.get(branchName);
	}

	public void branchTree(String newBranchName){
		branches++;
		branchNames.add(newBranchName);
		heads.put(newBranchName, heads.get(currentBranch));
	}

}