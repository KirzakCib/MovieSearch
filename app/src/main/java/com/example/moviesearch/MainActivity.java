package com.example.moviesearch;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;



public class MainActivity extends Activity implements PageFragment_one.OnSelectGridView {
    PageFragment_one fragment_one;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


         fragment_one = new PageFragment_one();
        FragmentTransaction fragment = getFragmentManager().beginTransaction();
        fragment.add(R.id.page, fragment_one);
        fragment.commit();
    }

    @Override
    public void onGridSelect(String name) {
        PageFragment_two pageFragment_two = new PageFragment_two();
        FragmentTransaction fragment = getFragmentManager().beginTransaction();
        pageFragment_two.setText(name);

        fragment.add(R.id.page, pageFragment_two).addToBackStack("tag");
        fragment.commit();
    }
}
