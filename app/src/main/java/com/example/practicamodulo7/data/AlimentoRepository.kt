package com.example.practicamodulo7.data

import com.example.practicamodulo7.data.remote.AlimentosApi
import com.example.practicamodulo7.data.remote.model.AlimentoDto
import retrofit2.Retrofit

class AlimentoRepository(private val retrofit: Retrofit) {

    private val alimentosApi = retrofit.create(AlimentosApi::class.java)

    suspend fun getAlimentos(): List<AlimentoDto> = alimentosApi.getAlimentos()

    suspend fun getAlimentoDetail(id: String?): AlimentoDto = alimentosApi.getAlimentoDetail(id)

}