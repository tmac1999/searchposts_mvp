package com.mrz.searchposts.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;

import com.mrz.searchposts.data.db.DBHelper;

public class LinkDao {
	/**
	 * 1m 约 2w条记录 50w汉字
	 * 1g 约 2kw条记录 50kw汉字
	 * 李毅吧 主题数17651508个 数据量为0.9g
	 * 北京吧 主题数2928180个  数据量为148m
	 * 北航吧 主题数65909个数据量为 3.3m
	 * 
	 * 主题总数35亿+（更新中） 数据量约200g
	 * 留言总数646亿+（更新中）
	 * 400亿条tweet  约为10t
	 * @param c
	 * @return
	 *@author mrz
	 */
	public static Cursor getAllLink(Context c){
		DBHelper helper = new DBHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from link ";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}
	public static Cursor getAllLinkByName(Context c,String TiebaName){
		DBHelper helper = new DBHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from "+TiebaName;
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
	}

	public static Cursor queryByTitle(Context c,String title) {
		DBHelper helper = new DBHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from link where title like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+title+"%"});
		return cursor;
	}
	public static Cursor queryByTitleFromSpecifiedTieba(Context c,String title,String TiebaName) {
		DBHelper helper = new DBHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from "+TiebaName+" where title like ?";
		Cursor cursor = db.rawQuery(sql, new String[]{"%"+title+"%"});
		return cursor;
	}
	
	public static Cursor getAllTieba(Context c){
//		DBHelper helper = new DBHelper(c);
//		SQLiteDatabase db = helper.getReadableDatabase();
//		String sql = "select * from tiebalist ";
//		Cursor cursor = db.rawQuery(sql, null);
        trimTiebaList(c);
        return new DBHelper(c).getReadableDatabase().rawQuery("select * from tiebalist where isdelete = 'false' ", null);

	}
    private static void trimTiebaList(Context paramContext)
    {
        SQLiteDatabase localSQLiteDatabase = new DBHelper(paramContext).getReadableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select name from sqlite_master where type ='table' ", null);
        while (true)
        {
            if (!localCursor.moveToNext())
            {
                if (Build.VERSION.SDK_INT < 14)
                    localCursor.close();
                localSQLiteDatabase.close();
                return;
            }
            updateTiebaList(paramContext, localCursor.getString(localCursor.getColumnIndex("name")));
        }
    }

    public static void updateTiebaList(Context paramContext, String paramString)
    {
        SQLiteDatabase localSQLiteDatabase = new DBHelper(paramContext).getWritableDatabase();
        if (isExistInTiebaList(paramContext, paramString))
            localSQLiteDatabase.execSQL("update tiebalist set isdelete='false' where name = ?", new String[] { paramString });
        localSQLiteDatabase.close();
    }

    public static boolean isExistInTiebaList(Context paramContext, String paramString)
    {
        SQLiteDatabase localSQLiteDatabase = new DBHelper(paramContext).getWritableDatabase();
        if (localSQLiteDatabase.rawQuery("select name from tiebalist where name = ?", new String[] { paramString }).getCount() < 1)
        {
            localSQLiteDatabase.close();
            return false;
        }
        localSQLiteDatabase.close();
        return true;
    }

    public static void deleteTableByTiebaName(Context paramContext, String paramString)
    {
        SQLiteDatabase localSQLiteDatabase = new DBHelper(paramContext).getWritableDatabase();
        localSQLiteDatabase.execSQL("drop table " + paramString);
        localSQLiteDatabase.execSQL("update tiebalist set isdelete='true'");
        localSQLiteDatabase.close();
    }
}
