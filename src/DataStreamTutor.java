import static org.junit.Assert.*;

import java.io.*;

import org.junit.Before;
import org.junit.Test;

public class DataStreamTutor{
    private static final String FILES_TEST_PATH = "test.txt";
    private static final String TEST_LINE = "test line";

    /**
     * Writes a string TEST_LINE to the file FILES_TEST_PATH, using
     * method writeUTF of class DataOutputStream.
     * Also use BufferedOutputStream for buffering.
     * Then close the stream.
     */
    public void writeToFile() {
        try(FileOutputStream fos = new FileOutputStream(new File(FILES_TEST_PATH))){
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            DataOutputStream dos = new DataOutputStream(bos);

            dos.writeUTF(TEST_LINE);

            dos.close();
            bos.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Reads a line from a file using the method readUTF and returns it.
     * @return
     */
    public String readFromFile() {
        try(FileInputStream fis = new FileInputStream(new File(FILES_TEST_PATH))){
            BufferedInputStream bis = new BufferedInputStream(fis);
            DataInputStream dis = new DataInputStream(bis);

            String text = dis.readUTF();

            dis.close();
            bis.close();
            return text;
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void testStream() {
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
