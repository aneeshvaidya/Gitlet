import datastructs.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class Gitlet {

	private CTree commitTree;

    public static void main(String[] args){
        switch(args[0]){
            case "init":
                
        }
    }

    private static void init(){
    	File gitlet = new File(".gitlet");
        if (!gitlet.exists()){
            gitlet.mkdir();
            CNode firstCommit = new CNode("initial commit", null);
            commitTree = new CTree(firstCommit);
        }
        else{
            System.out.println("A gitlet version control system already exists in the current directory");
        }
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