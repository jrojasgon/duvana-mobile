package inope.com.toolrepo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import inope.com.toolrepo.AbstractNavigationActivity;
import inope.com.toolrepo.R;

public class HomeActivity extends AbstractNavigationActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent = new Intent(getBaseContext(), TabedActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent2 = new Intent(getBaseContext(), CreationActivity.class);
                    startActivity(intent2);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        createNavigation();

        //mTextMessage = (TextView) findViewById(R.id.message);
        //BottomNavigationView menu_navigation = (BottomNavigationView) findViewById(R.id.menu_navigation);
        //menu_navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
