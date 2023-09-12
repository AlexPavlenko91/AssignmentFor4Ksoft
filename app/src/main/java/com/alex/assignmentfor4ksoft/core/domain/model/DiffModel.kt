package com.alex.assignmentfor4ksoft.core.domain.model

interface DiffModel<T> {

    fun areItemsTheSame(newItem: T): Boolean
    fun areContentsTheSame(newItem: T): Boolean
}