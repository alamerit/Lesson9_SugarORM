package geekbrains.ru.lesson5_sugarorm;

import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import geekbrains.ru.lesson5_sugarorm.dagger.AppComponent;
import geekbrains.ru.lesson5_sugarorm.retrofit.RetrofitModel;
import geekbrains.ru.lesson5_sugarorm.room.RoomModel;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class Presenter {
    @Inject
    Single<List<RetrofitModel>> request;
    @Inject
    Model model;

    DisposableObserver<Boolean> progress;
    DisposableObserver<String> showInfo;

    public Presenter(AppComponent component){

        component.inject(this);
        OrmApp.getModelComponent().inject(this);
    }

    void bindView(DisposableObserver<Boolean> progress, DisposableObserver<String> showInfo){
        this.progress = progress;
        this.showInfo = showInfo;
    }

    void unbindView(){
        progress.dispose();
        showInfo.dispose();
    }

    private DisposableSingleObserver<Bundle> CreateObserver() {
        return new DisposableSingleObserver<Bundle>() {
            @Override
            protected void onStart() {
                super.onStart();
                progress.onNext(true);
                showInfo.onNext("");
            }
            @Override
            public void onSuccess(@NonNull Bundle bundle) {
                progress.onNext(false);
                showInfo.onNext("количество = " + bundle.getInt("count") +
                        "\n милисекунд = " + bundle.getLong("msek"));
            }
            @Override
            public void onError(@NonNull Throwable e) {
                progress.onNext(false);
                showInfo.onNext("ошибка БД: " + e.getMessage());
            }
        };
    }

    void saveAllRoom() {
        Single<Bundle> singleSaveAllRoom = Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            String curLogin = "";
            String curUserID = "";
            String curAvatarUrl = "";
            Date first = new Date();
            List<RoomModel> roomModelList = new ArrayList<>();
            RoomModel roomModel = new RoomModel();
            for (RetrofitModel curItem : model.modelList) {
                curLogin = curItem.getLogin();
                curUserID = curItem.getId();
                curAvatarUrl = curItem.getAvatarUrl();
                roomModel.setLogin(curLogin);
                roomModel.setAvatarUrl(curAvatarUrl);
                roomModel.setUserId(curUserID);
                roomModelList.add(roomModel);
            }
            OrmApp.get().getDB().productDao().insertAll(roomModelList);
            Date second = new Date();
            List<RoomModel> tempList = OrmApp.get().getDB().productDao().getAll();
            Bundle bundle = new Bundle();
            bundle.putInt("count", tempList.size());
            bundle.putLong("msek", second.getTime() - first.getTime());
            emitter.onSuccess(bundle);
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        singleSaveAllRoom.subscribeWith(CreateObserver());
    }

    void selectAllRoom(){
        Single<Bundle> singleSelectAllRoom = Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                Date first = new Date();
                List<RoomModel> products = OrmApp.get().getDB().productDao().getAll();
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", products.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        singleSelectAllRoom.subscribeWith(CreateObserver());
    }

    void deleteAllRoom(){
        Single<Bundle> singleDeleteAllRoom = Single.create((SingleOnSubscribe<Bundle>) emitter -> {
            try {
                List<RoomModel> products = OrmApp.get().getDB().productDao().getAll();
                Date first = new Date();
                OrmApp.get().getDB().productDao().deleteAll();
                Date second = new Date();
                Bundle bundle = new Bundle();
                bundle.putInt("count", products.size());
                bundle.putLong("msek", second.getTime() - first.getTime());
                emitter.onSuccess(bundle);
            } catch (Exception e) {
                emitter.onError(e);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        singleDeleteAllRoom.subscribeWith(CreateObserver());
    }

//    private boolean checkConnection() {
//        NetworkInfo networkInfo = networkComponent.getNetwork();
//
//        if (networkInfo != null && networkInfo.isConnected()) return true;
//        Toast.makeText(this, "Подключите интернет", Toast.LENGTH_SHORT).show();
//        return false;
//    }

    public void onLoad() {
        showInfo.onNext("");
        model.modelList.clear();
//        if (!checkConnection()) return;
        model.request(progress,showInfo);


    }

    void saveAllSugar(){
        OrmApp.makeSugarComponent(model.modelList).sugarSaveAll().subscribeWith(CreateObserver());
    }

    void deleteAllSugar(){
        OrmApp.makeSugarComponent(model.modelList).deleteAll().subscribeWith(CreateObserver());
    }

    void selectAllSugar(){
        OrmApp.makeSugarComponent(model.modelList).getAll().subscribeWith(CreateObserver());
    }
}
