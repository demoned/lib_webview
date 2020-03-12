package com.bojun.webview;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.SimpleAdapter;

public class MainActivity extends AppCompatActivity {
    private static final int TBS_WEB = 0;
    private static final int FULL_SCREEN_VIDEO = 1;
    private static final int FILE_CHOOSER = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_advanced);
        init();
    }

    private void init() {
        ArrayList<HashMap<String, Object>> items = new ArrayList<HashMap<String, Object>>();
        GridView gridView = (GridView) this.findViewById(R.id.item_grid);
        if (null == gridView) {
            throw new IllegalArgumentException("the gridView is null");
        }
        String[] titles = getResources().getStringArray(R.array.index_titles);
        int[] iconResource = {R.drawable.tbsweb, R.drawable.fullscreen, R.drawable.filechooser};
        for (int i = 0; i < titles.length; i++) {
            HashMap<String, Object> item = new HashMap<String, Object>();
            item.put("title", titles[i]);
            item.put("icon", iconResource[i]);
            items.add(item);
        }
        SimpleAdapter gridAdapter = new SimpleAdapter(this, items, R.layout.function_block, new String[]{"title", "icon"},
				new int[]{R.id.Item_text, R.id.Item_bt});
        if (null != gridView) {
            gridView.setAdapter(gridAdapter);
            gridAdapter.notifyDataSetChanged();
            gridView.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> gridView, View view, int position, long id) {
                    Intent intent = null;
                    switch (position) {
                        case FILE_CHOOSER:
                            intent = new Intent(MainActivity.this, FileChooserActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;
                        case FULL_SCREEN_VIDEO:
                            intent = new Intent(MainActivity.this, FullScreenActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;
                        case TBS_WEB:
                            intent = new Intent(MainActivity.this, BrowserActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;
                        default:
                            break;
                    }
                }
            });
        }
    }
}
