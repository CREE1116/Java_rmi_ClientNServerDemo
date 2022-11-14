package studentNCourseDataManagement;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class textWriter {

	public <T> textWriter(String filePath, ArrayList<T> List) throws IOException {
		Path file = Paths.get(filePath);
		Files.deleteIfExists(file);
		Files.write(file, "".getBytes(), StandardOpenOption.CREATE);
		ArrayList<String> datas = new ArrayList<String>();
		 for( T data: List) {
			 datas.add(data.toString());
		 }
		 Files.write(file, 
	                String.format("%s\n\n\n", datas.stream()
	                                    .collect(Collectors.joining("\n\n\n"))).getBytes(),
	                            StandardOpenOption.APPEND);
	}
}
