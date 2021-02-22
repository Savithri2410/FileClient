package Store;

import java.security.MessageDigest;

public interface IReadWrite {
    void init(String cmd, String path);
    void writeToServer(String cmd, String path);
    void removeFromServer(String cmd, String path);
}
