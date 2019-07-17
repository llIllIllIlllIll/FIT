package com.example.fitmvp.presenter;

import android.text.TextUtils;

import com.example.fitmvp.base.BasePresenter;
import com.example.fitmvp.contract.LoginContract;
import com.example.fitmvp.model.LoginModel;
import com.example.fitmvp.mvp.IModel;
import com.example.fitmvp.utils.LogUtils;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.LoginActivity;

import java.util.HashMap;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

public class LoginPresenter extends BasePresenter<LoginActivity> implements LoginContract.Presenter {
    @Override
    public HashMap<String, IModel> getiModelMap() {
        return loadModelMap(new LoginModel());
    }

    @Override
    public HashMap<String, IModel> loadModelMap(IModel... models) {
        HashMap<String, IModel> map = new HashMap<>();
        map.put("login", models[0]);
        return map;
    }

    public boolean checkNull() {
        boolean isNull = false;
        if (TextUtils.isEmpty(getIView().getAccount())) {
            getIView().setAccountError("账号不能为空");
            isNull = true;
        }
        else if (TextUtils.isEmpty(getIView().getPassword())) {
            getIView().setPwError("密码不能为空");
            isNull = true;
        }
        return isNull;
    }

    @Override
    public void login(final String account,final String password){
        if (!checkNull()) {
            final LoginModel loginModel = (LoginModel) getiModelMap().get("login");
            loginModel.login(account, password, new LoginContract.Model.InfoHint() {
                @Override
                public void successInfo() {
                    // 登录成功后在JMessage中也进行登录
                    JMessageClient.login(account, password, new BasicCallback() {
                        @Override
                        public void gotResult(int responseCode, String responseMessage) {
                            if (responseCode == 0){
                                // 登录成功，在本地保存用户信息
                                loginModel.saveUser();
                                // 页面跳转
                                ToastUtil.setToast("登录成功");
                                getIView().loginSuccess();
                            }
                            else{
                                getIView().loginFail("登录失败",responseMessage);
                            }
                        }
                    });
                }

                @Override
                public void errorInfo(String str){
                    getIView().loginFail("错误",str); // 错误
                }

                @Override
                public void failInfo(String str) {
                    LogUtils.e("LoginPresenter.failInfo", str);
                    getIView().loginFail("登录失败",str);  //失败
                }
            });
        }
    }
}
