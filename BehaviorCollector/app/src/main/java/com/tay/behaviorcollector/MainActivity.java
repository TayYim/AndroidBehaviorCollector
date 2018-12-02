package com.tay.behaviorcollector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tay.behaviorcollector.Scenario.PlainActivity;
import com.tay.behaviorcollector.Scenario.SlidingActivity;

public class MainActivity extends AppCompatActivity {

    public static Context context; // global access

    private Button button_plain;
    private Button button_slide;
    private Button button_confirm;
    private EditText editText_userID;
    private TextView textView_hint;
    private User user;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();

        user = new User("default"); // set default user id

        button_plain = findViewById(R.id.button_plain);
        button_slide = findViewById(R.id.button_slide);
        button_confirm = findViewById(R.id.button_confirm);
        editText_userID = findViewById(R.id.editText_userID);
        textView_hint = findViewById(R.id.textView_hint);

        /** initial  toolbar **/
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** initial  button_plain **/
        button_plain.setEnabled(false);
        button_plain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlainActivity.class);
                startActivity(intent);
            }
        });

        /** initial  button_slide **/
        button_slide.setEnabled(false);
        button_slide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SlidingActivity.class);
                startActivity(intent);
            }
        });

        /** initial button_confirm **/
        button_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userIDInput = String.valueOf(editText_userID.getText());
                textView_hint.setText(userIDInput + ": choose scenario");
                user.setUserID(userIDInput);

                activate();
            }
        });
    }

    /**
     * initial menu items
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    /**
     * handle menu item events
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_config) {
            Toast.makeText(context, "config", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, ConfigActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        deactivate();
    }

    private void activate() {
        button_plain.setEnabled(true);
        button_slide.setEnabled(true);
    }

    private void deactivate() {
        button_plain.setEnabled(false);
        button_slide.setEnabled(false);
    }

    public static Context getContext(){
        return context;
    }

}
