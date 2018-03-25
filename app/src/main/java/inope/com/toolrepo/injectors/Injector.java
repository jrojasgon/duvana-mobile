package inope.com.toolrepo.injectors;


import inope.com.toolrepo.component.AppComponent;

public class Injector {

    private static Injector ourInstance = new Injector();

    private AppComponent appComponent;

    private Injector() {
    }

    public static Injector getInstance() {
        return ourInstance;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public void setAppComponent(AppComponent appComponent) {
        this.appComponent = appComponent;
    }
}
