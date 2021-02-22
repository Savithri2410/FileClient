package Util;

import StoreImpl.TextFileReadWrite;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MySocket {
    private static final int BUFSIZE=1024;
    Socket s = null;

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public MySocket(String host, int port, String path)
    {
        try {
            s = new Socket(host, port);
        }
        catch(ConnectException ce)
        {
            LOGGER.log(Level.SEVERE,"Unable to connect to server");
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in constructor of class MySocket");
        }
    }

    public void sendVariableToServer(String checksum) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(checksum);
        dos.flush();
    }

    public boolean getResponseFromServer()
    {
        try {
            DataInputStream dis = new DataInputStream(s.getInputStream());
            String response = dis.readUTF();
            if (response.equals("1"))
                return true;
            System.out.println(response);
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in getCheckSumResponseFromServer() of class MySocket");
            return false;
        }
        return false;
    }

    public void sendFile(String file, long fileSize) throws IOException {
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());
        FileInputStream fis = new FileInputStream(file);
        byte[] buffer = new byte[BUFSIZE];
        int read;
        int totalRead = 0;
        int remaining = (int)fileSize;
        while((read = fis.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
            totalRead += read;
            remaining -= read;
            System.out.println("read " + totalRead + " bytes.");
            dos.write(buffer, 0, read);
        }
        System.out.println("Sending file");
        dos.flush();
        dos.close();
        fis.close();
    }
}
