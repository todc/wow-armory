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
