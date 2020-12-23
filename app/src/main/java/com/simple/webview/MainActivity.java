package com.simple.webview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.FadingCircle;


import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Context mContext;
    ImageButton back, forward, stop, refresh, homeButton;
    private WebView mWebView;
    private Button mButtonBack;
    private Button mButtonForward;
    private ProgressBar mProgressBar;
    private String mUrl="https://google.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Request window feature action bar
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //buttons
        refresh = (ImageButton) findViewById(R.id.refresh);
        homeButton = (ImageButton) findViewById(R.id.home);
        back = (ImageButton) findViewById(R.id.back_arrow);
        forward = (ImageButton) findViewById(R.id.forward_arrow);
        stop = (ImageButton) findViewById(R.id.stop);
        mProgressBar=(ProgressBar) findViewById(R.id.progress1);

        Sprite fadingCircle = new FadingCircle();
        mProgressBar.setIndeterminateDrawable(fadingCircle);

        back.setOnClickListener(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        });
        forward.setOnClickListener(v -> {
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            }
        });

        stop.setOnClickListener(v -> mWebView.stopLoading());

        refresh.setOnClickListener(v -> mWebView.reload());
        homeButton.setOnClickListener(v -> {
            mWebView.loadUrl(mUrl);
        });

        // Get the application context
        mContext = getApplicationContext();
        // Get the activity
        Activity mActivity = MainActivity.this;

        // Get the widgets reference from XML layout
        RelativeLayout mRelativeLayout = (RelativeLayout) findViewById(R.id.rl);
        mWebView = (WebView) findViewById(R.id.web_view);
        mButtonBack = (Button) findViewById(R.id.btn_back);
        mButtonForward = (Button) findViewById(R.id.btn_forward);

        // Request to render the web page
        renderWebPage(mUrl);

        // Set a click listener for back button
        mButtonBack.setOnClickListener(view -> {
            /*
                public boolean canGoBack ()
                    Gets whether this WebView has a back history item.

                Returns
                    true : iff this WebView has a back history item
            */
            if (mWebView.canGoBack()) {
                /*
                    public void goBack ()
                        Goes back in the history of this WebView.
                */
                mWebView.goBack();

                // Display the notification
                Toast.makeText(mContext,"Go To Back",Toast.LENGTH_SHORT).show();
            }
        });

        // Set a click listener for forward button
        mButtonForward.setOnClickListener(view -> {
            /*
                public boolean canGoForward ()
                    Gets whether this WebView has a forward history item.

                Returns
                    true : iff this WebView has a forward history item
            */
            if (mWebView.canGoForward()) {
                /*
                    public void goForward ()
                        Goes forward in the history of this WebView.
                */
                mWebView.goForward();

                // Display the notification
                Toast.makeText(mContext,"Go To Forward",Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Custom method to render a web page
    protected void renderWebPage(String urlToRender) {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // Do something on page loading started
                super.onPageStarted(view, url, favicon);
                mProgressBar.setVisibility(ProgressBar.VISIBLE);
                mWebView.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // Do something when page loading finished
                // Check web view back history availability
                if(mWebView.canGoBack()){
                    mButtonBack.setEnabled(true);
                }else {
                    mButtonBack.setEnabled(false);
                }

                // Check web view forward history availability
                if(mWebView.canGoForward()){
                    mButtonForward.setEnabled(true);
                }else {
                    mButtonForward.setEnabled(false);
                }
            }
        });
        mProgressBar.setVisibility(ProgressBar.GONE);
        mWebView.setWebViewClient(new WebViewClient());

        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress) {
            }
        });

        mWebView.setWebViewClient(new WebViewClient() {
            public void onReceivedError(WebView view,int errorCode, String description, String failingUrl) {
                mWebView.setVisibility(View.GONE);
                Intent intent = new Intent(MainActivity.this,PageofflineActivity.class);
                startActivity(intent);
        } });

        // Enable the javascript
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setDatabaseEnabled(true);
        mWebView.getSettings().setGeolocationEnabled(true);
        mWebView.getSettings().setSupportZoom(true);
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.setWebChromeClient(new ChromeClient());

        // Render the web page
        mWebView.loadUrl(urlToRender);
    }



    /*
        public void onBackPressed ()
            Called when the activity has detected the user's press of the back key. The default
            implementation simply finishes the current activity, but you can override this to
            do whatever you want.
    */


    @Override
    public void onBackPressed(){
        Toast.makeText(mContext,"Back Key Pressed",Toast.LENGTH_SHORT).show();
        // We also allow to navigate back history by pressing device back key
        if(mWebView.canGoBack()){
            Toast.makeText(mContext,"Back History Available",Toast.LENGTH_SHORT).show();
            mWebView.goBack();
        }else {
            Toast.makeText(mContext,"No Back History Found",Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }

    private class ChromeClient extends WebChromeClient {
        private View mCustomView;
        private CustomViewCallback mCustomViewCallback;
        protected FrameLayout mFullscreenContainer;
        private int mOriginalOrientation;
        private int mOriginalSystemUiVisibility;

        ChromeClient() {}

        public Bitmap getDefaultVideoPoster()
        {
            if (mCustomView == null) {
                return null;
            }
            return BitmapFactory.decodeResource(getApplicationContext().getResources(), 2130837573);
        }

        public void onHideCustomView()
        {
            ((FrameLayout)getWindow().getDecorView()).removeView(this.mCustomView);
            this.mCustomView = null;
            getWindow().getDecorView().setSystemUiVisibility(this.mOriginalSystemUiVisibility);
            setRequestedOrientation(this.mOriginalOrientation);
            this.mCustomViewCallback.onCustomViewHidden();
            this.mCustomViewCallback = null;
        }

        public void onShowCustomView(View paramView, CustomViewCallback paramCustomViewCallback)
        {
            if (this.mCustomView != null)
            {
                onHideCustomView();
                return;
            }
            this.mCustomView = paramView;
            this.mOriginalSystemUiVisibility = getWindow().getDecorView().getSystemUiVisibility();
            this.mOriginalOrientation = getRequestedOrientation();
            this.mCustomViewCallback = paramCustomViewCallback;
            ((FrameLayout)getWindow().getDecorView()).addView(this.mCustomView, new FrameLayout.LayoutParams(-1, -1));
            getWindow().getDecorView().setSystemUiVisibility(3846 | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        }
    }
}