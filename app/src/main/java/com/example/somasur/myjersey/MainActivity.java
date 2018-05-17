package com.example.somasur.myjersey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.text.ParseException;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private Jersey myJersey;
    private boolean isRed;
    private TextView mNameTextView;
    private TextView mQuantityTextView;
    private ImageView mJerseyImageView;
    private final static String PREFS = "PREFS";
    private static final String KEY_JERSEY_NAME = "KEY_JERSEY_NAME";
    private static final String KEY_JERSEY_NUMBER = "KEY_JERSEY_NUMBER";
    private static final String KEY_JERSEY_COLOR = "KEY_JERSEY_COLOR";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myJersey = new Jersey();
        mNameTextView = findViewById(R.id.player_name_text);
        mQuantityTextView = findViewById(R.id.player_number_text);
        mJerseyImageView = findViewById(R.id.imageView);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editJerseyInfo();
            }
        });
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        String name = prefs.getString(KEY_JERSEY_NAME, getString(R.string.android));
        Integer number = Integer.parseInt(prefs.getString(KEY_JERSEY_NUMBER, getString(R.string.defaultPlayerNumber)));
        boolean jerseyColor = prefs.getBoolean(KEY_JERSEY_COLOR, true);
        myJersey.setmPlayerName(name);
        myJersey.setmPlayerNumber(number);
        myJersey.setMisRed(jerseyColor);
        showCurrentJersey();
    }

    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_JERSEY_NAME, myJersey.getmPlayerName());
        editor.putString(KEY_JERSEY_NUMBER, myJersey.getmPlayerNumber());
        editor.putBoolean(KEY_JERSEY_COLOR, myJersey.isMisRed());
        editor.commit();
    }

    public void editJerseyInfo() {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        myBuilder.setTitle(R.string.edit_jersey_title_text);
        View view = getLayoutInflater().inflate(R.layout.edit_dialog, null, false);

        final EditText nameEditTextField = view.findViewById(R.id.edit_name);//from the inflated view we will get the view
        //variables inside function can be used only when it is final
        final EditText quantityEditTextField = view.findViewById(R.id.edit_quantity);
        nameEditTextField.setText(myJersey.getmPlayerName());
        quantityEditTextField.setText(String.valueOf(myJersey.getmPlayerNumber()));
        Switch switchButton = view.findViewById(R.id.switch1);
        switchButton.setChecked(myJersey.isMisRed());

        switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if (isChecked) {
                    isRed = true;
                } else {
                    isRed = false;
                }
            }
        });

        myBuilder.setView(view);
        myBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String name = nameEditTextField.getText().toString();
                int quantity;
                try {
                    quantity = Integer.parseInt(quantityEditTextField.getText().toString());
                } catch (NumberFormatException  e) {
                    quantity = 0;
                }
                myJersey.setmPlayerName(name);
                myJersey.setmPlayerNumber(quantity);
                myJersey.setMisRed(isRed);
                showCurrentJersey();
            }
        });
        myBuilder.setNegativeButton(android.R.string.cancel, null);
        myBuilder.create().show();
    }


    public void showCurrentJersey() {
        mNameTextView.setText(myJersey.getmPlayerName());
        mQuantityTextView.setText(myJersey.getmPlayerNumber());
        if (myJersey.isMisRed()) {
            mJerseyImageView.setImageResource(R.mipmap.red_jersey);
        } else {
            mJerseyImageView.setImageResource(R.mipmap.blue_jersey);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        } else if (id == R.id.reset) {
            clearAllItemsConfirmationDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearAllItemsConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyDialogTheme);
        builder.setTitle(R.string.reset_confirmation_Dialog);
        builder.setMessage(R.string.confirmation_dialog_message);
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myJersey = new Jersey();
                showCurrentJersey();
            }
        });
        builder.create().show();
    }
}
