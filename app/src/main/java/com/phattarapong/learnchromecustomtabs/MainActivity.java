package com.phattarapong.learnchromecustomtabs;

import android.content.ComponentName;
import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private static final String CUSTOM_TAB_PACKAGE_NAME = "com.android.chrome";
    private static final String WEB_SITE_URL = "http://viralandroid.com/";
    private static final String GOOGLE_URL = "http://google.com/";

    private CustomTabsClient mCustomTabsClient;
    private CustomTabsSession mCustomTabsSession;
    private CustomTabsServiceConnection mCustomTabsServiceConnection;
    private CustomTabsIntent mCustomTabsIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCustomTabsServiceConnection = new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                mCustomTabsClient = client;
                    mCustomTabsClient.warmup(0L);
                mCustomTabsSession = mCustomTabsClient.newSession(null);
                mCustomTabsSession.mayLaunchUrl(Uri.parse(WEB_SITE_URL),null,null);
                mCustomTabsSession.mayLaunchUrl(Uri.parse(GOOGLE_URL),null,null);
;            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                mCustomTabsClient = null;
            }
        };
        CustomTabsClient.bindCustomTabsService(this, CUSTOM_TAB_PACKAGE_NAME, mCustomTabsServiceConnection);

        mCustomTabsIntent = new CustomTabsIntent.Builder(mCustomTabsSession)
                .setShowTitle(true)
                .setToolbarColor(getResources().getColor(R.color.colorAccent))
                .setStartAnimations(MainActivity.this,0,0)
                .build();
    }

    public void chromeCustomTabExample(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(WEB_SITE_URL));
    }

    public void chromeCustomTabGoogle(View view) {
        mCustomTabsIntent.launchUrl(this, Uri.parse(GOOGLE_URL));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCustomTabsServiceConnection != null) unbindService(mCustomTabsServiceConnection);
    }
}
