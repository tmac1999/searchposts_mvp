package com.mrz.searchenginefortieba.component.communicate.SubjectList;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.FindCallback;
import com.mrz.searchenginefortieba.data.SPRepository;
import com.mrz.searchenginefortieba.data.bean.Post;
import com.mrz.searchenginefortieba.service.AVService;

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

    private ArrayList<Post> postList = new ArrayList<Post>();
    private int currentPage;

    @Override
    public void getListFromNet(SubjectListContract.RequestType type) {

        subjectListActivity.showLoadingBar();
        if (type == SubjectListContract.RequestType.REFRESH) {
            //说明是首次进入或者刷新页面（不是加载更多），此时清空数据
            postList.clear();
            currentPage = 1;
        }else{
            currentPage++;
        }

        FindCallback findCallback = new FindCallback<AVObject>() {


            @Override
            public void done(List<AVObject> list, AVException e) {

                if (e == null) {
                    if (list.size()==0){
                        //说明没有更多数据
                        currentPage--;
                        //TODO 为空应该让activity感知到，同时设置loadmore 的textview 为没有更多。因此最好直接传递list给activity？ 待重构
                    }
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
        AVService.queryPostByPage(findCallback, currentPage);
    }

    @Override
    public void getListFromLocal() {

    }

    @Override
    public void start() {

    }

}
