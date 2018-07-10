package com.example.gbd.myapplication.functiontest.IOTest;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.gbd.myapplication.R;

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
                String path = getContext().getFilesDir().getPath() ;
                String lineSeparator = System.getProperty("line.separator", "/n");
                File f = new File(path + "/iotest", "test.txt");
                File f1 = new File(path, "test.txt");
                StringBuffer sb = new StringBuffer();

                sb.append("path is " + path);
                sb.append(lineSeparator);
                sb.append("file name is " + f.getAbsolutePath());
                sb.append(lineSeparator);
                sb.append("f1 name is " + f1.getAbsolutePath());
                sb.append(lineSeparator);
                boolean res = false;
                if (!f.exists()) {
                    try {
                        sb.append("create f ");
                        sb.append(lineSeparator);
                        res = f.createNewFile();
                        sb.append("result is " + res);
                    } catch (IOException e) {
                        sb.append("  failed with " + e);
                    }
                } else {
                    sb.append("f is already exists");
                }

                sb.append(lineSeparator);
                sb.append(lineSeparator);

                if (!f1.exists()) {
                    try {
                        sb.append("create f1 ");
                        sb.append(lineSeparator);
                        res = f1.createNewFile();
                        sb.append("result is " + res);
                    } catch (IOException e) {
                        sb.append("  failed with " + e);
                    }
                } else {
                    sb.append("f1 is already exists");
                }

                tv.setText(sb.toString());

            }
        });

        return root;
    }
}
