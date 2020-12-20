package no3ratii.mohammad.dev.app.rxjava.retrufit;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class Repository {

  private static Repository instance;

  public static Repository getInstance(){
    if(instance == null){
      instance = new Repository();
    }
    return instance;
  }


  public LiveData<ResponseBody> makeReactiveQuery(){
    return LiveDataReactiveStreams.fromPublisher(EmptyServiceGenerator.getRequestApi()
      .makeQuery()
      .subscribeOn(Schedulers.io()));
  }
}