package geekbrains.ru.lesson5_sugarorm;

import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import geekbrains.ru.lesson5_sugarorm.dagger.NetworkComponent;
import geekbrains.ru.lesson5_sugarorm.dagger.NetworkModule;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

class ExampleClass{
    int add(int a, int b){
        return a + b;
    }
}

@RunWith(MockitoJUnitRunner.class)
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, new ExampleClass().add(2,2));
    }

    @Test
    public void getNetworkInfo_null_activity() {
        NetworkModule moduleReal = new NetworkModule(Mockito.mock(Activity.class));
//        NetworkModule module = Mockito.mock(NetworkModule.class);
        assertEquals(null, moduleReal.getNetworkInfo());
    }

    @Test
    public void getNetworkInfo_correct() {
        Activity activity = Mockito.mock(Activity.class);

        ConnectivityManager conManager = Mockito.mock(ConnectivityManager.class);

        Mockito.when(activity.getSystemService(Mockito.anyString()))
                .thenReturn(conManager);

        NetworkModule moduleReal = new NetworkModule(activity);
        //        NetworkModule module = Mockito.mock(NetworkModule.class);
        NetworkInfo info = moduleReal.getNetworkInfo();
        Mockito.verify(conManager).getActiveNetworkInfo();
//        assertEquals(null, moduleReal.getNetworkInfo());
    }
}