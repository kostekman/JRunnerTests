import static org.junit.Assert.*;

import java.io.*;

import org.junit.Before;
import org.junit.Test;

public class FileStreamTutor {
    private static final String FILES_TEST_PATH = "test.txt";
    private static final String TEST_LINE = "test line";

    /**
     * Writes a string TEST_LINE to the file FILES_TEST_PATH ,
     * converting the string into array of byt  es.
     * For the writing, use the class FileOutputStream.
     */
    public void writeToFile() {
        try(FileOutputStream fos = new FileOutputStream(new File(FILES_TEST_PATH))){
            fos.write((byte)TEST_LINE.length());

            fos.write(TEST_LINE.getBytes());

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Reads a line from a file and returns it using FileInputStream.
     * @return
     */
    public String readFromFile() {
        try(FileInputStream fis = new FileInputStream(new File(FILES_TEST_PATH))){
            int length = fis.read();
            byte[] line = new byte[length];
            fis.read(line, 0, length);
            System.out.println(new String(line) + "|");
            return new String(line);

        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testFileStream() {
        writeToFile();
        String s = readFromFile();
        assertEquals(TEST_LINE, s);
    }

    @Before
    public void createFile() {
        File f1 = new File(FILES_TEST_PATH);
        try {
            f1.delete();
            f1.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

}