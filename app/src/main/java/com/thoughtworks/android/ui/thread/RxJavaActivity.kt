package com.thoughtworks.android.ui.thread

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.thoughtworks.android.R
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit

class RxJavaActivity : AppCompatActivity() {

    private var count: Int = 0

    private val compositeDisposable = CompositeDisposable()
    private val buttonCount: Button by lazy { findViewById(R.id.button_rxjava) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)
        initUI()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun initUI() {
        buttonCount.setOnClickListener {
            startCount()
            buttonCount.isEnabled = false
        }
    }

    private fun startCount() {
        countIncrease()
            .concatWith(countDecrease())
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Int> {
                override fun onSubscribe(d: Disposable) {
                    compositeDisposable.add(d)
                }
                override fun onNext(t: Int) {
                    buttonCount.text = String.format(Locale.getDefault(), "the num is %d", t)
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(this@RxJavaActivity, e.message, Toast.LENGTH_SHORT).show()
                }

                override fun onComplete() {
                    buttonCount.isEnabled = true
                }
            })
    }

    private fun countIncrease(): Observable<Int> {
        return Observable.interval(0, 1, TimeUnit.SECONDS)
            .take(10)
            .flatMap {
                return@flatMap Observable.create { emitter ->
                    emitter.onNext(++count)
                    emitter.onComplete()
                }
            }
    }

    private fun countDecrease(): Observable<Int> {
        return Observable.interval(1, 1, TimeUnit.SECONDS)
            .take(10)
            .flatMap {
                return@flatMap Observable.create { emitter ->
                    emitter.onNext(--count)
                    emitter.onComplete()
                }
            }
    }

}