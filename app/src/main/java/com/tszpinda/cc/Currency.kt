package com.tszpinda.cc
import android.databinding.BaseObservable
import android.databinding.Bindable


data class Currency(val name:String, val symbol:String)

data class Conversion(val from:Currency, val to:Currency, val rate:Float)

//data class Location(var name:String)


data class Location(private var _name: String) : BaseObservable() {


    var name: String
        @Bindable get() = _name
        set(value) {
            _name = value
            System.err.println("name:set:"+_name)
            notifyPropertyChanged(BR.currentLocation)
            notifyChange()
        }


}