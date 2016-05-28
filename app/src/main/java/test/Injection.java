package test;

import android.content.Context;

import com.mrz.searchposts.data.SPRepository;

/**
 * Created by zhengpeng on 2016/5/27.
 */
public class Injection {

    public static SPRepository provideTasksRepository( Context context) {
        return SPRepository.getInstance(null,
                null);
    }
}
