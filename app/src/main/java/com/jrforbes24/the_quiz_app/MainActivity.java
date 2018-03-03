package com.jrforbes24.the_quiz_app;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // this is an EditText variable theName that will hold the EditText state from the xml
    private EditText theName;
    // integer to hold the current score
    int theScore = 0;
    // integer to hold the total possible
    int possibleScore = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Pulling in the_name EditText and assigning it the theName
        theName = (EditText) findViewById(R.id.the_name);

        theName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    hideKeyboard();
                    createHelloToast();

                    return true;
                } else {
                    return false;
                }
            }
        });
    }

    /*
    * Function to hide the soft keyboard.
    */
    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imn = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imn.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /*
    * Creates a toast that says Hello name and then on new line Shall we play a game.
    */
    public void createHelloToast() {
//        Create a toast to say hello
        String myName = theName.getText().toString();
        Context context = getApplicationContext();
        CharSequence text = "Hello " + myName + ",";
        text = text + "\n Shall we play a game?";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    /*
    * checks for text on the button and calls the appropriate method
    */
    public void startOrQuit(View view) {
        Button whichButton = findViewById(R.id.whichButton);
        String decide = whichButton.getText().toString();
        Log.v("startOrQuiz", decide);
        if (decide == "START") {
            startQuiz();
        }
        else {
            quitQuiz();
        }
    }

    /*
    * startQuiz does the following, gets question and answers and adds to textview and buttons
    * also sets text of button to QUIT
    */
    public void startQuiz() {
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.quit_button));
//        Log.v("startQuiz", "Should be QUIT");
    }

    /*
    * quitQuiz will display the final score in a toast, reset theScore to 0, change the button to START
    * and reset anything else I can't think of right now back to the starting position.
    */
    public void quitQuiz() {
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.start_button));
    }
}


