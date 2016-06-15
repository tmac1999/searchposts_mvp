package com.mrz.searchposts.component.communicate.SubjectList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mrz.searchposts.R;
import com.mrz.searchposts.data.bean.Post;
import com.mrz.searchposts.utils.ImageLoaderCfg;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * Created by zhengpeng on 2016/6/15.
 */
public class SubjectListAdapter extends BaseAdapter {

    private ArrayList<Post> list;
    private Context c;

    public SubjectListAdapter(ArrayList<Post> list, Context c) {
        this.list = list;
        this.c = c;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    // End Of Content View Elements

    private Holder bindViews(View view) {
        Holder holder = new Holder();
        holder.iv_content = (ImageView) view.findViewById(R.id.iv_content);
        holder.tv_content = (TextView) view.findViewById(R.id.tv_content);
        holder.tv_author = (TextView) view.findViewById(R.id.tv_author);
        holder.tv_time = (TextView) view.findViewById(R.id.tv_time);
        holder.tv_title = (TextView) view.findViewById(R.id.tv_title);
        return holder;
    }

    class Holder {
        // Content View Elements

        private ImageView iv_content;
        private TextView tv_content;
        private TextView tv_author;
        private TextView tv_time;
        private TextView tv_title;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(c, R.layout.item_subject, null);
            ;
        }
        Holder holder = bindViews(convertView);
        Post post = list.get(position);
        ImageLoader.getInstance().displayImage(post.imgURL, holder.iv_content, ImageLoaderCfg.options);
        holder.tv_author.setText(post.userName);
        holder.tv_content.setText(post.content);
        holder.tv_time.setText(post.time);
        holder.tv_title.setText(post.title);
        return convertView;
    }
}
