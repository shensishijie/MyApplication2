package com.example.myapplication2.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.myapplication2.R;

import static android.view.KeyEvent.KEYCODE_BACK;
import static android.view.KeyEvent.KEYCODE_ENTER;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView webView;
    //搜索栏
    private EditText url;

    private Button search;

    private Button go_for;

    private Button go_next;

    private Button home;

    private Button refresh;

    private Button detail;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = (WebView) findViewById(R.id.web_view);
        url = (EditText) findViewById(R.id.url);
        search = (Button) findViewById(R.id.search);
        go_for = (Button) findViewById(R.id.go_for);
        go_next = (Button) findViewById(R.id.go_next);
        home = (Button) findViewById(R.id.go_home);
        refresh = (Button) findViewById(R.id.refresh);
        detail = (Button) findViewById(R.id.detail);
        search.setOnClickListener(this);
        go_for.setOnClickListener(this);
        go_next.setOnClickListener(this);
        home.setOnClickListener(this);
        refresh.setOnClickListener(this);
        detail.setOnClickListener(this);



        //设置webview部分属性
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //设置 WebView 是否应该启用对“viewport”HTML 元标记的支持还是应该使用宽视口
        webView.getSettings().setUseWideViewPort(true);
        //设置 WebView 是否以概览模式加载页面，即按宽度缩小内容以适应屏幕
        webView.getSettings().setLoadWithOverviewMode(true);
        //设置解码 html 页面时使用的默认文本编码名称
        webView.getSettings().setDefaultTextEncodingName("utf-8");

        webView.loadUrl("https://www.baidu.com/");
        //设置前进或后退步数
        webView.goBackOrForward(1);


        //监听键盘回车搜索
        url.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if((keyCode == KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN) {
                    onClick(search);
                }
                url.setSelection(url.getText().toString().length());
                return false;
            }
        });
    }
    //同步系统返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search:
                String url_name;
                if((!url.getText().toString().startsWith("https://")) && (!url.getText().toString().startsWith("http://"))) {
                     url_name = "https://" + url.getText().toString();
                } else {
                    url_name =url.getText().toString();;
                }
                if(Patterns.WEB_URL.matcher(url_name).matches()){
                    webView.loadUrl(url_name);
                } else {
                    webView.loadUrl("https://www.baidu.com/s?wd=" + url.getText().toString());
                }
                break;
            case R.id.go_for:
                if(webView.canGoBack()) {
                    webView.goBack();
                } else {
                    Toast.makeText(MainActivity.this,"没有上一页了",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_next:
                if(webView.canGoForward()) {
                    webView.goForward();
                } else {
                    Toast.makeText(MainActivity.this,"没有下一页了",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_home:
                Toast.makeText(MainActivity.this,"这里应该进入主页",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.refresh:
                webView.loadUrl(webView.getUrl().toString());
                Toast.makeText(MainActivity.this,"正在刷新",
                        Toast.LENGTH_SHORT).show();
                break;
            case R.id.detail:
                Toast.makeText(MainActivity.this,"这里应该弹出详细界面",
                        Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }
}