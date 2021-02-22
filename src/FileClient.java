import Store.IReadWrite;
import StoreImpl.TextFileReadWrite;
import Util.Command;
import Util.MySocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

import static java.lang.System.exit;

public class FileClient {

    public static void main(String[] args) {

        Command cmd = new Command(args);
        cmd.delegate();

    }
}
