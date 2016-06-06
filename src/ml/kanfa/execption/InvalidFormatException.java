package ml.kanfa.execption;

/**
 * @author Kanfa.
 */

public class InvalidFormatException extends Exception{

    public InvalidFormatException(String message){
        super(message);
    }

    @Override public void printStackTrace() {
        super.printStackTrace();
    }
}
