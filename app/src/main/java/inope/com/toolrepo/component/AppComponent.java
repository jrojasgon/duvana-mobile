package inope.com.toolrepo.component;


import android.app.Activity;

import javax.inject.Singleton;

import dagger.Component;
import inope.com.toolrepo.activity.AbstractCreationActivity;
import inope.com.toolrepo.modules.AppModule;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(AbstractCreationActivity activity);
}