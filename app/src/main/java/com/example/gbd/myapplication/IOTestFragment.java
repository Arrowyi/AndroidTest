package com.example.gbd.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

/**
 * Created by GBD_PC on 2018/3/25.
 */

public class IOTestFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.io_test_layout, container, false);

        final TextView tv = root.findViewById(R.id.io_test_textView);
        Button btn = root.findViewById(R.id.io_test_button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File("/sdcard/iotest", "test.txt");
                StringBuffer sb = new StringBuffer();
                boolean res = false;
                if (!f.exists()) {
                    try {
                        res = f.createNewFile();
                        sb.append("result is " + res);
                    } catch (IOException e) {
                        sb.append("  failed with " + e);
                    }
                } else {
                    sb.append("file is already exists");
                }

                tv.setText(sb.toString());

            }
        });

        return root;
    }
}
