package com.u063.explorer;



import android.graphics.Color;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import androidx.appcompat.app.AppCompatActivity;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getFiles("");
        requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE","android.permission.WRITE_EXTERNAL_STORAGE"}, 1);
        //requestPermissions(new String[]{"android.permission.READ_EXTERNAL_STORAGE",}, 1);
        //arr.add(Environment.getExternalStorageDirectory().getPath());
    }
    ArrayList<String> arr=new ArrayList<>();
    public void getFiles(String s){
        LinearLayout ll=new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        LinearLayout cc=findViewById(R.id.linear);
        cc.addView(ll);
        Button und = new Button(this);
        und.setText("UNDO");
        und.setBackgroundColor(Color.GRAY);
        und.setOnClickListener(v -> {
            ll.setVisibility(View.GONE);
            undo();
        });
        ll.addView(und);

        File f = new File(Environment.getExternalStorageDirectory().getPath()+"/"+s);
        File[] fs = f.listFiles();
        if (fs != null) {
            for(File files : fs){
                //Toast.makeText(this, ""+files.getName(), Toast.LENGTH_SHORT).show();
                Button bt = new Button(this);
                bt.setText(files.getName());
                bt.setOnClickListener(v -> {
                    ll.setVisibility(View.GONE);
                    arr.add("/"+files.getName());
                    getFiles(s+"/"+files.getName());
                });
                ll.addView(bt);
            }
        }
        Button del = new Button(this);
        del.setText("DELETE");
        del.setTextColor(Color.WHITE);
        del.setBackgroundColor(Color.BLACK);
        del.setOnClickListener(v -> {
            ll.setVisibility(View.GONE);
            delete();
        });
        ll.addView(del);
    }
    public void undo(){

        String paths="";
        arr.remove(arr.size()-1);
        for(int i=0; i<arr.size(); i++){
            paths+=arr.get(i);
        }

        getFiles(paths);
    }
    public void delete(){
        String paths="";
        String deleteF="";
        for(int i=0; i<arr.size(); i++){
            deleteF+=arr.get(i);
        }
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+deleteF);
        Toast.makeText(this, ""+f.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        f.delete();
        arr.remove(arr.size()-1);
        for(int i=0; i<arr.size(); i++){
            paths+=arr.get(i);
        }

        getFiles(paths);
    }
}