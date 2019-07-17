package com.example.fitmvp.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import com.example.fitmvp.R;
import com.example.fitmvp.base.BaseActivity;
import com.example.fitmvp.contract.ChangePwContract;
import com.example.fitmvp.presenter.ChangePwPresenter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ChangePwActivity extends BaseActivity<ChangePwPresenter> implements ChangePwContract.View {
    @InjectView(R.id.change_phone)
    EditText inputPhone;
    @InjectView(R.id.change_pwd)
    EditText changePw;
    @InjectView(R.id.change_pwd_again)
    EditText pwAgain;
    @InjectView(R.id.button_change)
    FloatingActionButton change;

    @Override
    protected void setBar(){
        ActionBar actionbar = getSupportActionBar();
        //显示返回箭头默认是不显示的
        actionbar.setDisplayHomeAsUpEnabled(true);
        //显示左侧的返回箭头，并且返回箭头和title一直设置返回箭头才能显示
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setDisplayUseLogoEnabled(true);
        //显示标题
        actionbar.setDisplayShowTitleEnabled(true);
        actionbar.setTitle("修改密码");
    }

    protected ChangePwPresenter loadPresenter() {
        return new ChangePwPresenter();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initListener() {
        change.setOnClickListener(this);
    }

    @Override
    protected void initView() {
        ButterKnife.inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.change_pw;
    }

    @Override
    protected void otherViewClick(View view) {
        mPresenter.changePw(getAccount(), getPassword());
    }

    @Override
    public String getAccount() {
        return inputPhone.getText().toString().trim();
    }

    @Override
    public String getPassword() {
        return changePw.getText().toString().trim();
    }

    @Override
    public String getPwdAgain() {
        return pwAgain.getText().toString().trim();
    }

    @Override
    public Boolean check(){
        Boolean flag = true;
        if (TextUtils.isEmpty(getAccount())) {
            inputPhone.setError("手机号不能为空");
            flag = false;
        }
        else if (TextUtils.isEmpty(getPassword())) {
            changePw.setError("密码不能为空");
            flag = false;
        }
        else if(TextUtils.isEmpty(getPwdAgain())){
            pwAgain.setError("请确认密码");
            flag = false;
        }
        else if(!getPassword().equals(getPwdAgain())){
            pwAgain.setError("两次密码不一致，请确认密码");
            flag = false;
        }
        return flag;
    }

    @Override
    public void changeSuccess(String str){
        AlertDialog.Builder builder  = new AlertDialog.Builder(ChangePwActivity.this);
        builder.setTitle("提示" ) ;
        builder.setMessage(str) ;
        builder.setPositiveButton("是" ,  new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(ChangePwActivity.this, LoginActivity.class);
                startActivity(intent);
                ChangePwActivity.this.finish();
            }});
        builder.show();
    }
    @Override
    public void changeFail(String title, String msg){
        AlertDialog.Builder builder  = new AlertDialog.Builder(ChangePwActivity.this);
        builder.setTitle(title) ;
        builder.setMessage(msg) ;
        builder.setPositiveButton("是" ,  null );
        builder.show();
    }
}