import datastructs.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.HashMap;
import java.io.Serializable;
import java.util.Calendar;

public class Gitlet implements Serializable{

	private CTree commitTree;
	private HashSet<String> staged;
	private HashSet<String> removal;

    public static void main(String[] args){
    	Gitlet g = loadGitlet();
        switch(args[0]){
            case "init":
                g = new Gitlet();
                g.init();
                break;
            case "add":
                g.add(args[1]);
                break;
            case "commit":
                g.commit(args[1]);
                break;
            case "remove":
                g.remove(args[1]);
                break;
            case "log":
            	g.log();
            	break;
            case "find":
            	g.find(args[1]);
            	break;
            case "status":
            	g.status();
            	break;
            case "checkout":
            	if (args.length>3){
            		g.checkout(Integer.parseInt(args[1]), args[2]);
            	}else{
            		g.checkout(args[1]);
            	}
            	break;
            case "branch":
            	g.branch(args[1]);
            	break;
            case "rm-branch":
            	g.removeBranch(args[1]);
            	break;
            case "reset":
            	g.reset(Integer.parseInt(args[1]));
            	break;
            
        }
        saveGitlet(g);
    }

    private void init(){
    	File gitlet = new File(".gitlet");
        if (!gitlet.exists()){
            gitlet.mkdir();
            commitTree = new CTree();
            staged = new HashSet<String>();
            removal = new HashSet<String>();
        }
        else{
            System.out.println("A gitlet version control system already exists in the current directory");
        }
    }
    
    private void add(String fileName){
    	File addFile = new File(fileName);
    	if (addFile.exists()){
    		if (commitTree.fileInTree(fileName)){
                String fileNameInGitlet = commitTree.getCurrentHead().getAll().get(fileName);
                try {
                    if (Arrays.equals(
                            Files.readAllBytes((new File(fileName)).toPath()),
                            Files.readAllBytes((new File(fileNameInGitlet)).toPath()))) {
                        System.out.println("File has not been modified since the last commit.");

                    }
                    else{
                        staged.add(fileName);
                    }
                } catch (IOException e) {
                    System.out.println("You threw an exception comparing files.");
                }

            } else if (!commitTree.fileInTree(fileName)){
                staged.add(fileName);
            } else{
    			System.out.println("File has not been modified since the last commit.");
    		}
    	}else{
    		System.out.println("File does not exist");
    	}
    }

    private void commit(String message) {
        commitTree.newCommit(message, staged, removal);
        staged.clear();
        removal.clear();
    }

    private void remove(String fileName) {
        if (staged.contains(fileName)) {
            staged.remove(fileName);
        } else if (commitTree.fileInTree(fileName)){
            removal.add(fileName);
        } else{
            System.out.println("No reason to remove the file.");
        }
    }

    private void find(String message){
        for (int id : commitTree.getID(message)) System.out.println(id);
    }
    
    private void log(){
    	String returnValue = "";
    	CNode current = commitTree.getCurrentHead();
    	while (current != null){
    		returnValue+="====\n"+"Commit "+current.getID()+".\n"+current.getDate().toString()+"\n"+current.getMessage()+"\n\n";
    		current = commitTree.getPrevious(current);
    	}
    	System.out.println(returnValue);
    }
    
    private void status(){
    	System.out.println("=== Branches ===");
    	for (String branchName: commitTree.getBranches().keySet()){
    		if (branchName == commitTree.getCurrentBranch()){
    			System.out.println("*"+branchName);
    		}else{
    			System.out.println(branchName);
    		}
    	}
    	System.out.println("=== Staged Files ===");
    	for (String stagedFiles: staged){
    		System.out.println(stagedFiles);
    	}
    	System.out.println("=== Files Marked for Removal ===");
    	for (String removalFiles: removal){
    		System.out.println(removalFiles);
    	}
    	
    }

    private void checkout(String name){
    	//case branch
    	//case file
    	//fail message
    	if (commitTree.isABranch(name)){
    		//case it's a branch
    		commitTree.revertToBranch(name);
    	}else if(commitTree.fileInTree(name)){
    		commitTree.revertSingleFile(name);
    	}else{
    		System.out.println("File does not exist in the most recent commit, or no such branch exists.");
    	}
    }
    
    private void checkout (Integer commitID, String fileName){
    	commitTree.revertSingleFileBranch(commitID,fileName);
    }
    
    private void branch(String branchName){
    	commitTree.branchTree(branchName);
    }
    
    private void removeBranch(String branchName){
    	commitTree.rmBranch(branchName);
    }
    
    private void reset(Integer id){
    	commitTree.resetToCommit(id);
    }
    
//    private void merge(String branchName){
//    	commitTree.mergeBranches(String branchName);
//    }
//    
    private static Gitlet loadGitlet(){
		Gitlet g = null;
		File gitlet = new File(".gitlet.ser");
		if (gitlet.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(gitlet);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                g = (Gitlet) objectIn.readObject();
            } catch (IOException e) {
                String msg = "IOException while loading Gitlet state.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading Gitlet state.";
                System.out.println(msg);
            }	
    	}
    	return g;
	}

	private static void saveGitlet(Gitlet g){
		if(g == null){
			return;
		}
		try {
            File saveFile = new File(".gitlet.ser");
            FileOutputStream fileOut = new FileOutputStream(saveFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(g);
        } catch (IOException e) {
            String msg = "IOException while saving Gitlet.";
            System.out.println(msg);
        }

	}


}