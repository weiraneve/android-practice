package com.thoughtworks.android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class RxJavaActivity : AppCompatActivity() {

    companion object {
        private const val ONE_SECOND = 1000L
    }

    private val compositeDisposable = CompositeDisposable()
    private val buttonCount: Button by lazy { findViewById(R.id.button_rxjava) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.rx_java_layout)
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
        return Observable.create { emitter ->
            var i = 0
            while (i < 10) {
                SystemClock.sleep(ONE_SECOND)
                emitter.onNext(++i)
            }
            emitter.onComplete()
        }
    }
}