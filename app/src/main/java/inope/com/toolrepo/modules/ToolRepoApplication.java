package inope.com.toolrepo.modules;

import android.app.Application;

import inope.com.toolrepo.component.DaggerAppComponent;
import inope.com.toolrepo.injectors.Injector;


public class ToolRepoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // DaggerAppComponent est une classe générée automatiquement après une compilation
        Injector.getInstance().setAppComponent(DaggerAppComponent.create());
    }
}