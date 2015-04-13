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
    		if (commitTree.fileChanged(fileName)){
    			staged.add(fileName);
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
        File removeFile = new File(fileName);
        if (staged.contains(fileName)) {
            staged.remove(fileName);
        } else if (commitTree.fileInTree(fileName)){
            removal.add(fileName);
        } else{
            System.out.println("No reason to remove the file.");
        }
    }

    private void find(String message){
        int commitID = commitTree.getID(message);
//        for (int id : commitID) System.out.println(id);
    }



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