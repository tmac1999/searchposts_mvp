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
    public void getListFromNet() {
        subjectListActivity.showLoadingBar();
        FindCallback findCallback = new FindCallback<AVObject>() {

            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    ArrayList<Post> postList = getPostList(list);
                    SubjectListAdapter subjectListAdapter = new SubjectListAdapter(postList, subjectListActivity);
                    subjectListActivity.showList(subjectListAdapter);
                    subjectListActivity.showRequestSuccessedUI();
                } else {
                    subjectListActivity.showRequestFailedUI();
                }
            }

            private ArrayList<Post> getPostList(List<AVObject> list) {
                ArrayList<Post> posts = new ArrayList<Post>();
                for (AVObject object : list) {
                    Post post = new Post();
                    post.imgURL = (String) object.get("imgURL");
                    post.content = (String) object.get("content");
                    post.title = (String) object.get("title");
                    post.time = (String) object.get("time");
                    post.userName = (String) object.get("userName");
                    posts.add(post);
                }
                return posts;
            }
        };
        AVService.queryAllPost(findCallback);
    }

    @Override
    public void getListFromLocal() {

    }

    @Override
    public void start() {

    }

}
