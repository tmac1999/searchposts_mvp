package com.mrz.searchposts.component.communicate.SubjectList;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.mrz.searchposts.data.SPRepository;
import com.mrz.searchposts.data.bean.Post;
import com.mrz.searchposts.service.AVService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhengpeng on 2016/5/30.
 */
public class SubjectListPresenter implements SubjectListContract.Presenter {
    private SubjectListActivity subjectListActivity;

    public SubjectListPresenter(SPRepository spRepository, SubjectListActivity subjectListActivity) {
        this.subjectListActivity = subjectListActivity;

    }


    @Override
    public void getListFromNet(final int page) {

        subjectListActivity.showLoadingBar();
        FindCallback findCallback = new FindCallback<AVObject>() {

            private ArrayList<Post> postList = new ArrayList<Post>();

            @Override
            public void done(List<AVObject> list, AVException e) {
                if (page==1){
                    //说明是首次进入或者刷新页面（不是加载更多），此时清空数据
                    postList.clear();
                    subjectListActivity.resetPageNum();
                }
                if (e == null) {
                    postList = getPostList(list);
                    SubjectListAdapter subjectListAdapter = new SubjectListAdapter(postList, subjectListActivity);
                    subjectListActivity.showList(subjectListAdapter);
                    subjectListActivity.showRequestSuccessedUI();
                } else {
                    subjectListActivity.showRequestFailedUI();
                }
            }

            private ArrayList<Post> getPostList(List<AVObject> list) {
                for (AVObject object : list) {
                    Post post = new Post();
                    post.imgURL = (String) object.get("imgURL");
                    post.content = (String) object.get("content");
                    post.title = (String) object.get("title");
                    post.time = (String) object.get("time");
                    post.userName = (String) object.get("userName");
                    postList.add(post);
                }
                return postList;
            }
        };
        AVService.queryPostByPage(findCallback,page);
    }

    @Override
    public void getListFromLocal() {

    }

    @Override
    public void start() {

    }

}
