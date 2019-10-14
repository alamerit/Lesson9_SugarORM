package geekbrains.ru.lesson5_sugarorm;

import android.app.Activity;
import android.app.Application;

import com.squareup.leakcanary.LeakCanary;

import java.util.List;

import androidx.room.Room;
import geekbrains.ru.lesson5_sugarorm.dagger.AppComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.DaggerAppComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.DaggerPresenterComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.DaggerSugarModule;
import geekbrains.ru.lesson5_sugarorm.dagger.ModelComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.NetworkComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.NetworkModule;
import geekbrains.ru.lesson5_sugarorm.dagger.PresenterComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.SugarComponent;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import geekbrains.ru.lesson5_sugarorm.room.MyDatabase;

public class OrmApp extends Application {

    private static final String DATABASE_NAME = "DATABASE_USER_GIT";
    public static MyDatabase database;
    public static OrmApp INSTANCE;
    private static AppComponent component;
    private static PresenterComponent presenterComponent;
    private static ModelComponent modelComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(getApplicationContext(), MyDatabase.class, DATABASE_NAME).build();
        INSTANCE = this;
        component = DaggerAppComponent.create();
        presenterComponent = DaggerPresenterComponent.create();
    }

    public MyDatabase getDB() {
        return database;
    }

    public static OrmApp get() {
        return INSTANCE;
    }

    public static AppComponent getComponent() {
        return component;
    }
    public static PresenterComponent getPresenterComponent() {
        return presenterComponent;
    }

    public static ModelComponent getModelComponent() {
        return modelComponent;
    }

    public static NetworkComponent makeNetworkComponent(Activity activity) {
        return component.getNetworkComponent(new NetworkModule(activity));
    }

    public static SugarComponent makeSugarComponent(List<RetrofitModel> models) {
        return component.getSugarComponent(new DaggerSugarModule(models));
    }
}