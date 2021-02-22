package Util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.security.MessageDigest;

public class CheckSumCalc {

    private static final int BUFF_SIZE = 1024;
    static byte[] partialHash = null;

    public static byte[] getChecksum( File file) throws IOException {
        try {
            RandomAccessFile afile = new RandomAccessFile(String.valueOf(file.toPath()), "r");
            MessageDigest hashSum = MessageDigest.getInstance("SHA-256");

            byte[] buffer = new byte[BUFF_SIZE];
            long read = 0;

            // calculate the hash of the whole file for the test
            long offset = file.length();
            int unitsize;
            while (read < offset) {
                unitsize = (int) (((offset - read) >= BUFF_SIZE) ? BUFF_SIZE : (offset - read));
                afile.read(buffer, 0, unitsize);

                hashSum.update(buffer, 0, unitsize);

                read += unitsize;
            }

            afile.close();
            partialHash = new byte[hashSum.getDigestLength()];
            partialHash = hashSum.digest();

            return partialHash;
        }
        catch (Exception e) {

        }
    return null;
    }

    /*private static MessageDigest getMessageDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }*/

}
