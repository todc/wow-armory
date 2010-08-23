package com.todc.wgrarmory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class UnknownArmoryException extends ArmoryException {

    public UnknownArmoryException() {
        super();
    }

    public UnknownArmoryException(String s) {
        super(s);
    }

    public UnknownArmoryException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnknownArmoryException(Throwable throwable) {
        super(throwable);
    }
}
