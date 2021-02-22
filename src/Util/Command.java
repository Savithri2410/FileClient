package Util;

import Store.IFileOperations;
import Store.IReadWrite;
import StoreImpl.TextFileOperations;
import StoreImpl.TextFileReadWrite;

import java.util.Arrays;
import java.util.Locale;

public class Command {
    String[] myArgs;

    public Command(String[] args)
    {
        myArgs = new String[args.length];
        myArgs = Arrays.copyOf(args, args.length);
    }

    public void delegate()
    {
        String cmd = myArgs[0].toLowerCase(Locale.ROOT);
        String path;
        IReadWrite irw = new TextFileReadWrite();
        IFileOperations ifo = new TextFileOperations();

        switch(cmd)
        {
            case "add":
            {
                if(myArgs[1] != null) {
                    path = myArgs[1];
                    irw.init(cmd, path);
                    irw.writeToServer(cmd, path);
                }
                else
                    System.out.println(" Provide filename");
                break;
            }
            case "rm":
            {
                if(myArgs[1] != null) {
                    path = myArgs[1];
                    irw.init(cmd, path);
                    irw.removeFromServer(cmd, path);
                }
                else
                    System.out.println(" Provide filename");
                break;
            }
            case "ls":
            {
                ifo.init( cmd, null);
                ifo.getDirectoryListFromServer();
                break;
            }
            case "wc":
            {
                ifo.init( cmd, null);
                ifo.getWordCountFromServer();
                break;
            }
            case "freq-words":
            {
                ifo.init( cmd, null);
                ifo.getFreqWordsFromServer();
                break;
            }
            default:
            {
                System.out.println ("Invalid option");
                break;
            }

        }
    }

}
