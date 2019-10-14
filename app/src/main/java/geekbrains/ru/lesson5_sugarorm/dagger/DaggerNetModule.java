package geekbrains.ru.lesson5_sugarorm.dagger;

import java.util.List;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson5_sugarorm.retrofit.RestAPI;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class DaggerNetModule {

    @Provides
    public String provideEndpoint() {
        return "https://api.github.com/";
    }

    @Provides
    Retrofit getRetrofit(String provider){
        return new Retrofit.Builder()
                .baseUrl(provider)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    RestAPI getApi(Retrofit retrofit){
        return retrofit.create(RestAPI.class);
    }

    @Provides
    Single<List<RetrofitModel>> getRequest(RestAPI api){
        return api.loadUsers()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Provides
    RepoApi getRepo(RestAPI api){
        return new RepoApi(api);
    }
}
