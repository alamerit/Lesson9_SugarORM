package geekbrains.ru.lesson5_sugarorm;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import geekbrains.ru.lesson5_sugarorm.dagger.AppComponent;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import io.reactivex.Single;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;

public class Model {
    @Inject
    Single<List<RetrofitModel>> request;
    List<RetrofitModel> modelList = new ArrayList<>();



    public Model(AppComponent component){

        component.getInject(this);
    }

    public void request(DisposableObserver<Boolean> progress,DisposableObserver<String> showInfo) {

        request.subscribe(new DisposableSingleObserver<List<RetrofitModel>>() {
            @Override
            protected void onStart() {
                super.onStart();
                progress.onNext(true);
            }

            @Override
            public void onSuccess(List<RetrofitModel> retrofitModels) {
                StringBuilder sb = new StringBuilder();

                sb.append("\n Size = " + retrofitModels.size()+
                        "\n-----------------");
                for (RetrofitModel curModel : retrofitModels) {
                    modelList.add(curModel);
                    sb.append(
                            "\nLogin = " + curModel.getLogin() +
                                    "\nId = " + curModel.getId() +
                                    "\nURI = " + curModel.getAvatarUrl() +
                                    "\n-----------------");
                }
                showInfo.onNext(sb.toString());
                progress.onNext(false);
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                showInfo.onNext(e.getLocalizedMessage());
                progress.onNext(false);
            }
        });
    }
}
