import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public interface ProgressListener {
    void onProgress(int current) throws NotImplementedException;
}
