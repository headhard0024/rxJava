package no3ratii.mohammad.dev.app.rxjava.retrufit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class EmptyServiceGenerator {

  public static final String BASE_URL = "https://jsonplaceholder.typicode.com";

  private static Retrofit.Builder retrofitBuilder =
    new Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .addConverterFactory(GsonConverterFactory.create());

  private static Retrofit retrofit = retrofitBuilder.build();

  private static EmptyRequestApi requestApi = retrofit.create(EmptyRequestApi.class);

  public static EmptyRequestApi getRequestApi(){
    return requestApi;
  }
}