package minervapg.project.superheroapp

import com.google.gson.annotations.SerializedName

data class SuperHeroDataResponse(@SerializedName("response") val isWorking: String)