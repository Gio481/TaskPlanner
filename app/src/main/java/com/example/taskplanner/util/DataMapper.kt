package com.example.taskplanner.util

interface DataMapper<in MODEL_A, out MODEL_B> {
    fun modelMapper(model: MODEL_A): MODEL_B
    fun modelListMapper(model: List<MODEL_A>): List<MODEL_B> {
        return model.map { modelMapper(it) }
    }
}