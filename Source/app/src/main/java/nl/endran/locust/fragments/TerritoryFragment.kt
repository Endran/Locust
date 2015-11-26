/*
 * Copyright (c) 2015 by David Hardy. Licensed under the Apache License, Version 2.0.
 */

package nl.endran.locust.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import nl.endran.locust.R
import rx.lang.kotlin.toObservable

class TerritoryFragment : Fragment() {

    companion object Factory {
        fun createInstance(): TerritoryFragment = TerritoryFragment()
    }

    lateinit var textView: TextView

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_territory, container, false)

        textView = view.findViewById(R.id.textView) as TextView


        //        val testObservable = observable<Byte> { subscriber ->
        //            "This is my first RxKotlin Test".toByteArray().toObservable().subscribe(subscriber)
        //        }
        //
        //        testObservable.subscribe {
        //            val message = "" + it
        //            Log.i("TEST", message)
        //            textView.setText(message)
        //        }


        "This is my first RxKotlin Test!".toByteArray().toObservable() // Created the observable that will emit each byte
                .map { it.toChar() } // Maps each byte to char
                .buffer(1024) // Collect 1024 items and emits it for every 1024 items, or when the stream ends
                .map { String(it.toCharArray()) } // Maps a char array to String
                .flatMap { it.split(' ').toObservable() } // Split the string
                .buffer (100) // Collected all 100 splitted items
                .map { it.reduce { x: String, y: String -> "$y-+-$x" } } // map the array of string via reduce to one string
                .subscribe { textView.text = it } // Show all incoming results on the UI

        //        Observable.from("This is my first RxTest".toCharList())
        //                //                .delay(4, TimeUnit.SECONDS)
        //                .observeOn(AndroidSchedulers.mainThread())
        //                .
        //                .subscribe {
        //                    val message = "" + it
        //                    Log.i("TEST", message)
        //                    textView.setText(message)
        //                }


        //                .subscribe {
        //                    object : Subscriber<Char>() {
        //
        //                        var message = ""
        //
        //                        override fun onNext(t: Char?) {
        //                            message += t!!
        //                            Log.i("TEST", message)
        //                        }
        //
        //                        override fun onError(e: Throwable?) {
        //                            throw UnsupportedOperationException()
        //                        }
        //
        //                        override fun onCompleted() {
        //                            Log.i("TEST", "final " +  message)
        //                            textView.setText(message)
        //                        }
        //                    }
        //                }

        return view
    }
}
