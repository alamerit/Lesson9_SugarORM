package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.Model;
import geekbrains.ru.lesson5_sugarorm.OrmApp;

@Singleton
@Module
class ModelModule {
    @Singleton
    @Provides
    Model getModel(){
        return new Model(OrmApp.getComponent());
    }
}
