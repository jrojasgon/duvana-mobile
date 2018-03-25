package inope.com.toolrepo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.util.Map;

import inope.com.toolrepo.activity.CreationActivity;
import inope.com.toolrepo.activity.HomeActivity;
import inope.com.toolrepo.activity.TabedActivity;

public abstract class AbstractNavigationActivity extends AppCompatActivity {

    private Map<Integer, Class> mapNavigation;

    protected void createNavigation() {
        this.mapNavigation = mapNavigation;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.navigation_dashboard:
                    Intent intent2 = new Intent(getBaseContext(), TabedActivity.class);
                    startActivity(intent2);
                    return true;
                case R.id.navigation_notifications:
                    Intent intent3 = new Intent(getBaseContext(), CreationActivity.class);
                    startActivity(intent3);
                    return true;
            }
            return false;
        }

    };
}
