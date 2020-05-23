package com.example.gbd.myapplication.nativetest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.gbd.myapplication.R;

public class NativeTestActivity extends AppCompatActivity {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_test2);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.native_text);
        String fromJNI = stringFromJNI();
        tv.setText(fromJNI);

        addressAndVarTest(fromJNI);
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();

    public native void addressAndVarTest(String text);
}
