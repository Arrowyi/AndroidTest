package com.example.gbd.myapplication.functiontest;

import android.app.Activity;
import android.os.Bundle;

import com.example.gbd.myapplication.R;
import com.example.gbd.myapplication.functiontest.IOTest.IOTestFragment;

public class FunctionTestMainActivity extends Activity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_test_main);
        getFragmentManager().beginTransaction()
                .add(R.id.functiontest_main_container, new IOTestFragment())
                .addToBackStack("MenuList").commit();
    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getFragmentManager().getBackStackEntryCount() <= 0) {
            finish();
        }
    }
}
