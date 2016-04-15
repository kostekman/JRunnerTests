import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;



public class RandomAccessTutor{

    private static final String FILES_TEST_PATH = "test.txt";

    public  static void randomAccessWrite() {
        try {
            RandomAccessFile file = new RandomAccessFile(FILES_TEST_PATH, "rw");
            file.writeInt(10);
            char[] message = "test line".toCharArray();
            for(char c : message){
                file.writeChar(c);
            }
        } catch(IOException e){
            e.printStackTrace();
        }
        /**
         * The method should open the file RandomAccessFile on path FILES_TEST_PATH,
         * skip the number 10 and the word "test" (without reading them),
         * jump to the fifth letter, read and return the string "line"
         */
    }
    public static String randomAccessRead() {
        try(RandomAccessFile file = new RandomAccessFile(FILES_TEST_PATH, "r");){
            byte[] array = new byte[8];
            file.skipBytes(2);
            file.read(array, 0, 8);
            System.out.println(array);
            return new String(array);
        }catch(IOException e){
            e.printStackTrace();
        }
        return null;
    }


    public static void createFile() {
        File f1 = new File(FILES_TEST_PATH);
        try {
            f1.delete();
            f1.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public static void main(String[] args) throws UnsupportedEncodingException  {
        createFile();
        randomAccessWrite();
        String s = null;
        int i=-1;
        try {
            RandomAccessFile f = new RandomAccessFile(FILES_TEST_PATH, "r");
            i = f.readInt();
            s = f.readLine();
            f.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
        s = new String(s.getBytes("UTF-8"), "Unicode");

        String read = randomAccessRead();
        read = new String(read.getBytes("UTF-8"), "Unicode");
        System.out.println(read);

    }
}
