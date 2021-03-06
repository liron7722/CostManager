package com.lironprojects.costmanager.DB;


/**
 * Class for storing strings name used in more than one place
 *
 * @author Liron Revah and Or Ohana
 */
public class Names {
    /* log Tag */
    public static final String logTAG = "CostManager";

    /* Tables */
    public static final String Profile_Table = "Profile";
    public static final String Transactions_Table = "Transactions";
    public static final String[] Table_Names = {Profile_Table, Transactions_Table};

    /* Profile Table */
    public static final String UID = "id";
    public static final String Name = "Name";
    public static final String Password = "Password";
    public static final String Email = "Email";
    protected static final String[] Profile_cols = {UID, Name, Password, Email};

    /* Transactions Table */
    public static final String TID = "tid";
    public static final String Date = "Date";
    public static final String Amount = "Amount";
    public static final String TName = "TName";
    public static final String Price = "Price";
    public static final String isIncome = "isIncome";
    public static final String Category = "Category";
    public static final String Currency = "Currency";
    public static final String Description = "Description";
    public static final String PaymentType = "PaymentType";
    public static final String isRepeat = "isRepeat";
    public static final String Repeat = "Repeat";
    protected static final String[] Transactions_cols = {UID, TID, Amount, TName, Price,
                                            isIncome, Category, Currency, Description, PaymentType};

    // keys for request json
    public static final String Command = "cmd";
    public static final String Table = "table";
    public static final String Data = "data";

    // keys for response json
    public static final String ResultMsg = "toast";
    public static final String newID = "newID";

}
