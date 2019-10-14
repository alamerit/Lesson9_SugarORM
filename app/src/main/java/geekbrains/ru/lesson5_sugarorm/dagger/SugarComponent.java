package geekbrains.ru.lesson5_sugarorm.dagger;

import android.os.Bundle;

import dagger.Subcomponent;
import io.reactivex.Single;

@Subcomponent(modules = DaggerSugarModule.class)
public interface SugarComponent {
    Single<Bundle> sugarSaveAll();
    Single<Bundle> getAll();
    Single<Bundle> deleteAll();
}
