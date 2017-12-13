package application;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Helper class to preserve console output when redirecting it to a file.
 * Example usage:
 * <pre>
 * {@code
 *  FileOutputStream file = new FileOutputStream("test.txt");
 * TeePrintStream tee = new TeePrintStream(file, System.out);
 * System.setOut(tee);
 *  }
 *  </pre>
 *  
 * @author Carlos Heuberger (https://stackoverflow.com/a/1994721/4036475)
 */

public class CopyPrintStream extends PrintStream {
    private final PrintStream second;

    public CopyPrintStream(OutputStream main, PrintStream second) {
        super(main);
        this.second = second;
    }

    /**
     * Closes the main stream. 
     * The second stream is just flushed but <b>not</b> closed.
     * @see java.io.PrintStream#close()
     */
    @Override
    public void close() {
        // just for documentation
        super.close();
    }

    @Override
    public void flush() {
        super.flush();
        second.flush();
    }

    @Override
    public void write(byte[] buf, int off, int len) {
        super.write(buf, off, len);
        second.write(buf, off, len);
    }

    @Override
    public void write(int b) {
        super.write(b);
        second.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
        super.write(b);
        second.write(b);
    }
}
