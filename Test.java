import datastructs.*;

public class Test{

	public static void main(String[] args){

		CNode node1 = new CNode("first commit", null);
		CNode node2 = new CNode("second commit", node1);

		CTree c = new CTree(node2);

		System.out.println(node1.getID());
		System.out.println(node2.getPrevious());
		System.out.println(c.getCurrentBranch());
		System.out.println(c.getHeadAtBranch("master"));
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