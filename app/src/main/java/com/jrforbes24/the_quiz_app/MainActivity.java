package com.jrforbes24.the_quiz_app;

import android.content.Context;
import android.provider.MediaStore;
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
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

//    FINAL to make logging easier.
    public static final String TAG = MainActivity.class.getSimpleName();
    // integer to hold the current score
    int theScore = 0;
    // integer to hold the total possible
    int possibleScore = 8;
    //    question number that will be iterated
    int questionNumber = 0;
    // variable to store the correct answer to check against for the answer.
    String correctAnswer = "";
    //    array to store the questions
    String[] questionArray = {
            "What is the most popular pistol caliber?",
            "How amy wars did Colonel Potter from MASH fight in?",
            "When is Justin Bieber's birthday?",
            "What is the best rock band of all time?",
            "Who wrote 'The Three Musketeers'?",
            "Who wrote the line 'And miles to go before I sleep'?",
            "What was the name of the probe that flew by Pluto in July 2015?",
            "What is the name of the closest star to Earth?"
    };
    //   multidimensional array to store the answers
    String[][] answerArray = {
            {"radio", ".40 caliber", "9mm", ".45 acp", ".380 acp", "2"},
            {"radio", "5", "1", "3", "4", "3"},
            {"radio", "1 Mar 1994", "21 Oct 1991", "3 July 1984", "30 May 1995", "1"},
            {"check", "U2", "U2", "U2", "U2", "any"},
            {"text", "alexandre dumas"},
            {"radio", "emily dickinson", "robert frost", "francis scott key", "andrew jackson", "2"},
            {"text", "new horizons"},
            {"radio", "alpha centauri a", "alpha centauri b", "proxima centauri", "the sun", "4"}
    };
    // this is an EditText variable theName that will hold the EditText state from the xml
    private EditText theName;

//    this is and EditText variable theTextFieldAnswer will hold the EditText state from the xml
    private EditText theTextFieldAnswer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        Pulling in the_name EditText and assigning it the theName prepping for setting the listener
        theName =  findViewById(R.id.the_name);
        /*
        * Method to listen for actions on theName text field
        */
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

        theTextFieldAnswer = findViewById(R.id.the_answer_text);

        theTextFieldAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            EditText theAnswer = findViewById(R.id.the_answer_text);

            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE) || ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER) && (event.getAction() == KeyEvent.ACTION_DOWN))) {
                    hideKeyboard();
                    String textAnswer = theAnswer.getText().toString().toLowerCase();
                    View view = findViewById(R.id.the_answer_text);
                    checkAnswer(textAnswer, view);
                    return true;
                } else {
                    return false;
                }
            }
        });

    }


    /*
    * Listen for radio buttons being checked
    */
    public void onRadioButtonClicked(View view) {
//        Bring in the radio buttons
        RadioButton rb1 = findViewById(R.id.radioButton1);
        RadioButton rb2 = findViewById(R.id.radioButton2);
        RadioButton rb3 = findViewById(R.id.radioButton3);
        RadioButton rb4 = findViewById(R.id.radioButton4);

        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();



        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton1:
                if (checked)
                    rb1.setChecked(false);
                    checkAnswer(rb1.getText().toString(), view);
                    break;
            case R.id.radioButton2:
                if (checked)
                    rb2.setChecked(false);
                    checkAnswer(rb2.getText().toString(), view);
                    break;
            case R.id.radioButton3:
                if (checked)
                    rb3.setChecked(false);
                    checkAnswer(rb3.getText().toString(), view);
                    break;
            case R.id.radioButton4:
                if (checked)
                    rb4.setChecked(false);
                    checkAnswer(rb4.getText().toString(), view);
                    break;
        }
        Log.v(TAG, "onRadioButton end");
    }

    /*
    * Check to see which checkbox is checked.
    */
    public void onCheckboxClicked(View view) {

//        Get the checkboxes
        CheckBox cb1 = findViewById(R.id.one_box);
        CheckBox cb2 = findViewById(R.id.two_box);
        CheckBox cb3 = findViewById(R.id.three_box);
        CheckBox cb4 = findViewById(R.id.four_box);

        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.one_box:
                if(checked) {
                    cb1.setChecked(false);
                    checkAnswer(cb1.getText().toString(), view);
                    break;
                }
            case R.id.two_box:
                if(checked) {
                    cb2.setChecked(false);
                    checkAnswer(cb2.getText().toString(), view);
                    break;
                }
            case R.id.three_box:
                if(checked) {
                    cb3.setChecked(false);
                    checkAnswer(cb3.getText().toString(), view);
                    break;
                }
            case R.id.four_box:
                if(checked) {
                    cb4.setChecked(false);
                    checkAnswer(cb4.getText().toString(), view);
                    break;
                }
        }
        Log.v(TAG, "oncheckbox end " );
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
        Log.v(TAG, "hideKeyboard end" );
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

        Log.v(TAG, "createHelloToast end " );
    }

    /*
    * checks for text on the button and calls the appropriate method
    */
    public void startOrQuit(View view) {
        Button whichButton = findViewById(R.id.whichButton);
        String decide = whichButton.getText().toString();
        if (decide.equals("START")) {
            hideKeyboard();
            startQuiz(view);
        } else {
            quitQuiz(view);
        }

        Log.v(TAG, "startOrQuit end");
    }

    /*
    * startQuiz does the following, gets question and answers and adds to textview and buttons
    * also sets text of button to QUIT
    */
    public void startQuiz(View view) {
//      method to populate the question text field.
        populateQuestion(view);
        showAnswerView(view);
//        this will set the Text view to visible
        TextView theQuestion = findViewById(R.id.question_TV);
        theQuestion.setVisibility(View.VISIBLE);
//        change the start button to quit
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.quit_button));

        Log.v(TAG, "startQuiz end" );
    }

    /*
    * quitQuiz will display the final score in a toast, reset theScore to 0, change the button to START
    * and reset anything else I can't think of right now back to the starting position.
    */
    public void quitQuiz(View view) {
//        set the global variables back to default
        theScore = 0;
        questionNumber = 0;
        correctAnswer = "";

//        hide the question view and answer views
        TextView theQuestion = findViewById(R.id.question_TV);
        theQuestion.setVisibility(View.GONE);
        EditText text_answer = findViewById(R.id.the_answer_text);
        text_answer.setVisibility(View.GONE);
        RadioGroup radio_group = findViewById(R.id.multi_radio);
        radio_group.setVisibility(View.GONE);
        LinearLayout the_check_boxs = findViewById(R.id.check_box);
        the_check_boxs.setVisibility(View.GONE);

//        change the quit button to start
        Button whichButton = findViewById(R.id.whichButton);
        whichButton.setText(getString(R.string.start_button));
        resetScore(view);

        Log.v(TAG, "quitQuiz end");
    }

    /*
    * populate question gets question from array and sets the question in the question_TV
    */
    public void populateQuestion(View view) {
        TextView theQuestion = findViewById(R.id.question_TV);
        theQuestion.setText(getString(R.string.the_question, questionArray[questionNumber]));

        Log.v(TAG, "populateQuestion end" );
    }

    /*
    * populateAnswer gets the answer from the array and based on the array will change
    * the answer view and populate the appropriate fields.
    */
    public void showAnswerView(View view) {
//        find out which view to show and which view to hide.
        String theView = answerArray[questionNumber][0];
        if(theView.equals("radio")) {
            populateRadioButtons(view);
            RadioGroup radio_group = findViewById(R.id.multi_radio);
            radio_group.setVisibility(View.VISIBLE);
            EditText text_answer = findViewById(R.id.the_answer_text);
            text_answer.setVisibility(View.GONE);
            LinearLayout the_check_boxs = findViewById(R.id.check_box);
            the_check_boxs.setVisibility(View.GONE);
        }
        else if(theView.equals("check")) {
            populateCheckBoxes(view);
            RadioGroup radio_group = findViewById(R.id.multi_radio);
            radio_group.setVisibility(View.GONE);
            EditText text_answer = findViewById(R.id.the_answer_text);
            text_answer.setVisibility(View.GONE);
            LinearLayout the_check_boxs = findViewById(R.id.check_box);
            the_check_boxs.setVisibility(View.VISIBLE);
        }
        else {

            RadioGroup radio_group = findViewById(R.id.multi_radio);
            radio_group.setVisibility(View.GONE);
            LinearLayout the_check_boxs = findViewById(R.id.check_box);
            the_check_boxs.setVisibility(View.GONE);
            EditText text_answer = findViewById(R.id.the_answer_text);
            text_answer.setVisibility(View.VISIBLE);
            populateTextField(view);
        }

        Log.v(TAG, "showAnswerView end" );
    }

    /*
    * populate the strings for each radio button
    * call method for listener on the check boxes
    */
    public void populateRadioButtons(View view) {
//        Get the radio group
        RadioGroup radGroup = findViewById(R.id.multi_radio);

        for (int i=0; i<radGroup.getChildCount(); i++) {
            ((RadioButton) radGroup.getChildAt(i)).setText(answerArray[questionNumber][i + 1]);
        }

        int correctAnswerArrayPosition = Integer.parseInt(answerArray[questionNumber][answerArray[questionNumber].length -1]);
        correctAnswer = answerArray[questionNumber][correctAnswerArrayPosition];

        Log.v(TAG, "populateRadioButtons end" );
    }

    /*
    * populate the hint for the text field
    * call the method to check for the keyboard done
    */
    public void populateTextField(View view) {
        correctAnswer = answerArray[questionNumber][1];
        Log.v(TAG, "populateTextField end");
    }

    /*
    * populate the check boxes and set listener
    */
    public void populateCheckBoxes(View view) {
        correctAnswer = "U2";
        Log.v(TAG, "populateCheckBoxes end" );
    }

/*
* method to check if the answer is correct
* @param string with the answer
* @param view
*/
    public void checkAnswer(String userAnswer, View view) {
        if (userAnswer.equals(correctAnswer)) {
            correctToast();
            increaseScore(view);
            questionNumber += 1;
            populateQuestion(view);
            showAnswerView(view);
        }
        else {
            incorrectToast();
            questionNumber += 1;
            populateQuestion(view);
            showAnswerView(view);
        }
        Log.v(TAG, "checkAnswer1 end"  );
    }

    /*
    * method to check if the answer is correct
    * @param string with the answer
    */
    public void checkAnswer(String userAnswer) {
        if (userAnswer.equals(correctAnswer)) {
            correctToast();


        }
        else {
            incorrectToast();
            questionNumber += 1;

        }
        Log.v(TAG, "checkAnswer2 end");
    }

    /*
    * Toast to let player know they are correct.
    */
    public void correctToast() {
        Context context = getApplicationContext();
        CharSequence text = "That is correct!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Log.v(TAG, "correctToast end" );
    }

    /*
    * Toast to let player know they are correct.
    */
    public void incorrectToast() {
        Context context = getApplicationContext();
        String text = "That is incorrect!";
        text += "\n the correct answer is " + correctAnswer;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Log.v(TAG, "incorrectToast end");
    }

    /*
    * Method to increase the score in the text field.
    */
    public void increaseScore(View view) {
        TextView quiz_score = findViewById(R.id.the_score);
        theScore += 1;
        quiz_score.setText(getString(R.string.quiz_score, theScore));

        Log.v(TAG, "increaseScore end");
    }

    /*
    * Need to reset the score on the app screen to 0/8
    */
    public void resetScore(View view) {
        TextView quiz_score = findViewById(R.id.the_score);
        quiz_score.setText(getString(R.string.quiz_score, theScore));

        Log.v(TAG, "resetScore end");
    }


}





