package JAM;

import java.io.IOException;

public class JAMIOException extends Exception {

    public JAMIOException() {
        super("JAM IO Exception");
    }

    public JAMIOException(IOException ioexception) {
        super(ioexception); 
    }
}
