package geekbrains.ru.lesson5_sugarorm.dagger;

import android.net.NetworkInfo;

import dagger.Subcomponent;

@Subcomponent(modules = NetworkModule.class)
public interface NetworkComponent {
    NetworkInfo getNetwork();
}
