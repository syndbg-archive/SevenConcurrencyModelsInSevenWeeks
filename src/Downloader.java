import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;

public class Downloader extends Thread {
    private InputStream in;
    private OutputStream out;
    private ArrayList<ProgressListener> listeners;

    public Downloader(URL url, String outputFilename) throws IOException {
        this.in = url.openConnection().getInputStream();
        this.out = new FileOutputStream(outputFilename);

        this.listeners = new ArrayList<ProgressListener>();
    }

    public synchronized void addListener(ProgressListener listener) {
        this.listeners.add(listener);
    }

    public synchronized void removeListener(ProgressListener listener) {
        this.listeners.remove(listener);
    }

    private synchronized void updateProgress(int n) {
        ArrayList<ProgressListener> listenersCopy;

        synchronized (this) {
            listenersCopy = (ArrayList<ProgressListener>)listeners.clone();
        }

        for (ProgressListener listener : listenersCopy) {
            listener.onProgress(n);
        }
    }

    public void run() {
        int n = 0;
        int total = 0;

        byte[] buffer = new byte[1024];

        try {
            while((n = in.read(buffer)) != -1) {
                out.write(buffer, 0, n);
                total += n;
                updateProgress(total);
            }

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
