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
public class CharacterNotFoundException extends ArmoryException {

    public CharacterNotFoundException() {
        super();
    }

    public CharacterNotFoundException(String s) {
        super(s);
    }

    public CharacterNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public CharacterNotFoundException(Throwable throwable) {
        super(throwable);
    }
}
