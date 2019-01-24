package com.example.developer.crashlytics;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import android.util.Log;
import android.widget.CheckBox;

import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LOG_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);



        // Log the onCreate event, this will also be printed in logcat
        Crashlytics.log(Log.VERBOSE, TAG, "onCreate");

        // Add some custom values and identifiers to be included in crash reports
        Crashlytics.setInt("MeaningOfLife", 42);
        Crashlytics.setString("LastUIAction", "Test value");
        Crashlytics.setUserIdentifier("123456789");

        // Report a non-fatal exception, for demonstration purposes
        Crashlytics.logException(new Exception("Non-fatal exception: something went wrong!"));

        // Checkbox to indicate when to catch the thrown exception.
        final CheckBox catchCrashCheckBox = findViewById(R.id.catchCrashCheckBox);



        // Log that the Activity was created.
        // [START crashlytics_log_event]
        Crashlytics.log("Activity created");
        // [END crashlytics_log_event]


    }

    public void causeCrash(View view) {

        final CheckBox catchCrashCheckBox = findViewById(R.id.catchCrashCheckBox);

        // Log that crash button was clicked.
        Crashlytics.log(Log.INFO, TAG, "Crash button clicked.");

        // If catchCrashCheckBox is checked catch the exception and report is using
        // logException(), Otherwise throw the exception and let Crashlytics automatically
        // report the crash.
        if (catchCrashCheckBox.isChecked()) {
            try {
                throw new NullPointerException();
            } catch (NullPointerException ex) {
                // [START crashlytics_log_and_report]
                Crashlytics.log(Log.ERROR, TAG, "NPE caught!");
                Crashlytics.logException(ex);
                // [END crashlytics_log_and_report]
            }
        } else {
            throw new NullPointerException();
        }

    }

}
