package inope.com.toolrepo.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import inope.com.toolrepo.AbstractNavigationActivity;
import inope.com.toolrepo.beans.SinkBean;
import inope.com.toolrepo.component.AppComponent;
import inope.com.toolrepo.fragment.MainFragment;
import inope.com.toolrepo.R;
import inope.com.toolrepo.fragment.PhotoFragment;
import inope.com.toolrepo.fragment.PipelineFragment;
import inope.com.toolrepo.fragment.SinkFragment;
import inope.com.toolrepo.injectors.Injector;
import inope.com.toolrepo.services.CustomService;

public class CreationActivity extends AbstractInputActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected SinkBean getSinkBeanToSave() {
        return null;
    }

    @Override
    protected boolean isModeEdition() {
        return false;
    }

    @Override
    protected void addSendListenerAction() {

    }

    @Override
    protected void populateFromExtras(String extra) {

    }

    @Override
    protected void updateCustomValues(SinkBean sinkBean) {

    }
}
