package com.example.groceryapp1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String>items;
    ArrayAdapter<String>adapter;
    static Context context;

    EditText input;
    ImageView enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=findViewById(R.id.listview);
        input=findViewById(R.id.input);
        enter=findViewById(R.id.add);
        context=getApplicationContext();

        items=new ArrayList<>();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view,int i,long l  ){
                String name=items.get(i);
                makeToast(name);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView,View view,int i,long l){
                makeToast("Long press: "+items.get(i));
                return false;
            }
        });
        adapter=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);


        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text=input.getText().toString();
                if(text==null || text.length()==0) {
                    makeToast("Enter an Item");
                }else{
                    addItem(text);
                    input.setText("");
                    makeToast("Added:"+ text);

                }
            }
        });
    }





    @Override
    protected void onDestroy() {
        File path=getApplicationContext().getFilesDir();
        try {
            FileOutputStream writer=new FileOutputStream(new File(path,"list.txt"));
            writer.write(items.toString().getBytes());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    public void addItem(String item){
        items.add(item);
        adapter.notifyDataSetChanged();
    }
    Toast t;

    private void makeToast(String s){
        if(t!=null) t.cancel();
        t=Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();

    }

}