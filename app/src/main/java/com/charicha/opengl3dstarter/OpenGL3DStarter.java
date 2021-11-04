package com.charicha.opengl3dstarter;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Created by Charicha on 1/16/2018.
 */

public class OpenGL3DStarter extends ListActivity {

    String[] activities = {"Vertices3Test", "ZBlendingTest", "CubeTest", "HierarchicalTest"};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setListAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, activities));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        try {
            Class clazz = Class.forName("com.charicha.opengl3dstarter." + activities[position]);
            Intent intent = new Intent(this, clazz);
            startActivity(intent);
        } catch(ClassNotFoundException cnfe){
            cnfe.printStackTrace();
        }
    }
}
