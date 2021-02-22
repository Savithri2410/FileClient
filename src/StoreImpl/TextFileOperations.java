package StoreImpl;

import Util.MySocket;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TextFileOperations implements Store.IFileOperations{

    MySocket mySocket;
    private static final String HOST = "localhost";
    private static final int PORT = 1988;
    private static final Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public void init(String cmd, String path)
    {
        try {
            mySocket = new MySocket(HOST, PORT,path );
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE,"Exception in init() of class TextFileOperations, path = " + path);
            e.printStackTrace();
        }
    }

    public void getDirectoryListFromServer()
    {
        try {
            mySocket.sendVariableToServer("ls");
            boolean temp = mySocket.getResponseFromServer();
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in getDirectoryListFromServer of class TextFileOperations");
        }
    }

    public void getWordCountFromServer()
    {
        try {
            mySocket.sendVariableToServer("wc");
            boolean temp = mySocket.getResponseFromServer();
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in getWordCountFromServer of class TextFileOperations");
        }
    }

    public void getFreqWordsFromServer()
    {
        try {
            mySocket.sendVariableToServer("freq-words");
            boolean temp = mySocket.getResponseFromServer();
        }
        catch(Exception e)
        {
            LOGGER.log(Level.SEVERE,"Exception in getFreqWordsFromServer of class TextFileOperations");
        }
    }


}
