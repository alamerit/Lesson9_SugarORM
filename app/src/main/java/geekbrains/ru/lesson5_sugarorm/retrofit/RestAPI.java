package geekbrains.ru.lesson5_sugarorm.retrofit;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestAPI {
    @GET("users")
    Single<List<RetrofitModel>> loadUsers();
}