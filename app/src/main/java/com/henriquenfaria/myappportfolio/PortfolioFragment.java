package com.henriquenfaria.myappportfolio;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

public class PortfolioFragment extends Fragment {
    private static final String ARG_APP_INFO = "APP_INFO";

    private MainActivity.AppInfo[] mAppInfo;

    private OnPortfolioFragmentInteractionListener mListener;

    public PortfolioFragment() {
    }

    public static PortfolioFragment newInstance(MainActivity.AppInfo[] appInfoArray) {
        PortfolioFragment fragment = new PortfolioFragment();
        Bundle args = new Bundle();

        if (appInfoArray == null) {
            throw new RuntimeException("PortfolioFragment - Initialization parameter can't be null");
        }
        args.putParcelableArray(ARG_APP_INFO, appInfoArray);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAppInfo = (MainActivity.AppInfo[]) getArguments().getParcelableArray(ARG_APP_INFO);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_portfolio, container, false);
        GridLayout appsGrid = (GridLayout) rootView.findViewById(R.id.apps_grid);

        for (int i = 0; i < mAppInfo.length; i++) {
            Button button = new Button(getActivity());
            button.setTag(mAppInfo[i]);
            button.setText(mAppInfo[i].getAppName());

            if (i + 1 == mAppInfo.length) {
                button.setTextColor(ContextCompat.getColor(getActivity(), R.color.specialColorAccent));
            }

            int buttonWidth = (int) getResources().getDimension(R.dimen.portfolio_app_button_width);
            int buttonHeight = (int) getResources().getDimension(R.dimen.portfolio_app_button_height);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(buttonWidth, buttonHeight);
            button.setLayoutParams(layoutParams);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.AppInfo appInfo = (MainActivity.AppInfo) (view.getTag());
                    onAppButtonPressed(getActivity(), appInfo);
                }
            });
            appsGrid.addView(button);
        }

        return rootView;
    }

    public void onAppButtonPressed(Context ctx, MainActivity.AppInfo appInfo) {
        if (mListener != null) {
            mListener.onPortfolioFragmentInteraction(ctx, appInfo);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnPortfolioFragmentInteractionListener) {
            mListener = (OnPortfolioFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnPortfolioFragmentInteractionListener {
        void onPortfolioFragmentInteraction(Context ctx, MainActivity.AppInfo appInfo);
    }
}
