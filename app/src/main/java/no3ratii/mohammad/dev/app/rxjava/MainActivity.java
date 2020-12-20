package no3ratii.mohammad.dev.app.rxjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import no3ratii.mohammad.dev.app.rxjava.Coments.ActivityComments;
import no3ratii.mohammad.dev.app.rxjava.retrufit.ActivitySearch;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private User user = null;
  private List<User> userList = new ArrayList<User>();
  private TextView txt;
  private TextView txt1;
  private Button btnCommend;
  private TextView btnSearch;
  private iTimer iTimer;
  private TaskSample task = new TaskSample("dog", true, 1);

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    btnCommend = (Button) findViewById(R.id.btnCommend);
    btnSearch = (Button) findViewById(R.id.btnSearch);
    btnSearch.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext() , ActivitySearch.class);
        startActivity(intent);
      }
    });

    btnCommend.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext() , ActivityComments.class);
        startActivity(intent);
      }
    });

    txt = (TextView) findViewById(R.id.txt);
    txt1 = (TextView) findViewById(R.id.txt1);


//    test();
//    observerTest();
    observerDelaySecund();
//    observerDelayMin();
//    createTest();
//    timerDelay();
//      fromList();
//    fromArrayList();
//    delayTest();
//    delayTest1();
    intrfaceTest();
//    resiveDataTest();
  }

  private void resiveDataTest() {
    List<Task> taskList = new ArrayList<>();
    taskList.add(new Task("Take out the trash", true, 3));
    taskList.add(new Task("Walk the dog", false, 2));
    taskList.add(new Task("Make my bed", true, 1));
    taskList.add(new Task("Unload the dishwasher", false, 0));
    taskList.add(new Task("Make dinner", true, 5));

    Observable<Task> dataTest = Observable
      .fromIterable(taskList)
      .filter(new Predicate<Task>() {
        @Override
        public boolean test(Task task) throws Exception {
          return task.getDescription().toLowerCase().startsWith("w");
        }
      })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    dataTest.subscribe(new Observer<Task>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Task task) {
        Log.i("MONO", "onNext: " + task.getDescription());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });

  }

  private void intrfaceTest() {

    ReseveData reseveData = new ReseveData() {
      @Override
      public void test(@NotNull ArrayList<Task> tt) {
        txt1.setText(txt1.getText() + tt.get(1).getDescription());
        Log.i("MONO", tt.get(1).getDescription());
      }
    };
    reseveData.test();


    iTimer = new iTimer() {
      @Override
      public void timer(Long item) {
        txt.setText("" + ++item);
      }

      @Override
      public void TaskList(ArrayList<Task> taskArrayList) {
        txt1.setText(taskArrayList.get(1).getDescription());
      }
    };
  }

  private void delayTest1() {
    Observable fetchWeatherInterval = Observable
      .interval(3, TimeUnit.SECONDS)
      .map(new Function<Long, Task>() {
        @Override
        public Task apply(Long aLong) throws Exception {
          Task task = new Task("test", false, 1);
          return task;
        }
      })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    fetchWeatherInterval.subscribe(new Observer<Task>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Task task) {
        Log.i("MONO", "onNext: " + task.getDescription());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void delayTest() {
    Observable textAnimationDisposable = Observable.fromArray("A", "B", "C")
      .concatMap(string ->
        Observable.merge(
          Observable.just(string),
          Observable.just("").delay(1500, TimeUnit.MILLISECONDS)
        )
      )
//          .concatWith(Observable.<String>empty().delay(500, TimeUnit.MILLISECONDS))
      .observeOn(AndroidSchedulers.mainThread())
      .subscribeOn(Schedulers.newThread());

    textAnimationDisposable.subscribe(new Observer<String>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(String s) {
        Log.i("MONO", "onNext: : " + s);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void fromArrayList() {
    List<Task> taskList = new ArrayList<>();
    taskList.add(new Task("Take out the trash", true, 3));
    taskList.add(new Task("Walk the dog", false, 2));
    taskList.add(new Task("Make my bed", true, 1));
    taskList.add(new Task("Unload the dishwasher", false, 0));
    taskList.add(new Task("Make dinner", true, 5));

    Observable<Task> taskObservable = Observable
      .fromIterable(taskList)
//        .concatWith(Observable.<Task>empty().delay(500, TimeUnit.MILLISECONDS))
//        .filter(new Predicate<Task>() {
//          @Override
//          public boolean test(Task s) throws Exception {
//            return s.getDescription().toLowerCase().startsWith("m");
//          }
//        })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    taskObservable.subscribe(new Observer<Task>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Task task) {
        Log.d(TAG, "onNext: : " + task.getDescription());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void fromList() {

    Task[] list = new Task[5];
    list[0] = (new Task("Take out the trash", true, 3));
    list[1] = (new Task("Walk the dog", false, 2));
    list[2] = (new Task("Make my bed", true, 1));
    list[3] = (new Task("Unload the dishwasher", false, 0));
    list[4] = (new Task("Make dinner", true, 5));

    Observable<Task> taskObservable = Observable
      .fromArray(list)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    taskObservable.subscribe(new Observer<Task>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Task task) {
        Log.d(TAG, "onNext: : " + task.getDescription());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }


  long curentTime = 0;

  private void timerDelay() {

    Observable<Long> timeObserve = Observable
      .timer(3, TimeUnit.SECONDS)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    timeObserve.subscribe(new Observer<Long>() {
      @Override
      public void onSubscribe(Disposable d) {
        curentTime = System.currentTimeMillis() / 1000;
//        Log.i(TAG , "1" + curentTime);
      }

      @Override
      public void onNext(Long aLong) {
//        Log.i(TAG , "System.currentTimeMillis()" + System.currentTimeMillis());
        Log.d(TAG, "onNext: " + ((System.currentTimeMillis() / 1000) - curentTime) + " seconds have elapsed.");
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void createTest() {

// Create the Observable
    Observable<TaskSample> singleTaskObservable = Observable
      .interval(1, TimeUnit.SECONDS)
      .create(new ObservableOnSubscribe<TaskSample>() {
        @Override
        public void subscribe(ObservableEmitter<TaskSample> emitter) throws Exception {
          if (!emitter.isDisposed()) {
            emitter.onNext(task);
          }
          // Once the loop is complete, call the onComplete() method
          if (!emitter.isDisposed()) {
            emitter.onComplete();
          }
        }
      })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

// Subscribe to the Observable and get the emitted object
    singleTaskObservable.subscribe(new Observer<TaskSample>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(TaskSample task) {
        Log.d(TAG, "onNext: single task: " + task.getWalk_the_dog());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void observerDelaySecund() {

    Observable<Long> intervalObservable = Observable
      .interval(1, TimeUnit.SECONDS)
      .subscribeOn(Schedulers.io())
//      .takeWhile(new Predicate<Long>() { // stop the process if more than 5 seconds passes
//        @Override
//        public boolean test(Long aLong) throws Exception {
//          return aLong <= 5;
//        }
//      })
      .observeOn(AndroidSchedulers.mainThread());

    intervalObservable.subscribe(new Observer<Long>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(Long aLong) {
        iTimer.timer(aLong);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void observerDelayMin() {

    Observable<Long> intervalObservable =
      Observable
        .range(1, 4)
        .repeat(2)
        .interval(1, TimeUnit.MINUTES)
        .subscribeOn(Schedulers.io())
        .takeWhile(new Predicate<Long>() { // stop the process if more than 5 seconds passes
          @Override
          public boolean test(Long aLong) throws Exception {
            return aLong <= 4;
          }
        })
        .observeOn(AndroidSchedulers.mainThread());

    intervalObservable.subscribe(new Observer<Long>() {
      @Override
      public void onSubscribe(Disposable d) {
      }

      @Override
      public void onNext(Long aLong) {
        txt1.setText("" + aLong);
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private void observerTest() {
    for (int i = 0; i < 10; i++) {
      User user = new User();
      user.name = "test" + i;
      userList.add(user);
    }

    Flowable<List<User>> animalsObservable1 = getAnimalsObservable().toFlowable(BackpressureStrategy.BUFFER);

    Observable<List<User>> animalsObservable = getAnimalsObservable();

    DisposableObserver<List<User>> animalsObserver = getAnimalsObserver();

    DisposableObserver<List<User>> animalsObserverAllCaps = getAnimalsAllCapsObserver();


    compositeDisposable.add(
      animalsObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(animalsObserver));

    compositeDisposable.add(
      animalsObservable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
//        .filter(new Predicate<String>() {
//          @Override
//          public boolean test(String s) throws Exception {
//            return s.toLowerCase().startsWith("c");
//          }
//        })
//        .map(new Function<String, String>() {
//          @Override
//          public String apply(String s) throws Exception {
//            return s.toUpperCase();
//          }
//        })
        .subscribeWith(animalsObserverAllCaps));
  }

  private void test() {

    Observable<User> singleTaskObservable = Observable
      .create(new ObservableOnSubscribe<User>() {
        @Override
        public void subscribe(ObservableEmitter<User> emitter) throws Exception {
          if (!emitter.isDisposed()) {
            emitter.onNext(userList.get(1));
            emitter.onComplete();
          }
        }
      })
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    singleTaskObservable.subscribe(new Observer<User>() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(User task) {
        Log.d(TAG, "onNext: single task: " + task.name());
      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }

  private DisposableObserver<List<User>> getAnimalsObserver() {
    return new DisposableObserver<List<User>>() {

      @Override
      public void onNext(List<User> users) {
        Log.d(TAG, "Name: " + users);
      }

      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
      }

      @Override
      public void onComplete() {
        Log.d(TAG, "All items are emitted!");
      }
    };
  }

  private DisposableObserver<List<User>> getAnimalsAllCapsObserver() {
    return new DisposableObserver<List<User>>() {
      @Override
      public void onNext(List<User> s) {
        Log.d(TAG, "Name: " + s);
      }

      @Override
      public void onError(Throwable e) {
        Log.e(TAG, "onError: " + e.getMessage());
      }

      @Override
      public void onComplete() {
        Log.d(TAG, "All items are emitted!");
      }
    };
  }

  private Observable<List<User>> getAnimalsObservable() {
    return Observable.fromArray(userList);
  }


  @Override
  protected void onDestroy() {
    super.onDestroy();

    // don't send events once the activity is destroyed
    compositeDisposable.clear();
  }

  private void testFrom() {
    List<Task> taskList = new ArrayList<>();
    taskList.add(new Task("Take out the trash", true, 3));
    taskList.add(new Task("Walk the dog", false, 2));
    taskList.add(new Task("Make my bed", true, 1));
    taskList.add(new Task("Unload the dishwasher", false, 0));
    taskList.add(new Task("Make dinner", true, 5));


    Observable<List<Task>> tast = Observable.just(taskList)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread());

    tast.subscribe(new Observer() {
      @Override
      public void onSubscribe(Disposable d) {

      }

      @Override
      public void onNext(Object o) {

      }

      @Override
      public void onError(Throwable e) {

      }

      @Override
      public void onComplete() {

      }
    });
  }
}