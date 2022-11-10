package ucunix;

/**
 *
 */
public class NullConsole implements Console {

    @Override
    public void print(String output) {
    }

    @Override
    public void clear() {
    }

    @Override
    public String read() {
        return "";
    }
    
}
