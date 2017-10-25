package com.leaklibrary.activiy;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.leaklibrary.LeakManager;
import com.leaklibrary.R;
import com.leaklibrary.adapter.LeakAdapter;
import com.leaklibrary.info.WeakObjectInfo;
import com.leaklibrary.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofeng
 * on 2017/10/23.
 */

public class LeakDetailActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leak_detail);
        List<WeakObjectInfo> LEAK_QUEUE = LeakManager.getLeakQueue();
        ListView listview = (ListView) findViewById(R.id.listview);
        LeakAdapter adapter = new LeakAdapter(LeakDetailActivity.this);
        listview.setAdapter(adapter);
        adapter.setData(getLeakFromCache(LEAK_QUEUE));
    }

    private List<LeakAdapter.LeakBean> getLeakFromCache(List<WeakObjectInfo> leakQueue) {
        List<LeakAdapter.LeakBean> list = new ArrayList<>();
        for (WeakObjectInfo leak : leakQueue) {
            final Object o = leak.get();
            if (o == null) continue;
            list.add(new LeakAdapter.LeakBean(o.toString(), DateUtil.dateFormatter(leak.getTime())));
        }
        return list;
    }


}
