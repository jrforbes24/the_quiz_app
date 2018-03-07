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
    // integer to hold the current score
    int theScore = 0;
    // integer to hold the total possible
    int possibleScore = 8;
    //    question number that will be iterated
    int qustionNumber = 0;
    //    array to store the questions
    String[] questionArray = {
            "What is the most popular pistol caliber?",
            "How amy wars did Colonel Potter from MASH fight in?",
            "When is Justin Bieber's birthday?",
            "What is the best rock band of all time?",
            "Who wrote 'The Three Musketeers'",
            "Who wrote the line 'And miles to go before I sleep'",
            "What was the name of the probe that flew by Pluto in July 2015?",
            "What is the name of the closest star to Earth?"
    };
    //   multidimensional array to store the answers
    String[][] answerArray = {
            {"radio", ".40 caliber", "9mm", ".45 acp", ".380 acp", "two_ button"},
            {"radio", "5", "1", "3", "4", "three_button"},
            {"radio", "1 Mar 1994", "21 Oct 1991", "3 July 1984", "30 May 1995", "one_button"},
            {"check", "U2", "U2", "U2", "U2", "any"},
            {"text", "alexandre dumas"},
            {"radio", "emily dickinson", "robert frost", "francis scott key", "andrew jackson", "two_button"},
            {"text", "new horizons"},
            {"radio", "alpha centauri a", "alpha centauri b", "proxima centauri", "the sun", "four_button"}
    };
    // this is an EditText variable theName that will hold the EditText state from the xml
    private EditText theName;

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
//            After hiding keyboard switch focus to Start button.
            Button whichButton = findViewById(R.id.whichButton);
            whichButton.setFocusableInTouchMode(true);
            whichButton.setFocusable(true);
            whichButton.requestFocus();
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
        if (decide.equals("START")) {
            startQuiz(view);
        } else {
            quitQuiz(view);
        }
    }

    /*
    * startQuiz does the following, gets question and answers and adds to textview and buttons
    * also sets text of button to QUIT
    */
    public void startQuiz(View view) {
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.quit_button));
    }

    /*
    * quitQuiz will display the final score in a toast, reset theScore to 0, change the button to START
    * and reset anything else I can't think of right now back to the starting position.
    */
    public void quitQuiz(View view) {
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.start_button));
    }
}


