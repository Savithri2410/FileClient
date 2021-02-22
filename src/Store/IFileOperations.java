package Store;

public interface IFileOperations {
    void init(String cmd, String path);
    void getDirectoryListFromServer();
    void getWordCountFromServer();
    void getFreqWordsFromServer();


}
