package com.example.practicamodulo7.data.remote

import com.example.practicamodulo7.data.remote.model.AlimentoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface AlimentosApi {

    @GET("alimentos")
    suspend fun getAlimentos(): List<AlimentoDto>

    @GET("alimentos/{id}")
    suspend fun getAlimentoDetail(
        @Path("id") id: String?
    ): AlimentoDto

}