package inope.com.toolrepo.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import inope.com.toolrepo.services.CustomService;
import inope.com.toolrepo.services.impl.CustomServiceImpl;

@Module
public class AppModule {

    @Provides
    @Singleton
    CustomService providesCustomService() {
        return new CustomServiceImpl();
    }

}