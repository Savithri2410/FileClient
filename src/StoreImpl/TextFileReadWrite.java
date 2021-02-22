package StoreImpl;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import Util.CheckSumCalc;
import Util.MySocket;

public class TextFileReadWrite implements Store.IReadWrite{
    private static final String HOST = "localhost";
    private static final int PORT = 1988;
    private static final String HEXES = "0123456789abcdef";

    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    MySocket mySocket;
    public void init(String cmd, String path)
    {
        try {
            mySocket = new MySocket(HOST, PORT,path );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Exception in init() of class MySocket, path = " + path);
            e.printStackTrace();
        }
    }

    private String getHex(byte[] bytes)
    {
        if(bytes.length ==0)
            return null;

        final StringBuilder hex = new StringBuilder(2 * bytes.length);
        for (final byte b : bytes) {
            hex.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt(b & 0x0F));
        }
        return hex.toString();
    }

    public void writeToServer(String cmd, String path){
        try {
                long fileSize = (new File(path)).length();
                if(fileSize > 0)
                {
                    String checkSumString = cmd + ":" + getHex(calculateCheckSum(path)) + ":" + path + ":" + (new File(path)).length();
                    mySocket.sendVariableToServer(checkSumString);
                    //if file does not exist in server, client sends the file
                    if (!mySocket.getResponseFromServer()) {
                        mySocket.sendFile(path, fileSize);
                        LOGGER.log(Level.INFO,"Sent file to server");
                    }
                    else
                        LOGGER.log(Level.INFO,"File already exists on server");
                }
                else
                    LOGGER.log(Level.INFO,"file is empty. Hence not sending file to server in writeToServer of class TextFileReadWrite");
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in writeToServer of class TextFileReadWrite");
            e.printStackTrace();
        }
    }

    public void removeFromServer(String cmd, String path){
        try {
            if(path != null) {
                LOGGER.log(Level.INFO,"Sent message to server to remove " + path);
                mySocket.sendVariableToServer(cmd + ":" + path);
            }
            else
                LOGGER.log(Level.INFO,"path of file to remove is not given in removeFromServer of class TextFileReadWrite");
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in removeFromServer of class TextFileReadWrite");
        }
    }

    public byte[] calculateCheckSum(String pathName)  {
        try {
            return CheckSumCalc.getChecksum(new File(pathName));
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in calculateCheckSum of class TextFileReadWrite");
            return null;
        }
    }
}
