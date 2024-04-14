package com.example.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<String> urls = new ArrayList<>();
        urls.add("https://www.anu.edu.au/");
        urls.add("https://www.google.com/");

        ListView listView = findViewById(R.id.my_list);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, urls);
        listView.setAdapter(adapter);

        EditText editText = findViewById(R.id.editTextText);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urls.add(editText.getText().toString());
                editText.setText("");
                adapter.notifyDataSetChanged();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ActivityWeb.class);
                intent.putExtra("URL", urls.get(position).toString());
                startActivity(intent);
            }
        });
    }
}