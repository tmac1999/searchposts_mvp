package com.mrz.searchposts.data.dao;

import com.mrz.searchposts.data.db.DBHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

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
		DBHelper helper = new DBHelper(c);
		SQLiteDatabase db = helper.getReadableDatabase();
		String sql = "select * from tiebalist ";
		Cursor cursor = db.rawQuery(sql, null);
		return cursor;
		
	}
}
