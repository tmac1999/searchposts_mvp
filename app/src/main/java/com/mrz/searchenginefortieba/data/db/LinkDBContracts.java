package com.mrz.searchenginefortieba.data.db;

import android.provider.BaseColumns;

public class LinkDBContracts {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public LinkDBContracts() {
    }

    /* Inner class that defines the table contents */
public static abstract class Link implements BaseColumns {
    public static final String TABLE_NAME = "link";
    public static final String COLUMN_NAME_URL = "url";
    public static final String COLUMN_NAME_TITLE = "title";
    public static final String COLUMN_NAME_AUTHOR = "author";
    public static final String COLUMN_NAME_POSTTIME = "posttime";
    public static final String COLUMN_NAME_REPLYCOUNT = "replycount";
}

}
