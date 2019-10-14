package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.OrmApp;
import geekbrains.ru.lesson5_sugarorm.Presenter;

@Singleton
@Module
class PresenterModule {
    @Singleton
    @Provides
    Presenter getPresenter(){
        return new Presenter(OrmApp.getComponent());
    }
}
