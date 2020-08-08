package com.lironprojects.costmanager.Models;

import android.content.Context;
import android.widget.Toast;


/**
 * Create toast on screen with the message send to the constructor.
 *
 * @author Liron Revah and Or Ohana
 */
public class Message {
    /**
     * The Toast will show for Toast.LENGTH_LONG time
     *
     * @param context Application Context
     * @param message The string to display on the screen
     * @see Toast
     */
    public static void message(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }
}