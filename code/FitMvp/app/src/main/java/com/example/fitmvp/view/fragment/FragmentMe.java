package com.example.fitmvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseFragment;
import com.example.fitmvp.contract.MeContract;
import com.example.fitmvp.mvp.IView;
import com.example.fitmvp.presenter.MePresenter;
import com.example.fitmvp.utils.ToastUtil;
import com.example.fitmvp.view.activity.LoginActivity;
import com.example.fitmvp.view.activity.SettingActivity;

import butterknife.ButterKnife;
public class FragmentMe extends Fragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.me, container, false);

public class FragmentMe extends BaseFragment<MePresenter> implements IView,View.OnClickListener {
   @Override
   protected Integer getLayoutId(){
       return R.layout.me;
   }

   @Override
   protected MePresenter loadPresenter() {
       return new MePresenter();
   }

   @Override
   protected void initData(){}

   @Override
   protected void initListener(){
       Button logout = ButterKnife.findById(view,R.id.button_logout);
       Button toSetting = ButterKnife.findById(view,R.id.button_setting);
       logout.setOnClickListener(this);
       toSetting.setOnClickListener(this);
   }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_logout:
                mPresenter.logout();
                break;
            case R.id.button_setting:
                toSetting(view);
        }
    }

    private void toSetting(View view){
        Intent intent = new Intent(getActivity(), SettingActivity.class);
        startActivity(intent);
    }

    public void toLogin(){
        ToastUtil.setToast("成功退出当前账号");
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
        // getActivity().finish();
    }
}