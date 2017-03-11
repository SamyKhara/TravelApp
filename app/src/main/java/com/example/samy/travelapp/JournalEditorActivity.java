package com.example.samy.travelapp;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import java.util.Date;

public class JournalEditorActivity extends AppCompatActivity {

    //Create an instance of the date in case this journal is new
    Date d = new Date();
    CharSequence dateCreated  = DateFormat.format("MMMM d, yyyy ", d.getTime());
    String lastName = "";
    String lastJournal = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Standard Generated stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_editor);

        //Shared Preferences Variables
        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //Check to see if this journal is a new journal or needs to be loaded in
        int loadNumber = sharedpreferences.getInt("load",-1);
        if (loadNumber!=-1){
            //Load in the information
            String loadString = Integer.toString(loadNumber);
            //load Name of journal
            TextView textViewName= (TextView)findViewById(R.id.textViewName);
            textViewName.setText(sharedpreferences.getString("name"+loadString,""));
            //Load Date Created
            TextView textViewDateCreated = (TextView) findViewById(R.id.textViewDateCreated);
            textViewDateCreated.setText(sharedpreferences.getString("date"+loadString,""));
            //Load the Journal Content
            TextView textViewJournal= (TextView)findViewById(R.id.textViewJournal);
            textViewJournal.setText(sharedpreferences.getString("journal"+loadString,""));
        }
        else{
            //Set the Date created to today
            TextView textViewDateCreated = (TextView) findViewById(R.id.textViewDateCreated);
            textViewDateCreated.setText(dateCreated);
        }

        //Somehow forces the keyboard not to be be brought up on start
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


    }

    public void onSave(View view){
        //Shared Preferences Variables
        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        int loadNumber = sharedpreferences.getInt("load", -1);
        TextView textViewName = (TextView) findViewById(R.id.textViewName);
        TextView textViewJournal = (TextView) findViewById(R.id.textViewJournal);

        //Check to see if this is a new journal
        if (loadNumber==-1) {
            //this is a new journal, so it scans for the next available slot to save to
            boolean a = true;
            for (int i = 0; a; i++) {
                if (sharedpreferences.getString("name" + Integer.toString(i), "") == "") {
                    editor.putInt("load", i);
                    //if the name is blank, then set the name to a default name
                    if(textViewName.getText().toString()==""){
                        textViewName.setText("Name "+ Integer.toString(sharedpreferences.getInt("load",0)));
                    }
                    //save the current settings
                    editor.putString("name" + Integer.toString(i), textViewName.getText().toString());
                    editor.putString("journal" + Integer.toString(i), textViewJournal.getText().toString());
                    editor.putString("date" + Integer.toString(i), dateCreated.toString());
                    lastName = textViewName.getText().toString();
                    lastJournal = textViewJournal.getText().toString();
                    a = false;
                    editor.commit();
                }
            }
        }
        else{
            //this is not a new journal and has been edited
            editor.putString("name" + Integer.toString(loadNumber), textViewName.getText().toString());
            editor.putString("journal" + Integer.toString(loadNumber), textViewJournal.getText().toString());
            editor.commit();
        }

        //close the keyboard so the user can see what they have written
        hideKeyboard(this);

    }


    public void backActivity(View view){
        //save the stuff and end this activity and open the journal chooser
        onSave(view);
        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("update",true);
        editor.commit();
        finish();
    }

    public void deleteJournal(View view){
        //Shared preferences variables
        SharedPreferences sharedpreferences = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        int loadNumber = sharedpreferences.getInt("load", -1);

        //if this is a not a new journal
        if (loadNumber !=-1){
            //while there is data at the next load number
            while (sharedpreferences.getString("name"+ Integer.toString(loadNumber+1),"")!=""){
                //load the load number because it might have changed if this is the 2+ time going throught this while loop
                loadNumber = sharedpreferences.getInt("load", -1);
                //set the variables from the current load number to the variables from the next load number
                editor.putString("name"+ Integer.toString(loadNumber),sharedpreferences.getString("name"+ Integer.toString(loadNumber+1),""));
                editor.putString("date"+ Integer.toString(loadNumber),sharedpreferences.getString("date"+ Integer.toString(loadNumber+1),""));
                editor.putString("journal"+ Integer.toString(loadNumber),sharedpreferences.getString("journal"+ Integer.toString(loadNumber+1),""));
                //increase the load number so that the next iteration can do the same thing
                editor.putInt("load",loadNumber+1);
                editor.commit();

            }
            //when this is the last load number in the sequence, remove it so that it is not a duplicate of the second to last one
            editor.remove("name"+ Integer.toString(loadNumber));
            editor.remove("date"+ Integer.toString(loadNumber));
            editor.remove("journal"+ Integer.toString(loadNumber));
            editor.commit();
        }
        //whether is is new or not, exit back to the journal chooser
        editor.putBoolean("update",true);
        editor.commit();
        finish();

    }

    //somehow forces the keyboard to close
    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}

