package com.example.practicamodulo7.application

import android.app.Application
import com.example.practicamodulo7.data.AlimentoRepository
import com.example.practicamodulo7.data.remote.RetrofitHelper

class PracticaModulo7app: Application() {
    private val retrofit by lazy{
        RetrofitHelper().getRetrofit()
    }

    val repository by lazy{
        AlimentoRepository(retrofit)
    }
}