package com.mrz.searchenginefortieba.component.main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.mrz.searchenginefortieba.PostListActivity;
import com.mrz.searchenginefortieba.R;
import com.mrz.searchenginefortieba.component.communicate.SendPost.SendPostActivity;
import com.mrz.searchenginefortieba.component.communicate.SubjectList.SubjectListActivity;
import com.mrz.searchenginefortieba.component.feedback.FeedBackActivity;
import com.mrz.searchenginefortieba.component.login.LoginActivity;
import com.mrz.searchenginefortieba.component.me.MeActivity;
import com.mrz.searchenginefortieba.data.dao.LinkDao;
import com.mrz.searchenginefortieba.data.db.DBHelper;
import com.mrz.searchenginefortieba.engine.SearchEngine;
import com.mrz.searchenginefortieba.service.AVService;
import com.mrz.searchenginefortieba.session.UserSession;
import com.mrz.searchenginefortieba.utils.CommonUtils;
import com.mrz.searchenginefortieba.utils.TimeUtils;
import com.mrz.searchenginefortieba.utils.ToastUtils;
import com.mrz.searchenginefortieba.view.catloadingview.CatLoadingView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * version1.0    1.点击显示帖子 进入帖子详情，退回返回帖子列表（新activity） 2.数据库增加贴吧字段or 一个贴吧一张表？ 作者 发帖日期
 * 最后回复 日期？ 3.数据库去重复？利用url 相等？利用贴吧名字？同一贴吧再次 点击 搜索 则删除之前数据库贴吧内容（提示用户） 4.多线程
 * 抓取，看看效率 目前单线程 7min/1w主题数。。 10线程 3min/1w （2.5min/7500) 5.
 */

/**
 * version1.1    16.3.4 mainactivity-增加侧边栏--1.进入贴吧列表（默认显示上次退出的贴吧）。 2.增加关于。添加操作说明
 * 搜索建表时 增加进度条。提示不要进行其他操作？
 * webview 与 sildingmenu滑动冲突
 * <p/>
 * version2.0     16.6.15 （1）ui优化，侧边栏进入activity页面时为平移动画（2）增加用户体系（3）增加交流版 ，发帖功能 （4） 增加意见反馈
 * TODO 1.2 建表时提供取消功能（保留已建立数据or删除已建立数据） 增加测试用例  (360,应用宝，百度,小米商店，华为商店)
 *
 * @author mrz
 */
public class MainActivity extends SlidingFragmentActivity implements
        OnClickListener {

    private static final String URL_DEFAULT = "http://tieba.baidu.com";
    private static final String URL_PREFIX = "http://tieba.baidu.com/f?kw=";// http://tieba.baidu.com/f?kw=app%E5%A4%96%E5%8C%85

    protected static final String TAG = "MainActivity";
    private static final int THREADCOUNT = 15;// 搜索任务执行的线程数
    protected static final int SHOW_TOTALPAGECOUNT = 10;
    protected static final int GET_POSTCOUNTERROR = -1;
    private WebView wv_tieba;
    private Button btn_confirm;
    private EditText et_tiebaname;
    private Button btn_search;
    private String searchURL;
    private SQLiteDatabase db;
    private RelativeLayout relativeLayout;
    private TextView tv_progress;
    private Integer tiebaPageSum;
    /**
     * 搜索开始时间，用于估算总计耗时
     */
    private long startTime;
    private ExecutorService pool;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initBehindView();

        wv_tieba = (WebView) findViewById(R.id.wv_tieba);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        btn_search = (Button) findViewById(R.id.btn_search);
        et_tiebaname = (EditText) findViewById(R.id.et_tiebaname);
        tv_progress = (TextView) findViewById(R.id.tv_progress);
        btn_confirm.setOnClickListener(this);
        btn_search.setOnClickListener(this);
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(et_tiebaname.getWindowToken(), 0);
        String arg0 = "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0)";
        WebSettings settings = wv_tieba.getSettings();
        settings.setUserAgentString(arg0);
        wv_tieba.loadUrl(URL_DEFAULT);
        settings.setBuiltInZoomControls(true);
        settings.setDisplayZoomControls(true);
        wv_tieba.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, url);
                return super.shouldOverrideUrlLoading(view, url);
            }

//			@Override
//			public WebResourceResponse shouldInterceptRequest(WebView view,
//					WebResourceRequest request) {
//				Log.i(TAG, request.getMethod() + request.getUrl());
//				return super.shouldInterceptRequest(view, request);
//			}
        });

        createDatabase();

    }

    private void initBehindView() {
        setBehindContentView(R.layout.mainactivity_behind);
        SlidingMenu slidingMenu = getSlidingMenu();
        // 设置菜单模式
        slidingMenu.setMode(SlidingMenu.LEFT);// 只允许左侧菜单可以滑动
        // 设置触摸范围
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置正文保留的宽度
        slidingMenu.setBehindOffset(CommonUtils.dp2px(this, 200));
        LinearLayout ll_jumptolist = (LinearLayout) findViewById(R.id.ll_jumptolist);
        LinearLayout ll_about = (LinearLayout) findViewById(R.id.ll_about);
        LinearLayout ll_login = (LinearLayout) findViewById(R.id.ll_login);
        LinearLayout ll_feedback = (LinearLayout) findViewById(R.id.ll_feedback);
        LinearLayout ll_communicate = (LinearLayout) findViewById(R.id.ll_communicate);
        LinearLayout ll_communicate_area = (LinearLayout) findViewById(R.id.ll_communicate_area);
        LinearLayout ll_me = (LinearLayout) findViewById(R.id.ll_me);
        ll_jumptolist.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PostListActivity.class));
            }
        });
        ll_about.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                showCaption();
            }
        });
        ll_login.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        ll_feedback.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedBackActivity.class));
            }
        });
        ll_communicate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SendPostActivity.class));
            }
        });
        ll_communicate_area.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SubjectListActivity.class));
            }
        });
        ll_me.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MeActivity.class));
            }
        });
    }

    private void createDatabase() {
        DBHelper helper = new DBHelper(this);
        db = helper.getWritableDatabase();

    }

    private void showCaption() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this,
                R.layout.popupwindow, null);
        builder.setView(view);
        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_confirm:
                tiebaName = et_tiebaname.getText().toString();
                if (TextUtils.isEmpty(tiebaName)) {
                    Toast.makeText(getApplicationContext(), "贴吧名字不能为空", Toast.LENGTH_LONG).show();
                } else {
                    encodeTiebaName = Uri.encode(tiebaName);
                    Log.i(TAG, encodeTiebaName);
                    wv_tieba.loadUrl(URL_PREFIX + encodeTiebaName);
                    searchURL = URL_PREFIX + encodeTiebaName + "&ie=utf-8&pn=";
                    new Thread() {
                        public void run() {
                            // Returns the double conversion of the most negative
                            // (closest to negative
                            // infinity) integer value which is greater than the
                            // argument.
                            if (SearchEngine.getPostCount(searchURL, db) == -1) {
                                handler.sendEmptyMessage(GET_POSTCOUNTERROR);
                                return;
                            }
                            count = (int) Math.ceil(SearchEngine.getPostCount(
                                    searchURL, db) / 50.0);
                            Log.i(TAG, "贴吧一共有" + count + "页");
                            Message msg = Message.obtain();
                            msg.what = SHOW_TOTALPAGECOUNT;
                            msg.obj = count;
                            handler.sendMessage(msg);
                        }
                    }.start();

                }

                break;
            case R.id.btn_search:
                if (TextUtils.isEmpty(searchURL)
                        || TextUtils.isEmpty(encodeTiebaName)) {
                    Toast.makeText(getApplicationContext(), "贴吧名字不能为空或者还没进入贴吧", Toast.LENGTH_LONG)
                            .show();
                } else {// 都不为空
                    shouldCreateTiebaTable(encodeTiebaName, tiebaName);
                }
                break;
        }
    }

    private CatLoadingView  catLoadingView = new CatLoadingView();

    private void showProgressBar() {
        ToastUtils.showSingletonToast("开始创建数据库...在此期间请勿做任何操作（也做不了^_^）");
        tv_progress.setVisibility(View.VISIBLE);
        tv_progress.setText("页数" + count);
        catLoadingView.setCancelable(false);
        catLoadingView.show(getSupportFragmentManager(), "");
    }

    private void startSearchEngine(final String searchPrefix,
                                   final String encodeTiebaName, final String tiebaName) {
        updateUserData(tiebaName, null);
        startFillInTiebaTable(searchPrefix, tiebaName);
    }

    private void updateUserData(String tiebaName, String costTime) {
        if (costTime != null) {
            //说明是贴吧表创建完成 的数据上传，此时只需更新costTime字段
            AVService.updateUserSearchData(costTime, AVService.getUserSearchDataObjectId());
        } else {
            AVService.insertUserSearchData(UserSession.getUserID(), UserSession.getLoginUserName(), tiebaName, TimeUtils.getCurrentTime(), costTime, tiebaPageSum);
        }
    }

    private void startFillInTiebaTable(final String searchPrefix, final String tiebaName) {
        showProgressBar();
        progressPaperCount = 0;
        startTime = System.currentTimeMillis();
        if (count > 9) {// 大于10页开启线程池搜索
            pool = Executors
                    .newFixedThreadPool(THREADCOUNT + 1);

            final int baseErrand = count / THREADCOUNT;// e.g. 154页贴吧。
            // baseErrand 为15。
            int currentErrand = 0;
            for (int j = 0; j < THREADCOUNT; j++) {

                Log.i(TAG, "线程" + j + "开始工作，现在执行搜索第" + currentErrand + "页");

                int lastErrand = currentErrand + baseErrand;

                pool.execute(new SearchTask(currentErrand, lastErrand,
                        tiebaName));

                currentErrand += baseErrand;

            }
            // 比如 有25页，以上搜索完了 0-19 即1-20页。还有21-25页需求搜索
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    for (int i = THREADCOUNT * baseErrand; i < count; i++) {
                        Log.i(TAG, "线程" + THREADCOUNT + "开始工作，现在执行搜索第" + i
                                + "页");
                        String index = i * 50 + "";
                        SearchEngine.articleByName(index, searchPrefix, db,
                                tiebaName);
                        message = new Message();
                        message.obj = 1;
                        handler.sendMessage(message);
                    }
                }
            });
        } else {
            // 子线程开启搜索
            Thread thread = new Thread() {
                public void run() {
                    for (int i = 0; i < count; i++) {
                        String index = i * 50 + "";
                        SearchEngine.articleByName(index, searchPrefix, db,
                                tiebaName);
                        message = new Message();
                        message.obj = 1;
                        handler.sendMessage(message);
                    }
                }
            };
            thread.start();
        }
    }

    /**
     * 查询数据库中是否已经存在该tieba的表(查询是否存在表，而不是tiebalist表中是否存在此列)。给出提示
     *
     * @param encodeTiebaName 1.不存在表，或者存在用户按下确定要删除重载)开启搜索
     *                        2.用户取消了搜索
     * @author mrz
     */
    @SuppressLint("NewApi")
    private void shouldCreateTiebaTable(final String encodeTiebaName, final String tiebaName) {
        if (tiebaPageSum > 5000 && UserSession.getLoginUserName() == null) {
            ToastUtils.longToast("这个贴吧太庞大，装进手机会撑爆哦~不怕？那登录了再来试试吧！");
            return;
        }
        // String sql =
        // "SELECT * FROM sqlite_master where type = 'table' and name = ?";
        Log.i(TAG, "encodeTiebaName:" + encodeTiebaName);
        String sql = "select name from sqlite_master where type ='table' and name = ?";
        Cursor cursor = db.rawQuery(sql, new String[]{tiebaName});
        if (cursor.moveToNext()) {
            String tableName = cursor.getString(0);
            if (tableName.equals(tiebaName)) {
                // 弹出提示框，提醒用户
                //  showDemoDialog();
                showDuplicateCreateDialog(encodeTiebaName, tiebaName);
            }
            Log.i(TAG, "cursor0" + tableName);
        } else {
            // 是一个新贴吧搜索请求
            /**
             *TODO 1.判断此贴吧页数是否过大，过大提醒用户会占用大量内存 2.创建新表,将新表信息插入 tiebaList表
             * 3.开始搜索插入新的数据
             */
            createNewTable(encodeTiebaName, tiebaName);
            startSearchEngine(searchURL, encodeTiebaName, tiebaName);
        }

    }

    private void showDuplicateCreateDialog(final String encodeTiebaName, final String tiebaName) {
        final Builder builder = new Builder(MainActivity.this);
        builder.setTitle("提示");
        builder.setMessage("您已经创建过此贴吧数据表，若确定将删除原表并重新建立新表");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ToastUtils.showSingletonToast("原" + tiebaName + "数据表已删除");
                LinkDao.deleteTableByTiebaName(getApplicationContext(), tiebaName);
                createNewTable(encodeTiebaName, tiebaName);
                ToastUtils.showSingletonToast("新" + tiebaName + "数据表已创建");
                startSearchEngine(searchURL, encodeTiebaName, tiebaName);

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void createNewTable(String encodeTiebaName, String tiebaName) {
        String sql1 = " CREATE TABLE IF NOT EXISTS "
                + tiebaName
                + " (_id integer primary key autoincrement, url varchar(30), title varchar(100), author varchar(50), posttime varchar(20), replycount varchar(10))";
        db.execSQL(sql1);

        String sql2 = "insert into tiebalist(name,encodename,createtime,isdelete) values(?,?,?,?)";
        db.execSQL(
                sql2,
                new String[]{tiebaName, encodeTiebaName,
                        TimeUtils.getCurrentTime(), "false"});
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
            return super.dispatchTouchEvent(ev);
        }
        // 必不可少，否则所有的组件都不会有TouchEvent了
        if (getWindow().superDispatchTouchEvent(ev)) {
            return true;
        }
        return onTouchEvent(ev);
    }

    public boolean isShouldHideInput(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] leftTop = {0, 0};
            // 获取输入框当前的location位置
            v.getLocationInWindow(leftTop);
            int left = leftTop[0];
            int top = leftTop[1];
            int bottom = top + v.getHeight();
            int right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击的是输入框区域，保留点击EditText的事件
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    private void stopShowProgressBar() {
        catLoadingView.dismiss();
        ToastUtils.showSingletonToast("建表完成,请到数据表页面查看。");
    }

    int progressPaperCount = 0;
    private Handler handler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_TOTALPAGECOUNT:
                    tv_progress.setVisibility(View.VISIBLE);
                    tiebaPageSum = (Integer) msg.obj;
                    tv_progress.setText("贴吧一共有" + tiebaPageSum + "页，\n建表需" + tiebaPageSum * 3 / 1000.0F + "m空间，耗时约" + tiebaPageSum / 80 + "分钟");
                    break;
                case GET_POSTCOUNTERROR:
                    Toast.makeText(MainActivity.this, "查找贴吧页数出错", Toast.LENGTH_LONG).show();
                    break;
                default:
                    progressPaperCount += (Integer) msg.obj;
                    long l = System.currentTimeMillis();
                    long elapseTime = l - startTime;
                    String evaluateTotalTIme = TimeUtils.formatTimeMillis(elapseTime / progressPaperCount * count);
                    tv_progress.setText("当前进度：" + progressPaperCount + "/" + count + "预估时间：" + TimeUtils.formatTimeMillis(elapseTime) + "/" + evaluateTotalTIme);
//                    tv_progress.layout(tv_progress.getLeft(), 0, tv_progress.getRight(), tv_progress.getBottom());
//                    tv_progress.requestLayout();
                    if (progressPaperCount == MainActivity.this.count) {
                        updateUserData(tiebaName, evaluateTotalTIme);
                        stopShowProgressBar();
                    }
                    break;
            }
        }
    };
    private Message message;
    private int count;
    private String encodeTiebaName;
    private String tiebaName;

    @Override
    protected void onDestroy() {
        db.close();
        super.onDestroy();
    }

    public void showCancelDialog() {
        final Builder builder = new Builder(this);
        builder.setTitle("提示");
        builder.setMessage("正在疯狂加载数据中，确定要取消吗？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.create().dismiss();

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int unicode = 0x1F601;
                String emojiByUnicode = getEmojiByUnicode(unicode);

                ToastUtils.longToast("其实这个功能还没开发..."+emojiByUnicode+"下个版本，等我！");
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public String getEmojiByUnicode(int unicode){
        return new String(Character.toChars(unicode));
    }
    class SearchTask implements Runnable {
        private int currentErrand;
        private int lastErrand;
        private String tiebaName;

        public SearchTask(int currentErrand, int lastErrand, String tiebaName) {
            super();
            this.currentErrand = currentErrand;
            this.lastErrand = lastErrand;
            this.tiebaName = tiebaName;
        }

        @Override
        public void run() {
            for (int i = currentErrand; i < lastErrand; i++) {//
                String index = i * 50 + "";
                SearchEngine.articleByName(index, searchURL, db, tiebaName);
                Log.i(TAG, "Thread==" + Thread.currentThread().getName()
                        + "=============Search One Time ==================" + i);
                message = Message.obtain();
                message.obj = 1;
                handler.sendMessage(message);
            }
        }

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.in_righttoleft, R.anim.out_righttoleft);
    }
}
