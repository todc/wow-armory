package com.todc.wgrarmory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class DownForMaintenanceException extends ArmoryException {

    public DownForMaintenanceException() {
        super();
    }

    public DownForMaintenanceException(String s) {
        super(s);
    }

    public DownForMaintenanceException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public DownForMaintenanceException(Throwable throwable) {
        super(throwable);
    }
}
