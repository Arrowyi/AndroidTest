package com.example.gbd.myapplication.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    public static DummyItem[] dummyItems = {
            new DummyItem(android.R.drawable.btn_star, "IO test"
                    , "com.example.gbd.myapplication.functiontest.FunctionTestMainActivity"),
            new DummyItem(android.R.drawable.btn_star, "Native test"
                    , "com.example.gbd.myapplication.nativetest.NativeTestActivity"),
            new DummyItem(android.R.drawable.btn_star, "Rx test"
                    , "com.gbd.example.rxtest.RxTestActivity")
    };


    static {
        // Add some sample items.
        for (int i = 0; i < dummyItems.length; i++) {
            addItem(dummyItems[i]);
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final int iconId;
        public final String content;
        public final String fragment;

        public DummyItem(int id, String content, String fragment) {
            this.iconId = id;
            this.content = content;
            this.fragment = fragment;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
