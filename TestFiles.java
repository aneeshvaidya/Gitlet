import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TestFiles {
	//This class exists to clear up the use of Java's Files package.

	public static void main(String[] args) {
		File source = new File("myTestFile.txt");
		File destination = new File ("puto/cow/myTestFile.txt");
		File source1 = new File("Test.java");
		File destination1 = new File ("puto/Test.java");
		copyFiles(source,destination);
		copyFiles(source1,destination1);
	}
	
	private static void copyFiles(File source, File destination){
	    //This method copies the file in source to destination.
		try{
	    	if(!destination.getParentFile().exists()){
	    		destination.getParentFile().mkdirs();
	    	}
			Files.copy(source.toPath(), destination.toPath());
	    } catch (IOException e){
	    	System.out.println("You threw an exception, bro");
	    }
		
	}

}
