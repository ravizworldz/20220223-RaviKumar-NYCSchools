package com.schools.nycschools.model

data class SchoolListModelItem(
    val dbn: String?,
    val phone_number: String?,
    val school_email: String?,
    val website: String?,
    val primary_address_line_1: String?,
    val city: String?,
    val zip: String?,
    val state_code: String?,
    val school_name: String?,
    val overview_paragraph: String?
)