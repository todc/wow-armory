/*
 * $Id$
 *
 * Copyright (c)2010, Tim O'Donnell. All Rights Reserved.
 *
 * This code may not be used or reproduced in part or in whole without
 * express written permission of Tim O'Donnell.
 */
package com.todc.wgrarmory;


/**
 * @author <a href="mailto:tim@timodonnell.com">Tim O'Donnell</a>
 */
public class TooManyRequestsException extends Exception {

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