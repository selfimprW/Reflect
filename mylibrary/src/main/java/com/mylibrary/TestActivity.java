package com.mylibrary;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by Jiacheng on 2017/7/15.
 */
public class TestActivity extends AppCompatActivity implements TestAdapter.OnItemClick {
    private final String tag = "TestActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        TestAdapter adapter = new TestAdapter();
        recyclerView.setAdapter(adapter);

        adapter.setActions(getActions());
        adapter.setOnItemClick(this);
    }

    private List<String> actions;
    private Map<String, String> configMap;
    private Class cls;

    private List<String> getActions() {
        actions = new ArrayList<>();
        try {
            cls = ReflectUtils.getReflectClass("com.reflect.ConfigUtils");
            configMap = (Map<String, String>) ReflectUtils.getReflectValue(cls, "configMap");
            for (Map.Entry<String, String> entry : configMap.entrySet()) {
                Log.e("wjc", "key= " + entry.getKey() + " and value= " + entry.getValue());
                actions.add(entry.getKey());
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Log.e(tag, "ClassNotFoundException:" + e);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            Log.e(tag, "NoSuchFieldException:" + e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            Log.e(tag, "IllegalAccessException:" + e);
        } catch (InstantiationException e) {
            e.printStackTrace();
            Log.e(tag, "InstantiationException:" + e);
        }
        return actions;
    }

    @Override
    public void onItemClickListener(View view, int position) {
        if (actions == null || actions.size() < position || position < 0) {
            return;
        }
        String action = actions.get(position);
        String methodName = configMap.get(action);
        try {
            Method method = ReflectUtils.getMethod(cls, methodName);
            Object[] objects;
            if (method != null) {
                method.setAccessible(true);
                Class<?>[] classes = method.getParameterTypes();
                objects = new Object[classes.length];
                for (int i = 0; i < classes.length; i++) {
                    Class c = classes[i];
                    String simpleName = c.getSimpleName();
                    Log.e("wjc", simpleName);
                    if (simpleName.equals("Context")) {
                        objects[i] = view.getContext();
                    } else if (simpleName.equals("String")) {
                        objects[i] = String.format(getResources().getString(R.string.toast), position + 1);
                    } else if (simpleName.equals("int")) {
                        objects[i] = position;
                    }
                }
                method.invoke(cls.newInstance(), objects);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
