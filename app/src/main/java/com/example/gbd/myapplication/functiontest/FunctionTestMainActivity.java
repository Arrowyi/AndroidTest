package com.example.gbd.myapplication.functiontest;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.gbd.myapplication.R;
import com.example.gbd.myapplication.functiontest.dummy.DummyContent;

import java.util.ArrayList;

public class FunctionTestMainActivity extends Activity implements ManuItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_test_main);
        getFragmentManager().beginTransaction()
                .add(R.id.functiontest_main_container, ManuItemFragment.newInstance(1))
                .addToBackStack("MenuList").commit();
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        try {
            Class c = Class.forName(item.fragment);
            Fragment f = (Fragment) c.newInstance();
            if (f != null) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.add(R.id.functiontest_main_container, f);
                transaction.addToBackStack(item.fragment);
                transaction.commit();
            }

        } catch (ClassNotFoundException e) {

        } catch (IllegalAccessException e) {

        } catch (InstantiationException e) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() <= 0) {
            finish();
        }
    }
}
