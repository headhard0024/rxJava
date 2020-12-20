package no3ratii.mohammad.dev.app.rxjava.retrufit;

import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import no3ratii.mohammad.dev.app.rxjava.R;

public class ActivitySearch extends AppCompatActivity {

  private static final String TAG = "MONO";

  //ui
  private SearchView searchView;

  // vars
  private CompositeDisposable disposables = new CompositeDisposable();
  private long timeSinceLastRequest; // for log printouts only. Not part of logic.

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_activityretrofit);
    searchView = findViewById(R.id.search_view);

    timeSinceLastRequest = System.currentTimeMillis();

    // create the Observable
    Observable<String> observableQueryText = Observable
      .create(new ObservableOnSubscribe<String>() {
        @Override
        public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
          searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
              return false;
            }

            @Override
            public boolean onQueryTextChange(final String newText) {
              if (!emitter.isDisposed()) {
                emitter.onNext(newText); // Pass the query to the emitter
              }
              return false;
            }
          });
        }
      })
      .debounce(1, TimeUnit.SECONDS) // Apply Debounce() operator to limit requests
      .subscribeOn(Schedulers.io());

    Handler handler = new Handler();
    // Subscribe an Observer
    observableQueryText.subscribe(new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {
        disposables.add(d);
      }

      @Override
      public void onNext(String s) {

//        timeSinceLastRequest = System.currentTimeMillis();

        handler.post(new Runnable() {
          @Override
          public void run() {
            Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
          }
        });

        // method for sending a request to the server
        sendRequestToServer(s);
      }

      @Override
      public void onError(Throwable e) {
      }

      @Override
      public void onComplete() {
      }
    });
  }

  // Fake method for sending a request to the server
  private void sendRequestToServer(String query) {
    // do nothing
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    disposables.clear(); // clear disposables
  }
}