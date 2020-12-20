package no3ratii.mohammad.dev.app.rxjava

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class ActivityTest : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test()
    }

    @SuppressLint("CheckResult")
    private fun test() {
            val fetchWeatherInterval: Observable<Task?>? = Observable
                    .interval(3, TimeUnit.SECONDS)
                    .map(object : Function<Long?, Task?> {
                        fun apply(aLong: Long?): Task? {
                            return Task("test", false, 1)
                        }
                        override fun apply(t: Long): Task? {
                            return null
                        }
                    })
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            fetchWeatherInterval?.subscribe {
                    Log.i("MONO", "onNext: " + it?.description)
            }
        }
    }


