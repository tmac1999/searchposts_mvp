package com.mrz.searchposts;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mrz.searchposts.data.dao.LinkDao;
import com.mrz.searchposts.utils.CommonUtils;

public class PostListActivity extends SlidingFragmentActivity {
	protected static final String TAG = "PostListActivity";
	private ListView lv_postlist;
	private EditText et_searchbytitle;
	private Cursor cursor;
	private String editable;
    private ListView lv_listbehind;

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setTitle(title)
		setContentView(R.layout.activity_postlist);
		initSlidingMenu();
		
		et_searchbytitle = (EditText) findViewById(R.id.et_searchbytitle);
		et_searchbytitle.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				editable = s.toString();
				Log.i(TAG, "afterTextChanged" + editable);
				cursor = LinkDao.queryByTitleFromSpecifiedTieba(PostListActivity.this, editable,getTitle().toString());
				lv_postlist.setAdapter(new SCursorAdapter(
						PostListActivity.this, cursor));
				// lv_postlist.requestLayout();
			}
		});
		lv_postlist = (ListView) findViewById(R.id.lv_postlist);
		cursor = LinkDao.getAllLink(this);
		lv_postlist.setAdapter(new SCursorAdapter(this, cursor));
		lv_postlist.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 每次 都根据
			 */
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewGroup vGroup = (ViewGroup)view;
				TextView tv_url = (TextView) vGroup.getChildAt(0);
				CharSequence url = tv_url.getText();
				Log.i(TAG, "==position=="+position);
//				String first = cursor.getString(cursor
//						.getColumnIndex("_id"));
//				int firstPostId = Integer.parseInt(first);
//				if (firstPostId == (position + 1)) {
//					// 拿到对应行的url，进入新acitivity 并用webview打开url
//					url = cursor.getString(cursor.getColumnIndex("url"));
//					Log.i(TAG, "url_postfix:" + url);
//				}else {
//					while (cursor.moveToNext()) {
//
//						String postId_str = cursor.getString(cursor
//								.getColumnIndex("_id"));
//						int postId = Integer.parseInt(postId_str);
//						if (postId == (position + 1)) {
//							// 拿到对应行的url，进入新acitivity 并用webview打开url
//							url = cursor.getString(cursor.getColumnIndex("url"));
//							Log.i(TAG, "url_postfix:" + url);
//							break;
//						}
//					}
//				}
				
				Intent intent = new Intent(PostListActivity.this,
						PostDetailActivity.class);
				intent.putExtra("url", url);
				startActivity(intent);
			}
		});
	}

	private void initSlidingMenu() {
		setBehindContentView(R.layout.postlistbehind);
		SlidingMenu slidingMenu = getSlidingMenu();
		// 设置菜单模式
		slidingMenu.setMode(SlidingMenu.LEFT);// 只允许左侧菜单可以滑动
		// 设置触摸范围
		slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// 设置正文保留的宽度
		slidingMenu.setBehindOffset(CommonUtils.dp2px(this, 200));
        lv_listbehind = (ListView) findViewById(R.id.lv_listbehind);
		Cursor allTieba = LinkDao.getAllTieba(PostListActivity.this);
		lv_listbehind.setAdapter(new BehindListAdapter(PostListActivity.this,allTieba));
		lv_listbehind.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ViewGroup vGroup = (ViewGroup) view;
				TextView tv = (TextView) vGroup.getChildAt(0);
				if (tv!= null) {
					String tiebaName = tv.getText().toString();
					Log.i(TAG, "tiebaName"+tiebaName);
					Cursor allLinkByName = LinkDao.getAllLinkByName(PostListActivity.this, tiebaName);
					lv_postlist.setAdapter(new SCursorAdapter(PostListActivity.this, allLinkByName));
					PostListActivity.this.setTitle(tiebaName);
				}
			}
		});
        lv_listbehind.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
            {
                TextView localTextView = (TextView)((ViewGroup)paramView).getChildAt(0);
                if (localTextView != null)
                {
                    String str = localTextView.getText().toString();
                    Log.i("PostListActivity", "tiebaName" + str);
                    PostListActivity.this.showDeleteDialog(str);
                }
                return true;
            }
        });
	}
    private void showDeleteDialog(final String paramString)
    {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("确定将删除此贴吧数据");
        localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
                paramDialogInterface.dismiss();
            }
        });
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            public void onClick(DialogInterface paramDialogInterface, int paramInt)
            {
                LinkDao.deleteTableByTiebaName(PostListActivity.this, paramString);
                Cursor localCursor = LinkDao.getAllTieba(PostListActivity.this);
                PostListActivity.this.lv_listbehind.setAdapter(new PostListActivity.BehindListAdapter( PostListActivity.this, localCursor));
            }
        });
        localBuilder.create().show();
    }
	class BehindListAdapter extends CursorAdapter {

		public BehindListAdapter(Context context, Cursor c) {
			super(context, c);
			// TODO Auto-generated constructor stub
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = View.inflate(context, R.layout.item_listbehind, null);
			TextView tv_itembehind = (TextView) view.findViewById(R.id.tv_itembehind);
			tv_itembehind.setText(cursor.getString(cursor.getColumnIndex("name")));
			return view;
		}
//
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv_itembehind = (TextView) view.findViewById(R.id.tv_itembehind);
			tv_itembehind.setText(cursor.getString(cursor.getColumnIndex("name")));
		}
		
	}
	class SCursorAdapter extends CursorAdapter {

		public SCursorAdapter(Context context, Cursor c) {
			super(context, c);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = View.inflate(PostListActivity.this,
					R.layout.item_postlist, null);
			TextView tv_url = (TextView) view.findViewById(R.id.tv_url);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_author = (TextView) view.findViewById(R.id.tv_author);
			TextView tv_posttime = (TextView) view.findViewById(R.id.tv_posttime);
			TextView tv_replycount = (TextView) view.findViewById(R.id.tv_replycount);
			String url = cursor.getString(cursor.getColumnIndex("url"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String author = cursor.getString(cursor.getColumnIndex("author"));
			String posttime = cursor.getString(cursor.getColumnIndex("posttime"));
			String replycount = cursor.getString(cursor.getColumnIndex("replycount"));
			tv_url.setText(url);
			tv_title.setText(title);
			tv_author.setText(author);
			tv_posttime.setText(posttime);
			tv_replycount.setText(replycount);
			return view;
		}

		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			TextView tv_url = (TextView) view.findViewById(R.id.tv_url);
			TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
			TextView tv_author = (TextView) view.findViewById(R.id.tv_author);
			TextView tv_posttime = (TextView) view.findViewById(R.id.tv_posttime);
			TextView tv_replycount = (TextView) view.findViewById(R.id.tv_replycount);
			String url = cursor.getString(cursor.getColumnIndex("url"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String author = cursor.getString(cursor.getColumnIndex("author"));
			String posttime = cursor.getString(cursor.getColumnIndex("posttime"));
			String replycount = cursor.getString(cursor.getColumnIndex("replycount"));
			tv_url.setText(url);
			tv_title.setText(title);
			tv_author.setText(author);
			tv_posttime.setText(posttime);
			tv_replycount.setText(replycount);
		}

	}
}
