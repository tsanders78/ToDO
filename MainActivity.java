package com.example.todoapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;
import static org.apache.commons.io.FileUtils.readLines;
import static org.apache.commons.io.FileUtils.writeLines;


public class MainActivity extends AppCompatActivity {
    public static final String KEY_ITEM_TEXT = "item_text";
    public static final String KEY_ITEM_POSITION = "item_position";
    public static final int EDIT_TEXT_CODE = 20;
//Each design function
    List<String> itemArray;
    ItemsNotify itemsNotify;
    EditText txtItems;
    Button btnAdd;
    RecyclerView rvItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button) findViewById(R.id.addBtn); //The button
        txtItems = (EditText) findViewById(R.id.txtItems);//Inserting the tasks for the list
        rvItem = (RecyclerView) findViewById(R.id.rvItems);// Display of each item.

        loadItems();

        ItemsNotify.OnLongClickListener onLongClickListener = new ItemsNotify.OnLongClickListener(){
            @Override
            public void onItemLongClicked(int position) {
                itemArray.remove(position); // the item that is listed will be remove by clicking the long clicker
                itemsNotify.notifyItemRemoved(position); //notify when the item is removed
                Toast.makeText(getApplicationContext(), "Item is removed", Toast.LENGTH_SHORT).show(); // This will show once one of the task is removed
                saveItems(); // Save the items that is type on the screen
            }
        };

        ItemsNotify.OnClickListener onClickListener = new ItemsNotify.OnClickListener() {
            @Override
            public void onItemClicked(int position) {
                //Create the new activity
                Log.d("MainActivity", "Single click at position " + position);
                Intent e = new Intent(MainActivity.this, EditActivity.class);
                //Pass the data being edited
                e.putExtra(KEY_ITEM_TEXT, itemArray.get(position));
                e.putExtra(KEY_ITEM_POSITION, position);
                //Display the activity
                startActivityForResult(e, EDIT_TEXT_CODE);
            }
        };
        itemsNotify = new ItemsNotify(itemArray, onLongClickListener, onClickListener);
        rvItem.setAdapter(itemsNotify); //The set adapter helps by removing the items.
        rvItem.setLayoutManager(new LinearLayoutManager(this));

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemTodo = txtItems.getText().toString();
                itemArray.add(itemTodo); // add diffferent items that are being typed in and saved
                itemsNotify.notifyItemRemoved(itemArray.size() - 1);
                txtItems.setText("");
                Toast.makeText(getApplicationContext(), "Item was added", Toast.LENGTH_SHORT); //shows when the task item is added
                saveItems(); // Tells if the item is being removed
            }
        });
    }

    @Override
    protected void onActivityResult(int rCode, int finalCode, @Nullable Intent data) {
        super.onActivityResult(rCode, finalCode, data);
        if (finalCode == RESULT_OK && rCode == EDIT_TEXT_CODE) {
            String text = data.getStringExtra(KEY_ITEM_TEXT);
            int p = data.getExtras().getInt(KEY_ITEM_POSITION);
            itemArray.set(p, text);
            itemsNotify.notifyItemChanged(p); // notify any item changed
            saveItems();
            Toast.makeText(getApplicationContext(), "Successful Edit!!", Toast.LENGTH_SHORT).show();
        } else {
            Log.w("MainActivity", "No call to onActivityResult");
        }
    }


    private File getDataFile(){ //File will collect the data
        return new File(getFilesDir(), "data.txt");
    } //File 'data.txt' helps by adding different items in the file
    private void loadItems(){
        try { // try and catch is for the readLines
            itemArray = new ArrayList<String>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e){
            Log.e("MainActivity", "Error reading items", e); //readlines reads the items from the string
            itemArray = new ArrayList<>(); // any new items that updated once load to the file
        }
    }
    private void saveItems(){
        try {  //try and catch is for writeLines to work
            FileUtils.writeLines(getDataFile(), itemArray);
        } catch (IOException e){
            Log.e("MainActivity", "Error writing items", e);
        }
    }
}


