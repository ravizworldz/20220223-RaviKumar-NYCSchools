package com.schools.nycschools.ui.adapter

import com.schools.nycschools.model.SchoolListModelItem

interface ISchoolRowClickListener {
    fun onSchoolRowClick(schoolListModelItem: SchoolListModelItem)
    fun onEmailClick(schoolListModelItem: SchoolListModelItem)
    fun onPhoneClick(schoolListModelItem: SchoolListModelItem)
    fun onWebsiteClick(schoolListModelItem: SchoolListModelItem)
}