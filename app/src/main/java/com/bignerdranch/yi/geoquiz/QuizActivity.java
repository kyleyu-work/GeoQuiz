package com.bignerdranch.yi.geoquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends AppCompatActivity {

  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX= "index";
  private static final String KEY_ANSWERED = "answered";
  private static final String KEY_CORRECT_ANSWER = "correctAnswers";

  private Button mTrueButton;
  private Button mFalseButton;
  private Button mNextButton;
  private TextView mQuestionTextView;

  private Question[] mQuestionBank = new Question[] {
      new Question(R.string.question_australia, true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true),
  };
  private boolean[] mQuestionAnswered = new boolean[mQuestionBank.length];

  private int mCurrentIndex = 0;
  private int mCorrectAnswers = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "OnCreate(Bundle) called");
    setContentView(R.layout.activity_quiz);

    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      mQuestionAnswered = savedInstanceState.getBooleanArray(KEY_ANSWERED);
      mCorrectAnswers = savedInstanceState.getInt(KEY_CORRECT_ANSWER, 0);
    }

    mQuestionTextView = (TextView) findViewById(R.id.question_text_view);

    mTrueButton = (Button) findViewById(R.id.true_button);
    mTrueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
      }
    });

    mFalseButton = (Button) findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });

    updateQuestion();

    mNextButton = (Button) findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex++;
        if (mCurrentIndex == mQuestionBank.length) {
          Toast.makeText(
              QuizActivity.this,
              "Correct rate: " + (1.0 * mCorrectAnswers / mQuestionBank.length),
              Toast.LENGTH_LONG)
              .show();
          restartGame();
        }
        updateQuestion();
      }
    });
  }

  @Override
  public void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");
  }

  @Override
  public void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  @Override
  public void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    savedInstanceState.putInt(KEY_CORRECT_ANSWER, mCorrectAnswers);
    savedInstanceState.putBooleanArray(KEY_ANSWERED, mQuestionAnswered);
  }

  @Override
  public void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  public void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getTextResId();
    mQuestionTextView.setText(question);
    mTrueButton.setEnabled(!mQuestionAnswered[mCurrentIndex]);
    mFalseButton.setEnabled(!mQuestionAnswered[mCurrentIndex]);
  }

  private void checkAnswer(boolean userPressedTrue) {
    mQuestionAnswered[mCurrentIndex] = true;
    updateQuestion();
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();

    int messageResId;
    if (userPressedTrue == answerIsTrue) {
      messageResId = R.string.correct_toast;
      mCorrectAnswers++;
    } else {
      messageResId = R.string.incorrect_toast;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }

  private void restartGame() {
    mCurrentIndex = 0;
    mCorrectAnswers = 0;
    mQuestionAnswered = new boolean[mQuestionBank.length];
  }
}
