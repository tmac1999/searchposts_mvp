package com.mrz.searchposts.engine;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mrz.searchposts.data.db.DBHelper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SearchEngine {
	private static final String TAG = "SearchEngine";

	public static void article(String index, String searchPrefix,
			SQLiteDatabase db) {
		String sqlString = "insert into link(url,title,author,posttime,replycount) values(?,?,?,?,?)";
		Object[] objs = new Object[5];
		Document doc;
		String url = searchPrefix + index;
		Log.i(TAG, url);
		// String url =
		// "http://tieba.baidu.com/f?kw=%E5%8C%97%E4%BA%AC%E8%88%AA%E7%A9%BA%E8%88%AA%E5%A4%A9%E5%A4%A7%E5%AD%A6&ie=utf-8&pn="+index;
		try {
			Connection connect = Jsoup.connect(url);
			String arg0 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";
			connect.userAgent(arg0);
			doc = connect.get();
			Elements posts = doc.getElementsByAttributeValue("class",
					"j_th_tit");
			Elements authorElements = doc.getElementsByAttributeValue("class",
					"tb_icon_author");
			Elements posttimeElements = doc.getElementsByAttributeValue(
					"class", "pull-right is_show_create_time");
			Elements replayCountElements = doc.getElementsByAttributeValue(
					"class", "threadlist_rep_num center_text");
			for (int i = 0; i <posts.size(); i++) {
				Elements links = posts.get(i).getElementsByTag("a");
				for (Element link : links) {
					String linkHref = link.attr("href");

					String linkText = link.text().trim();
					objs[0] = linkHref;
					objs[1] = linkText;
				}
				String author = null;
				String posttime = null;
				String replyCount = null;
				try {
					author = authorElements.get(i).getElementsByTag("span").text();
	            	posttime = posttimeElements.get(i).getElementsByTag("span").text();
	            	replyCount = replayCountElements.get(i).getElementsByTag("span").text();
				} catch (Exception e) {
					Log.e(TAG, "角标越界，可能造成作者、回复数、发帖时间与Post 不一致~");
				}
				
            	objs[2] = author;
            	objs[3] = posttime;
            	objs[4] = replyCount;
            	
            	db.execSQL(sqlString, objs);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void articleByName(String index, String searchPrefix,
			SQLiteDatabase db,String TiebaName) {
		String sqlString = "insert into "+TiebaName+"(url,title,author,posttime,replycount) values(?,?,?,?,?)";
		Object[] objs = new Object[5];
		Document doc;
		String url = searchPrefix + index;
		Log.i(TAG, url);
		// String url =
		// "http://tieba.baidu.com/f?kw=%E5%8C%97%E4%BA%AC%E8%88%AA%E7%A9%BA%E8%88%AA%E5%A4%A9%E5%A4%A7%E5%AD%A6&ie=utf-8&pn="+index;
		try {
			Connection connect = Jsoup.connect(url);
			String arg0 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";
			connect.userAgent(arg0);
			doc = connect.get();
			Elements posts = doc.getElementsByAttributeValue("class",
					"j_th_tit");
			Elements authorElements = doc.getElementsByAttributeValue("class",
					"tb_icon_author");
			Elements posttimeElements = doc.getElementsByAttributeValue(
					"class", "pull-right is_show_create_time");
			Elements replayCountElements = doc.getElementsByAttributeValue(
					"class", "threadlist_rep_num center_text");
			for (int i = 0; i <posts.size(); i++) {
				Elements links = posts.get(i).getElementsByTag("a");
				for (Element link : links) {
					String linkHref = link.attr("href");

					String linkText = link.text().trim();
					objs[0] = linkHref;
					objs[1] = linkText;
				}
				String author = null;
				String posttime = null;
				String replyCount = null;
				try {
					author = authorElements.get(i).getElementsByTag("span").text();
	            	posttime = posttimeElements.get(i).getElementsByTag("span").text();
	            	replyCount = replayCountElements.get(i).getElementsByTag("span").text();
				} catch (Exception e) {
					e.printStackTrace();
					Log.e(TAG, "角标越界，可能造成作者、回复数、发帖时间与Post 不一致~");
				}
				
            	objs[2] = author;
            	objs[3] = posttime;
            	objs[4] = replyCount;
            	
            	db.execSQL(sqlString, objs);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static int getPostCount(String searchPrefix, SQLiteDatabase db) {
		Document doc;
		String url = searchPrefix + "0";
		Log.i(TAG, url);
		ArrayList<String> list = new ArrayList<String>();
		// String url =
		// "http://tieba.baidu.com/f?kw=%E5%8C%97%E4%BA%AC%E8%88%AA%E7%A9%BA%E8%88%AA%E5%A4%A9%E5%A4%A7%E5%AD%A6&ie=utf-8&pn="+index;
		try {
			Connection connect = Jsoup.connect(url);
			String arg0 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";
			connect.userAgent(arg0);
			doc = connect.get();
			//共有主题数<span class="red">7992</span>个，贴子数<span class="red">164518</span>篇
			// 共有主题数<span class="red_text">7617</span>个，贴子数<span class="red_text">254304</span>篇

			Elements ListDiv = doc.getElementsByAttributeValue("class",
					"red_text");
			if (ListDiv.isEmpty()) {
				ListDiv = doc.getElementsByAttributeValue("class",
						"red");
			}
			for (Element element : ListDiv) {
				Elements links = element.getElementsByTag("span");
				for (Element link : links) {
					String linkText = link.text().trim();
					list.add(linkText);
					System.out.println("linkText" + linkText);
				}
			}
			if (list.size() == 0) {
				return -1;
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return Integer.parseInt(list.get(0));
	}
}
