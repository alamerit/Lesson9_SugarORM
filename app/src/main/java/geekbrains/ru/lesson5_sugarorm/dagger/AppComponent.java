package geekbrains.ru.lesson5_sugarorm.dagger;

import dagger.Component;
import geekbrains.ru.lesson5_sugarorm.MainActivity;
import geekbrains.ru.lesson5_sugarorm.Model;
import geekbrains.ru.lesson5_sugarorm.Presenter;

@Component(modules = {DaggerNetModule.class})
public interface AppComponent {
    void inject(Presenter presenter);
    void getInject(Model model);

    NetworkComponent getNetworkComponent(NetworkModule module);
    SugarComponent getSugarComponent(DaggerSugarModule module);

    RepoApi getRepo();
}
