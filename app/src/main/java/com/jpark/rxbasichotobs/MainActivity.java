package com.jpark.rxbasichotobs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    List<String> list1 = new ArrayList<>();
    List<String> list2 = new ArrayList<>();
    ArrayAdapter<String> adapter1;
    ArrayAdapter<String> adapter2;
    ListView view1;
    ListView view2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view1 = (ListView) findViewById(R.id.list1);
        view2 = (ListView) findViewById(R.id.list2);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list1);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list2);
        view1.setAdapter(adapter1);
        view2.setAdapter(adapter2);

        Observable<String> hotObs = Observable.create(sub -> {
            for(int i=0;i<10;i++){
                sub.onNext(""+i);
            }
        });
        hotObs.subscribeOn(Schedulers.newThread()).publish();

        try{
            Thread.sleep(1000);
        } catch (Exception e){

        }
        hotObs.observeOn(Schedulers.io())
                .subscribe(result-> {
                    list1.add(result);
                    adapter1.notifyDataSetChanged();
                });
        try{
            Thread.sleep(1000);
        } catch (Exception e){

        }
        hotObs.observeOn(Schedulers.io())
                .subscribe(result-> {
                    list2.add(result);
                    adapter2.notifyDataSetChanged();
                });

    }
}
