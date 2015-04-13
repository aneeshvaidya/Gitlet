import datastructs.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.HashMap;

public class Gitlet {

	private CTree commitTree;
	private HashMap<String, CFile> allFiles;
	private HashSet<String> staged;
	private HashSet<String> removal;

    public static void main(String[] args){
        switch(args[0]){
            case "init":
                
        }
    }

    private void init(){
    	File gitlet = new File(".gitlet");
        if (!gitlet.exists()){
            gitlet.mkdir();
            CNode firstCommit = new CNode("initial commit", null);
            commitTree = new CTree(firstCommit);
            staged = new HashSet<String>();
            removal = new HashSet<String>();
        }
        else{
            System.out.println("A gitlet version control system already exists in the current directory");
        }
    }
    
    private void add(String fileName){
    	File addFile = new File(fileName);
    	if (! addFile.exists()){
    		staged.add(fileName);
    		//missing if file has been modified
    		int commitID = allFiles.get(fileName).getCommitAtBranch(commitTree.getCurrentBranch());
    		
    	}
    	else{
    		System.out.println("File does not exist");
    	}
    	
    }
    
    private void commit(String message){
    	//Update CTree by calling new commit
    	//Update CFile
    	//Clear staged, clear remove
    }
    
    private static CTree loadCommitTree() {
		CTree commitTree = null;
		File commitFile = new File("commitTree.ser");
		if (commitFile.exists()) {
            try {
                FileInputStream fileIn = new FileInputStream(commitFile);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                commitTree = (CTree) objectIn.readObject();
            } catch (IOException e) {
                String msg = "IOException while loading commit tree.";
                System.out.println(msg);
            } catch (ClassNotFoundException e) {
                String msg = "ClassNotFoundException while loading commit tree.";
                System.out.println(msg);
            }	
    	}
    	return commitTree;
	}

	private static void saveCommitTree(CTree commitTree){
		if(commitTree == null){
			return;
		}
		try {
            File commitFile = new File("commitTree.ser");
            FileOutputStream fileOut = new FileOutputStream(commitFile);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(commitTree);
        } catch (IOException e) {
            String msg = "IOException while saving commit tree.";
            System.out.println(msg);
        }

	}


}