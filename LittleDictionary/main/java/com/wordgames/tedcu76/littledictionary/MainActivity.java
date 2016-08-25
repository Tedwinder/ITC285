package com.wordgames.tedcu76.littledictionary;

import android.app.ListActivity;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.res.AssetManager;

import android.graphics.Typeface;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Gravity;

import android.view.View;

import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;


import java.io.IOException;
import java.util.ArrayList;










public class MainActivity extends ActionBarActivity implements View.OnClickListener {


    public static AssetManager assetManager;
    private Button search;
    private ListView listView;
    private EditText editText;

    private SQLiteDatabase db;
    private ListAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        search = (Button) findViewById(R.id.button);
        editText = (EditText) findViewById(R.id.editText);
        //listView = (ListView) findViewById(R.id.list);
        //listView = getListView();


        listView = (ListView) findViewById(R.id.list);
        //myAdapter = new ListAdapter(getApplicationContext());
        //listView.setAdapter(myAdapter);




        assetManager = this.getAssets();

        search.setOnClickListener(this);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.custom_action_bar_layout);
        View view =getSupportActionBar().getCustomView();

        //ImageButton imageButton= (ImageButton)view.findViewById(R.id.action_bar_forward);


        //db= new AssetDatabaseOpenHelper(this).openDatabase();


        DataBaseHelper myDbHelper = new DataBaseHelper(null);
        myDbHelper = new DataBaseHelper(this);

        try {

            myDbHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            myDbHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;


        }
    }

    public void search() {
        /*int letterNumString = 6;
        int letterNum = 0;
        // get the number of letters
//        letterNumString = letterNumEditText.getText().toString();
        try {
            //letterNum = Integer.parseInt(letterNumString);
             letterNum = letterNumString;
        } catch (NumberFormatException e) {
            letterNum = 0;
        }
        //DataBaseHelper db = new DataBaseHelper(this);*/
        //AssetDatabaseOpenHelper db = new AssetDatabaseOpenHelper(this);
        DataBaseHelper db = new DataBaseHelper(this);
        db.openDataBase();



        String wordSearch=editText.getText().toString();

        ArrayList<Word> searchWords = db.searchWords(wordSearch);


        String[] words = new String[searchWords.size()];
        String both = "";
        SpannableStringBuilder longDescription = new SpannableStringBuilder();
        StringBuilder builder = new StringBuilder();
        if (searchWords.isEmpty()){
            Toast toast= Toast.makeText(MainActivity.this,
                    "Sorry, no entry for " + "\n" +wordSearch
                            + " exists" , Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 0);
            toast.show();

        }else {
            for (int i = 0; i < searchWords.size(); i++) {

                String def = searchWords.get(i).getDefinition();
                String word = searchWords.get(i).getName();
                String pos = searchWords.get(i).getOther();


                int start = longDescription.length();
                longDescription.append((i + 1) + ". ");
                longDescription.setSpan(new ForegroundColorSpan(0xFF000000), start,
                        longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new StyleSpan(Typeface.BOLD),
                        start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                start = longDescription.length();
                longDescription.append(word);
                longDescription.setSpan(new ForegroundColorSpan(0xFFCC5500), start,
                        longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),
                        start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                start = longDescription.length();
                longDescription.append(": " + pos);
                longDescription.setSpan(new ForegroundColorSpan(0xFF000000), start,
                        longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.setSpan(new StyleSpan(Typeface.ITALIC),
                        start, longDescription.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                longDescription.append(" " + def + "\n" + "\n");

                both =  (i + 1) + ". " + word + ": " + pos + " " + def + "\n" + "\n";
                builder.append(both);

                words[i] = both;

            }
        }

        //list1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.list_i‌​tem,startDates_str))‌​;
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_words,words));

        /*listView = getListView();
        listView.setTextFilterEnabled(true);*/

       /* listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                //Toast.makeText(getApplicationContext(),
                        //((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });*/

        Log.d("word search", wordSearch);

        Log.d("word search", wordSearch);

       /* for (int i = 0; i < 1; i++) {
            Log.d("word search", "Word: " + searchWords.get(i).getName());
            Log.d("word search", "Definition: " + searchWords.get(i).getDefinition());
            Log.d("word search", "Other info: " + searchWords.get(i).getOther());
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                search();

                break;
        }
    }





   /* private void openReadableDB(){
        myDataBase = DataBaseHelper.this.getWritableDatabase();
    }
    private void closeDB(){
        if (myDataBase != null)
            myDataBase.close();
    }
    public ArrayList<Word> searchWords(String wordSearch){
        String where =
                "word_name" + " LIKE ?";
        String[] whereArgs = {wordSearch};
        DataBaseHelper.this.getReadableDatabase();
        Cursor cursor = myDataBase.query(WORD_TABLE, null, where, whereArgs, null, null,null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()){
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));
            words.add(word);
        }
        if(cursor != null)
            cursor.close();
        this.closeDB();
        return words;*/


}