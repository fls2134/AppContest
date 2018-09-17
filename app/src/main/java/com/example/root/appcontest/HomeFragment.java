package com.example.root.appcontest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class HomeFragment extends Fragment {
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_activity_home, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        super.onViewCreated(view, savedInstance);
        FloatingActionButton writeBtn = view.findViewById(R.id.floatingActionButton_home);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent writeIntent = new Intent(getActivity(), WriteActivity.class);
                startActivity(writeIntent);
            }
        });


        TabLayout mTabs = (TabLayout) view.findViewById(R.id.tabs_home);
        mTabs.addTab(mTabs.newTab().setText("정렬1"));
        mTabs.addTab(mTabs.newTab().setText("정렬2"));
        mTabs.addTab(mTabs.newTab().setText("정렬3"));

        mTabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 탭 선택시
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // 탭 선택이 풀릴 시
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // 탭이 다시 선택 되었을 시
            }
        });
    }
}
