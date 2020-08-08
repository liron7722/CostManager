package com.lironprojects.costmanager.Models;

/**
 * Combine all exception in app exception.
 *
 * @author Liron Revah and Or Ohana
 */
public class CostManagerException extends Exception {

    /**
     *
     * @param message The string contain the error accord
     * @param cause  The cause of the error accord
     * @see Exception
     */
    public CostManagerException(String message, Throwable cause){
        super(message, cause);
    }
}
