package pl.futurecollars.invoicing.db.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileService {
  public void writeLineToFile (Path path, String line) throws IOException {
    Files.write(path, (line + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
  }
  public List<String> readAllLines (Path path) throws IOException {
    return Files.readAllLines(path);
  }
}
