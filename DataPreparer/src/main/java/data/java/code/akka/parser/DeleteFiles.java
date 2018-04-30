package data.java.code.akka.parser;

import java.code.parser.utils.FileHelper;

public class DeleteFiles {

	public static void main(String[] args) {
		FileHelper.deleteDirectory(args[0]);
	}

}
