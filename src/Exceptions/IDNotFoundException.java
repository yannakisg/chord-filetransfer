package Exceptions;

/**
 * thrown by create() if the node we try to create the ring from does not have an already declared IdKey
 * @author ChordFPG Team
 * @version 1.0
 */
public class IDNotFoundException extends Exception {

    public IDNotFoundException() {
        super();
    }

    public IDNotFoundException(String message) {
        super(message);
    }
}