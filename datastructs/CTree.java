package datastructs;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashSet;
import java.io.IOException;
import java.util.LinkedList;

/**
*	This is a CTree class, created to maintain a record of all branch names, and current head pointers
*	at each branch.
**/

public class CTree implements Serializable{

	private HashMap<String, Integer> branchHeads; //<branch name, ID of head node>
	private HashMap<Integer,CNode> tree; 
	private String currentBranch;
	private HashMap<String, LinkedList<Integer>> cMessageToID = new HashMap<String, LinkedList<Integer>>();

	public CTree(){
		this.branchHeads = new HashMap<String, Integer>();
		this.tree = new HashMap<Integer,CNode>();
		this.currentBranch = "master";
		CNode firstElement = new CNode("initial commit");
		this.branchHeads.put(currentBranch,firstElement.getID());
		this.tree.put(firstElement.getID(),firstElement);
		addToMTID(firstElement.getMessage(),firstElement.getID());
	}

	public String getCurrentBranch(){
		return this.currentBranch;
	}

	public CNode getHeadAtBranch(String branchName){
		if (branchName == null){
			return null;
		}
		return getCNode(this.branchHeads.get(branchName));
	}

	public void branchTree(String newBranchName){
		this.branchHeads.put(newBranchName, branchHeads.get(currentBranch));
	}
	
	private void addToMTID(String element, Integer id){
		if (this.cMessageToID.containsKey(element)){
			this.cMessageToID.get(element).add(id);
		}else{
			LinkedList<Integer> newVal = new LinkedList<Integer>();
			newVal.add(id);
			this.cMessageToID.put(element, newVal);
		}
	}
	
	public void newCommit(String message, HashSet<String> staged, HashSet<String> remove){
		CNode previousNode = getHeadAtBranch(currentBranch);
		CNode newCommitNode = new CNode(message,previousNode);
		newCommitNode.addFiles(staged, remove);
		this.branchHeads.put(currentBranch, newCommitNode.getID());
		this.tree.put(newCommitNode.getID(),newCommitNode);
		addToMTID(newCommitNode.getMessage(), newCommitNode.getID());
	}
	
	public LinkedList<Integer> getID(String message){
		return cMessageToID.get(message);
	}
	
	public CNode getCNode(Integer id){
		//Given an ID, return the CNode at that point in time.
		if (id == null){
			return null;
		}
		return tree.get(id);
	}
	
	private CNode getCurrentHead(){
		return getHeadAtBranch(currentBranch);
	}
	
	public boolean fileInTree(String fileName){
		return getCurrentHead().getAll().keySet().contains(fileName);
	}
	
	public boolean fileChanged(String fileName){
		try{
			if (fileInTree(fileName)){
				String fileNameInGitlet = getCurrentHead().getAll().get(fileName);
				return Arrays.equals(
						Files.readAllBytes((new File(fileName)).toPath()), 
						Files.readAllBytes((new File(fileNameInGitlet)).toPath()));
			}else{
				return false;
			}
			
			
		}
		catch(IOException e){
			System.out.println("You threw an exception on sameFile in CTree");
			return false;
		}
	}

}