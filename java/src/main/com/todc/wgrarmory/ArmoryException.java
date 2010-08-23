package com.todc.wgrarmory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class ArmoryException extends Exception {

    public ArmoryException() {
        super();
    }

    public ArmoryException(String s) {
        super(s);
    }

    public ArmoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ArmoryException(Throwable throwable) {
        super(throwable);
    }
}
