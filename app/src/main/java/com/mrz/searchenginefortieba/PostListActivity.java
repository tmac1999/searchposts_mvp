package com.mrz.searchenginefortieba;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
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
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mrz.searchenginefortieba.data.dao.LinkDao;
import com.mrz.searchenginefortieba.utils.CommonUtils;
import com.mrz.searchenginefortieba.utils.SharePreferUtil;
import com.mrz.searchenginefortieba.utils.ToastUtils;
import com.mrz.searchenginefortieba.view.XListView;

public class PostListActivity extends SlidingFragmentActivity {
    protected static final String TAG = "PostListActivity";
    private ListView lv_postlist;
    private EditText et_searchbytitle;
    private Cursor cursor;
    private String editable;
    private ListView lv_listbehind;

    private TextView tv_empty;
    private TextView tv_empty_list;
    private SharePreferUtil sharePreferUtil;
    private View sv_postlist;
    private String[] items;
    private String title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTitle(title)
        sharePreferUtil = new SharePreferUtil(getApplicationContext());
        setContentView(R.layout.activity_postlist);
        initSlidingMenu();
        initViewAndData();

    }

    private void initViewAndData() {
        et_searchbytitle = (EditText) findViewById(R.id.et_searchbytitle);
        tv_empty = (TextView) findViewById(R.id.tv_empty);
        sv_postlist = findViewById(R.id.sv_postlist);
        et_searchbytitle.addTextChangedListener(getWatcher());
        lv_postlist = (ListView) findViewById(R.id.lv_postlist);
        refreshContent(sharePreferUtil.getLastBrowseTiebaName());
        lv_postlist.setOnItemClickListener(getOnItemClickListener());
        lv_postlist.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ViewGroup vGroup = (ViewGroup) view;
                TextView tv_url = (TextView) vGroup.getChildAt(0);
                CharSequence url = tv_url.getText();
                ClipboardManager myClipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData myClip;
                myClip = ClipData.newPlainText("text", PostDetailActivity.BASE_TIEBA_URL + url);
                myClipboard.setPrimaryClip(myClip);
                ToastUtils.longToast("链接已复制到剪贴板");
                return true;
            }
        });
        lv_postlist.setOnScrollListener(new XListView.OnXScrollListener() {
            @Override
            public void onXScrolling(View view) {

            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                setTitle(title + ":" + firstVisibleItem + "+" + visibleItemCount);
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner_time);
        items = getResources().getStringArray(R.array.spinnername);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, items));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String order = parent.getItemAtPosition(position).toString();
//                if (TextUtils.isEmpty(editable)) {
//                    return;
//                }
                if (order.equals(items[0])) {//升序
                    cursor = LinkDao.queryByTitleFromSpecifiedTiebaAscByTime(PostListActivity.this, editable, tiebaName);
                    lv_postlist.setAdapter(new SCursorAdapter(
                            PostListActivity.this, cursor));
                } else {
                    //降序
                    cursor = LinkDao.queryByTitleFromSpecifiedTiebaDescByTime(PostListActivity.this, editable, tiebaName);
                    lv_postlist.setAdapter(new SCursorAdapter(
                            PostListActivity.this, cursor));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private OnItemClickListener getOnItemClickListener() {
        return new OnItemClickListener() {
            /**
             * 每次 都根据
             */
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vGroup = (ViewGroup) view;
                TextView tv_url = (TextView) vGroup.getChildAt(0);
                CharSequence url = tv_url.getText();
                Log.i(TAG, "==position==" + position);
                Intent intent = new Intent(PostListActivity.this,
                        PostDetailActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        };
    }

    private TextWatcher getWatcher() {
        return new TextWatcher() {

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
                cursor = LinkDao.queryByTitleFromSpecifiedTieba(PostListActivity.this, editable, tiebaName);
                lv_postlist.setAdapter(new SCursorAdapter(
                        PostListActivity.this, cursor));
                // lv_postlist.requestLayout();
            }
        };
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
        tv_empty_list = (TextView) findViewById(R.id.tv_empty_list);
        Cursor allTieba = LinkDao.getAllTieba(PostListActivity.this);
        refreshBehindUI(allTieba);
        lv_listbehind.setAdapter(new BehindListAdapter(PostListActivity.this, allTieba));
        lv_listbehind.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ViewGroup vGroup = (ViewGroup) view;
                TextView tv = (TextView) vGroup.getChildAt(0);
                if (tv != null) {
                    String tiebaName = tv.getText().toString();
                    Log.i(TAG, "tiebaName" + tiebaName);
                    refreshContent(tiebaName);
                    sharePreferUtil.setLastBrowseTiebaName(tiebaName);
                }
            }
        });
        lv_listbehind.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong) {
                TextView localTextView = (TextView) ((ViewGroup) paramView).getChildAt(0);
                if (localTextView != null) {
                    String str = localTextView.getText().toString();
                    Log.i("PostListActivity", "tiebaName" + str);
                    PostListActivity.this.showDeleteDialog(str);
                }
                return true;
            }
        });
    }

    private void refreshBehindUI(Cursor allTieba) {
        if (allTieba.getCount() < 1) {
            tv_empty_list.setVisibility(View.VISIBLE);
            lv_listbehind.setVisibility(View.GONE);
        } else {
            tv_empty_list.setVisibility(View.GONE);
            lv_listbehind.setVisibility(View.VISIBLE);
        }
    }

    String tiebaName;

    private void refreshContent(String tiebaName) {
        if (tiebaName == null) {
            tv_empty.setVisibility(View.VISIBLE);
            sv_postlist.setVisibility(View.GONE);
        } else {
            if (LinkDao.isExistInTiebaList(getApplicationContext(), tiebaName)) {
                tv_empty.setVisibility(View.GONE);
                sv_postlist.setVisibility(View.VISIBLE);
                Cursor allLinkByName = LinkDao.getAllLinkByName(PostListActivity.this, tiebaName);
                lv_postlist.setAdapter(new SCursorAdapter(PostListActivity.this, allLinkByName));
                title = tiebaName + "(" + allLinkByName.getCount() + ")";
                this.tiebaName = tiebaName;
                PostListActivity.this.setTitle(title);
            } else {
                tv_empty.setVisibility(View.VISIBLE);
                sv_postlist.setVisibility(View.GONE);
            }


        }
    }

    private void showDeleteDialog(final String tiebaName) {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("确定将删除此贴吧数据");
        localBuilder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                paramDialogInterface.dismiss();
            }
        });
        localBuilder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                LinkDao.deleteTableByTiebaName(PostListActivity.this, tiebaName);
                Cursor localCursor = LinkDao.getAllTieba(PostListActivity.this);
                refreshBehindUI(localCursor);
                refreshContent(tiebaName);
                PostListActivity.this.lv_listbehind.setAdapter(new PostListActivity.BehindListAdapter(PostListActivity.this, localCursor));
            }
        });
        localBuilder.create().show();
    }

    class BehindListAdapter extends CursorAdapter {

        public BehindListAdapter(Context context, Cursor c) {
            super(context, c);
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
