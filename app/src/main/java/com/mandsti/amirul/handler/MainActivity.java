package com.mandsti.amirul.handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    public static final int START_PROGRESS = 100;
    public static final int UPDATE_COUNT = 101;

    ProgressBar progressBar;
    Button btnStart, btnStop;
    TextView textView;

    Handler mHandler;
    Thread mThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        textView = (TextView) findViewById(R.id.tv_show);
        progressBar.setMax(100);

        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Log.d("I", ":" + i);

                    progressBar.setProgress(i);

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Message message = new Message();
                    message.what = UPDATE_COUNT;
                    message.arg1 = i;

                    mHandler.sendMessage(message);
                }
            }
        });


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentProgress = progressBar.getProgress();

                mHandler.sendEmptyMessage(START_PROGRESS);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                if (msg.what == START_PROGRESS) {
                    mThread.start();
                } else if (msg.what == UPDATE_COUNT) {
                    textView.setText("Count" + msg.arg1);
                }
            }
        };
    }
}
