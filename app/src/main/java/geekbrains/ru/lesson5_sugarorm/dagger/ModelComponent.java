package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Singleton;

import dagger.Component;
import geekbrains.ru.lesson5_sugarorm.Presenter;

@Singleton
@Component(modules = {ModelModule.class})
public interface ModelComponent {
    void inject(Presenter presenter);
}
