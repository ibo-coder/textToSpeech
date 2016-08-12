package com.example.ibrahim.texttospeechapp;

import android.content.DialogInterface;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.StaticLayout;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.content.Intent;
import android.widget.Toast;

import java.util.Locale;

public  class MainActivity extends AppCompatActivity implements  OnInitListener {
    private int MY_DATA_CHECK_CODE = 0;

    private TextToSpeech myTTS;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button SpeakOut= (Button) findViewById(R.id.button);
        Intent checkTTSIntent = new Intent(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);

        startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);
        SpeakOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText etxt= (EditText) findViewById(R.id.editText);
                String text=etxt.getText().toString();
                speakWords(text);
            }
        });

    }
    private void speakWords(String speech) {

        myTTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MY_DATA_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                myTTS = new TextToSpeech(this, this);
            }
            else {
                Intent installTTSIntent = new Intent();
                installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installTTSIntent);
            }
        }
    }
    @Override
    public void onInit(int initStatus) {
        if (initStatus == TextToSpeech.SUCCESS) {
            if(myTTS.isLanguageAvailable(Locale.US)==TextToSpeech.LANG_AVAILABLE)
            myTTS.setLanguage(Locale.US);
          myTTS.setPitch(1.2f);
            myTTS.setSpeechRate(0.7f);
        }else if (initStatus == TextToSpeech.ERROR) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }
}
