package com.example.gbd.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.commonsettings.CommonSettings;
import com.example.commonsettings.configmanager.ConfigManager;
import com.example.gbd.myapplication.dummy.DummyContent;
import com.example.gbd.myapplication.functiontest.FunctionTestMainActivity;

public class MainActivity extends AppCompatActivity implements ManuItemFragment.OnListFragmentInteractionListener {

    @CommonSettings(type = CommonSettings.Type.BOOLEAN)
    public static final String THE_FIRST_TEST_SETTING = "THE_FIRST_TEST_SETTING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        ConfigManager.init();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getFragmentManager().beginTransaction()
                .add(R.id.content_main, ManuItemFragment.newInstance(1))
                .addToBackStack("MenuList").commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
//            addressAndVarTest("addressAndVarTest test");
            return true;
        } else if (id == R.id.action_io) {
            Intent intent = new Intent(this, FunctionTestMainActivity.class);
            startActivity(intent);
        }


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

        try {
            Class c = Class.forName(item.fragment);
            Intent intent = new Intent(this, c);
            startActivity(intent);

        } catch (ClassNotFoundException e) {

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
