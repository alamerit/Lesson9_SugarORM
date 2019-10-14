package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Singleton;

import dagger.Component;
import geekbrains.ru.lesson5_sugarorm.MainActivity;
import geekbrains.ru.lesson5_sugarorm.Presenter;

@Singleton
@Component(modules = {PresenterModule.class})
public interface PresenterComponent {
    void inject(MainActivity mainActivity);
}
