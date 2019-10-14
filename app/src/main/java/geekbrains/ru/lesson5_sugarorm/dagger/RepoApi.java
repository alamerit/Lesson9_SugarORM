package geekbrains.ru.lesson5_sugarorm.dagger;

import javax.inject.Inject;

import geekbrains.ru.lesson5_sugarorm.retrofit.RestAPI;

public class RepoApi {
    RestAPI api;
    public RepoApi(RestAPI api){
        this.api = api;
    }
}
