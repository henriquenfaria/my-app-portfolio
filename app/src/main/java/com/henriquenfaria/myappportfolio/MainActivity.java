package com.henriquenfaria.myappportfolio;

import android.content.Context;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PortfolioFragment.OnPortfolioFragmentInteractionListener {

    private static final String[] APP_NAMES = {"Popular Movies", "Stock Hawk", "Build it Bigger", "Make Your App Material", "Go Ubiquitous", "Capstone"};
    private static final String[] APP_PACKAGE_NAMES = {"com.henriquenfaria.popularmovies", "com.udacity.stockhawk", "", "", "", ""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            return;
        }

        AppInfo[] appInfoArray = makeAppInfoArray(APP_NAMES, APP_PACKAGE_NAMES);
        PortfolioFragment portfolioFragment = PortfolioFragment.newInstance(appInfoArray);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.portfolio_container, portfolioFragment).commit();
    }

    @Override
    public void onPortfolioFragmentInteraction(Context ctx, AppInfo appInfo) {
        startNewApp(ctx, appInfo);
    }


    public void startNewApp(Context ctx, AppInfo appInfo) {
        Intent intent = ctx.getPackageManager().getLaunchIntentForPackage(appInfo.getAppPackageName());
        if (intent != null) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } else {
            String toastText = String.format(ctx.getResources().getString(R.string.launch_app_toast), appInfo.getAppName());
            Toast.makeText(ctx, toastText, Toast.LENGTH_SHORT).show();
        }
    }

    public class AppInfo implements Parcelable {
        private String appName;
        private String appPackageName;

        public AppInfo(String appName, String appPackageName) {
            this.appName = appName;
            this.appPackageName = appPackageName;
        }

        public String getAppName() {
            return appName;
        }

        public String getAppPackageName() {
            return appPackageName;
        }

        public AppInfo(Parcel in) {
            String[] data = new String[2];
            in.readStringArray(data);
            this.appName = data[0];
            this.appPackageName = data[1];
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeStringArray(new String[]{this.appName, this.appPackageName});
        }

        public final Parcelable.Creator CREATOR = new Parcelable.Creator() {
            public AppInfo createFromParcel(Parcel in) {
                return new AppInfo(in);
            }

            public AppInfo[] newArray(int size) {
                return new AppInfo[size];
            }
        };
    }

    private AppInfo[] makeAppInfoArray(String[] appNames, String[] appPackageNames) {
        AppInfo[] appInfoArray = new AppInfo[appNames.length];
        for (int i = 0; i < appInfoArray.length; i++) {
            appInfoArray[i] = new AppInfo(appNames[i], appPackageNames[i]);
        }
        return appInfoArray;
    }
}
