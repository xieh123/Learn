package com.example.myapplication.util;

import com.example.myapplication.model.User;
import com.google.gson.Gson;

/**
 * Created by xieH on 2017/9/1 0001.
 */
public class UserUtils {

    private static User mUser;

    public static void saveUser(User user) {
        if (user == null)
            return;

        mUser = user;
        SPUtils.putString("user", new Gson().toJson(user).toString());
    }

    public synchronized static User getUser() {
        if (mUser == null) {
            mUser = GsonUtils.fromJson(SPUtils.getString("user", ""), User.class);
        }

        if (mUser == null) {
            mUser = new User();
        }
        return mUser;
    }

    public static boolean isLogin() {
        return getUser().getId() > 0;
    }

    public static void logout() {
        mUser = null;
        SPUtils.remove("user");
    }
}
