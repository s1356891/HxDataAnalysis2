package com.puhui.dataanalysis.hxdataanalysis;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.puhui.dataanalysis.hxdataanalysis.crashhandler.*;
import com.puhui.dataanalysis.hxdataanalysis.crashhandler.HttpUtil;

import java.io.FileNotFoundException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        processErrorReport();
    }

    private void processErrorReport(){
        HXLog.eForDeveloper(SPUtil.loadCrashState(this)+"-----------------------");
        if (SPUtil.loadCrashState(this)) {
            HXLog.eForDeveloper("弹对话框=======================");
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("温馨提示")
                    .setMessage("应用发生崩溃,是否提交崩溃日志以协助工程师解决")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                            try {


                                                HttpUtil.sendFile2ecc(MainActivity.this,System.currentTimeMillis()+"");

                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });
            builder.create().show();
            SPUtil.clearCrashState(this);
        }
    }

    public void onclick(View view) {
        HXAnalysis.onEvent(this, "21321321321");

    }


    public void onclick2(View view) {
        HXAnalysis.onEvent(this, "213312321321312312");
    }


    public void makecrash(View view) {
        int a = 10 / 0;
    }
}

