import datastructs.*;
import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;


public class Test{

	public static void main(String[] args){
		try{
			File source = new File(".gitlet/1/wug.txt");
			File destination = new File("wug.txt");
			Files.delete(destination.toPath());
			Files.copy(source.toPath(), destination.toPath());
		}
		catch (IOException e){
			System.out.println("Yup, your code failed... again");
		}
		
		
//
//		CNode node1 = new CNode("first commit", null);
//		CNode node2 = new CNode("second commit", node1);
//
//		CTree c = new CTree(node2);
//
//		System.out.println(node1.getID());
//		System.out.println(node2.getPrevious());
//		System.out.println(c.getCurrentBranch());
//		System.out.println(c.getHeadAtBranch("master"));
//
//		saveCommitTree(c);
//
//		CTree loaded = loadCommitTree();
//		System.out.println(c.getCurrentBranch());
//		System.out.println(c.getHeadAtBranch("master"));
//		CNode head = c.getHeadAtBranch("master");
//		System.out.println(node2.getPrevious());
//
//	}
//
//	private static CTree loadCommitTree() {
//		CTree commitTree = null;
//		File commitFile = new File("commitTree.ser");
//		if (commitFile.exists()) {
//            try {
//                FileInputStream fileIn = new FileInputStream(commitFile);
//                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
//                commitTree = (CTree) objectIn.readObject();
//            } catch (IOException e) {
//                String msg = "IOException while loading commit tree.";
//                System.out.println(msg);
//            } catch (ClassNotFoundException e) {
//                String msg = "ClassNotFoundException while loading commit tree.";
//                System.out.println(msg);
//            }	
//    	}
//    	return commitTree;
//	}
//
//	private static void saveCommitTree(CTree commitTree){
//		if(commitTree == null){
//			return;
//		}
//		try {
//            File commitFile = new File("commitTree.ser");
//            FileOutputStream fileOut = new FileOutputStream(commitFile);
//            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
//            objectOut.writeObject(commitTree);
//        } catch (IOException e) {
//            String msg = "IOException while saving commit tree.";
//            System.out.println(msg);
//        }
//
	}
}