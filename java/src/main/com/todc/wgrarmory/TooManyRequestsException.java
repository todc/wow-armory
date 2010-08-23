package com.todc.wgrarmory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class TooManyRequestsException extends ArmoryException {

    public TooManyRequestsException() {
        super();
    }

    public TooManyRequestsException(String s) {
        super(s);
    }

    public TooManyRequestsException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public TooManyRequestsException(Throwable throwable) {
        super(throwable);
    }
}
