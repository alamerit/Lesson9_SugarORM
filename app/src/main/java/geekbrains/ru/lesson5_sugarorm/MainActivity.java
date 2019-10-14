package geekbrains.ru.lesson5_sugarorm;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.orm.SugarContext;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.observers.DisposableObserver;

public class MainActivity extends AppCompatActivity{
    private TextView mInfoTextView;
    private ProgressBar progressBar;

    @Inject
    Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OrmApp.getPresenterComponent().inject(this);
        setContentView(R.layout.activity_main);
        mInfoTextView = (TextView) findViewById(R.id.tvLoad);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        ((Button) findViewById(R.id.btnLoad)).setOnClickListener((view)->presenter.onLoad());
        ((Button) findViewById(R.id.btnSaveAllSugar)).setOnClickListener((view)-> presenter.saveAllSugar());
        ((Button) findViewById(R.id.btnSelectAllSugar)).setOnClickListener((view)-> presenter.selectAllSugar());
        ((Button) findViewById(R.id.btnDeleteAllSugar)).setOnClickListener((view)-> presenter.deleteAllSugar());

        findViewById(R.id.btnSaveAllRoom).setOnClickListener((v)->presenter.saveAllRoom());
        findViewById(R.id.btnSelectAllRoom).setOnClickListener((v)->presenter.selectAllRoom());
        findViewById(R.id.btnDeleteAllRoom).setOnClickListener((v)->presenter.deleteAllRoom());
        SugarContext.init(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        DisposableObserver<String> showInfo = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                mInfoTextView.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                mInfoTextView.setText(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };

        DisposableObserver<Boolean> progress = new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean visible) {
                progressBar.setVisibility(visible?View.VISIBLE: View.GONE);
            }

            @Override
            public void onError(Throwable e) {
                mInfoTextView.setText(e.getLocalizedMessage());
            }

            @Override
            public void onComplete() {

            }
        };
        presenter.bindView(progress,showInfo);
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.unbindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SugarContext.terminate();
    }

}
