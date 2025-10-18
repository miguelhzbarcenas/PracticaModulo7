package com.example.practicamodulo7.data.remote.model

import com.google.gson.annotations.SerializedName

data class AlimentoDto(
    @SerializedName("id")
    var id: Int? = 1,
    @SerializedName("nombre")
    var nombre: String? = "N/A",
    @SerializedName("calorias")
    var calorias: Int? = 0,
    @SerializedName("proteinas_gr")
    var proteinas: Double? = 0.0,
    @SerializedName("carbohidratos_gr")
    var carbohidratos: Double? = 0.0,
    @SerializedName("grasas_gr")
    var grasas: Double? = 0.0,
    @SerializedName("porcion")
    var porcion: String? = "N/A",
    @SerializedName("imagen_url")
    var imagenUrl: String? = ""
)
