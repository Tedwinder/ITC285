package com.wordgames.tedcu76.portabledictionary;
//implemented code from https://gist.github.com/donaldlittlepie/1271795
/**
 * Created by tedcu76 on 8/22/16.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



/**
 * Created by tedcu76 on 8/22/16.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AssetDatabaseOpenHelper {

    public static final int DB_VERSION = 1;

    // word table constants
    public static final String WORD_TABLE = "WORD";

    public static final String WORD_ID = "_id";
    public static final int WORD_ID_COL = 0;

    public static final String WORD_NAME = "WORD_NAME";
    public static final int WORD_NAME_COL = 1;

    public static final String WORD_DEFINITION = "WORD_DEFINITION";
    public static final int WORD_DEFINITION_COL = 2;

    public static final String WORD_OTHER = "WORD_OTHER";
    public static final int WORD_OTHER_COL = 3;



    SQLiteDatabase db;

    private static final String DB_NAME = "Dictionary.db";

    private Context context;

    public AssetDatabaseOpenHelper(Context context) {
        this.context = context;
    }

    public SQLiteDatabase openDatabase() {
        File dbFile = context.getDatabasePath(DB_NAME);

        if (!dbFile.exists()) {
            try {
                copyDatabase(dbFile);
            } catch (IOException e) {
                throw new RuntimeException("Error creating source database", e);
            }
        }
        //db=SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);
        return SQLiteDatabase.openDatabase(dbFile.getPath(), null, SQLiteDatabase.OPEN_READONLY);

    }

    private void copyDatabase(File dbFile) throws IOException {
        //InputStream is = context.getAssets().open(DB_NAME);
        InputStream is=MainActivity.assetManager.open("dictionary"+ File.separator+"Dictionary.db");
        OutputStream os = new FileOutputStream(dbFile);

        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }


    private void openReadableDB(){
        db = AssetDatabaseOpenHelper.this.openDatabase();
    }

    private void closeDB(){
        if (db != null)
            db.close();
    }

    public ArrayList<Word> searchWords(String wordSearch) {

        String where =
                "word_name" + " LIKE ?";
        String[] whereArgs = {wordSearch};


        db= AssetDatabaseOpenHelper.this.openDatabase();
        Cursor cursor = db.query(WORD_TABLE, null, where, whereArgs, null, null, null);
        ArrayList<Word> words = new ArrayList<Word>();
        while (cursor.moveToNext()) {
            Word word = new Word();
            word.setId(cursor.getInt(WORD_ID_COL));
            word.setName(cursor.getString(WORD_NAME_COL));
            word.setDefinition(cursor.getString(WORD_DEFINITION_COL));
            word.setOther(cursor.getString(WORD_OTHER_COL));

            words.add(word);
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return words;

    }

}





