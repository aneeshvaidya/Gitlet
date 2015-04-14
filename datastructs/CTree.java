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

	public boolean isABranch(String branchName){
		return branchHeads.containsKey(branchName);
	}
	
	private CNode getHeadAtBranch(String branchName){
		if (branchName == null){
			return null;
		}
		return getCNode(this.branchHeads.get(branchName));
	}
	
	public void revertToBranch(String branchName){
		getHeadAtBranch(branchName).revertToCommit();;
		this.currentBranch=branchName;
	}
	
	public void revertSingleFile(String fileName){
		getCurrentHead().revertFile(fileName);
	}
	
	public void revertSingleFileBranch(Integer commitID, String fileName){
		CNode requestedCommit = getCNode(commitID);
		if (requestedCommit == null){
			System.out.println("No commit with that id exists.");
		}else if(requestedCommit.fileExistsInCommit(fileName)){
			getCNode(commitID).revertFile(fileName);
		}else{
			System.out.println("No need to checkout the current branch.");
		}
	}
	
	public void resetToCommit(Integer commitID){
		CNode target = getCNode(commitID);
		target.revertToCommit();;
		this.branchHeads.put(currentBranch, target.getID());
	}
	
	public void rmBranch(String branchName){
		if(! branchHeads.containsKey(branchName)){
			System.out.println("A branch with that name does not exist.");
		}else if(branchName == currentBranch){
			System.out.println("Cannot remove the current branch.");
		}else{
			branchHeads.remove(branchName);
		}
	}

	public void branchTree(String newBranchName){
		if (branchHeads.containsKey(newBranchName)){
			System.out.print("A branch with that name already exists.");
		}else{
			this.branchHeads.put(newBranchName, branchHeads.get(currentBranch));
		}
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
	
	public HashMap<String, Integer> getBranches(){
		return this.branchHeads;
	}
	
	public void newCommit(String message, HashSet<String> staged, HashSet<String> remove){
		
		CNode previousNode = getHeadAtBranch(currentBranch);
		CNode newCommitNode = new CNode(message,previousNode, tree.size());
		
		newCommitNode.addFiles(staged, remove);
		this.branchHeads.put(currentBranch, newCommitNode.getID());
		this.tree.put(newCommitNode.getID(),newCommitNode);
		addToMTID(newCommitNode.getMessage(), newCommitNode.getID());
	}
	
	public LinkedList<Integer> getID(String message){
		return cMessageToID.get(message);
	}
	
	private CNode getCNode(Integer id){
		//Given an ID, return the CNode at that point in time.
		if (id == null){
			return null;
		}
		return tree.get(id);
	}
	
	public CNode getCurrentHead(){
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
	
	public CNode getPrevious(CNode a){
		if (a.getID() == -1){
			return null;
		}
		return tree.get(a.getPreviousID());
	}

}