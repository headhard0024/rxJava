package no3ratii.mohammad.dev.app.rxjava.retrufit;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;

public interface EmptyRequestApi {

  @GET("todos/1")
  Flowable<ResponseBody> makeQuery();
}
