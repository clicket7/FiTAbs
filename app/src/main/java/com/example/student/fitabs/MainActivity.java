package com.example.student.fitabs;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Define bottom navigation view (thats why design library in gradle was imported)
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById (R.id.bottom_navigation);

        //Define Bottom navigation view listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            //Selected icon(item) - changes to the appropriate view
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    //Contacts
                    case R.id.action_contacts:
                        //Toast.makeText(MainActivity.this, "Contacts", Toast.LENGTH_SHORT).show();


                        break;

                    //Chat
                    case R.id.action_chat:
                        //Toast.makeText(MainActivity.this, "Chat", Toast.LENGTH_SHORT).show();


                        break;

                    //Calendar
                    case R.id.action_calendar:
                        //Toast.makeText(MainActivity.this, "Calendar", Toast.LENGTH_SHORT).show();


                        break;

                    //Settings
                    case R.id.action_settings:
                        //Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();


                        break;
                }
                return true;
            }
        });
    }
}
