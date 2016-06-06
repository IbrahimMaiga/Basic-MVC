package execption;

/**
 * @author Ibrahim Maïga.
 */

public class InvalidFormatException extends Exception{

    public InvalidFormatException(String message){
        super(message);
    }

    @Override public void printStackTrace() {
        super.printStackTrace();
    }
}
