package com.hugh.bmobtest;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import cn.bmob.push.BmobPush;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 *
 */
public class MainActivity extends Activity {

    private EditText mName, mFeedBack,m_query_only;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bmob.initialize(this, "c7330025f2dec763922760bebdee30de");

        BmobInstallation.getCurrentInstallation(this).save();
        BmobPush.startWork(this,"c7330025f2dec763922760bebdee30de");//push-sdk初始化
        init();
    }

    private void init() {
        mName = (EditText) findViewById(R.id.et_name);
        mFeedBack = (EditText) findViewById(R.id.et_feedback);
        m_query_only = (EditText) findViewById(R.id.et_query_only);
    }

    /**
     * @param v
     * 推送消息
     */
    public void push(View v){
        BmobPushManager push = new BmobPushManager(this);
        push.pushMessageAll("test");
    }

    /**
     * @param v
     * 向服务器提交数据
     */
    public void submit(View v) {
        String name = mName.getText().toString();
        String feedback = mFeedBack.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(feedback)) {
            return;
        }
        FeedBack feedBackObj = new FeedBack();
        feedBackObj.setName(name);
        feedBackObj.setFeedback(feedback);
        feedBackObj.save(this, new SaveListener() {
            @Override
            public void onSuccess() {
                Toast.makeText(MainActivity.this, "Submit Succeed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int i, String s) {
                Toast.makeText(MainActivity.this, "Submit Failed", Toast.LENGTH_SHORT).show();

            }
        });

    }


    /**
     * @param v
     * 查询单条记录
     */
    public void query_only(View v){
        String str = m_query_only.getText().toString();
        if (TextUtils.isEmpty(str)){
            return;
        }
        BmobQuery<FeedBack> query = new BmobQuery<>();
        query.addWhereEqualTo("name",str);//增加查询条件
        query.findObjects(this, new FindListener<FeedBack>() {
            @Override
            public void onSuccess(List<FeedBack> feedbacks) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("query");
                String str = "";
                for (FeedBack feedBack : feedbacks) {
                    str += feedBack.getName() +":"+ feedBack.getFeedback() + "\n";
                }

                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    /**
     * @param v
     * 查询全部数据
     */
    public void query(View v) {
        BmobQuery<FeedBack> query = new BmobQuery<>();
        query.findObjects(this, new FindListener<FeedBack>() {
            @Override
            public void onSuccess(List<FeedBack> feedbacks) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("query");
                String str = "";
                for (FeedBack feedBack : feedbacks) {
                    str += feedBack.getName() +":"+ feedBack.getFeedback() + "\n";
                }

                builder.setMessage(str);
                builder.create().show();
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


}
