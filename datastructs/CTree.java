package datastructs;

import java.util.ArrayList;
import java.util.HashMap;
import java.io.Serializable;
import java.util.HashSet;

/**
*	This is a CTree class, created to maintain a record of all branch names, and current head pointers
*	at each branch. The name implies it is a tree, when it is more like a collection of pointers.
**/

public class CTree implements Serializable{

	private HashMap<String, CNode> heads; //<branch name, head node>
	private ArrayList<String> branchNames;
	private String currentBranch;
	private int branches;

	public CTree(CNode commit){
		this.heads = new HashMap<String, CNode>();
		this.branchNames = new ArrayList<String>();
		this.currentBranch = "master";
		this.branches = 1;
		this.heads.put(currentBranch, commit);
		this.branchNames.add(currentBranch);
	}

	public String getCurrentBranch(){
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
	
	public void newCommit(String message, HashSet staged){
		CNode currentNode = this.heads.get(currentBranch);
		CNode nextNode = new CNode(message, currentNode);
		nextNode.addFiles(staged); //put the staged items into the commit node
		heads.put(currentBranch, nextNode);
	}
	
	public CNode getID(int id){
		//Given an ID, return the CNode at that point in time.
	}

}