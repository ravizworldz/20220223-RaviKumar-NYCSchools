package com.schools.nycschools.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.schools.nycschools.R
import com.schools.nycschools.databinding.SchoolRecyclerListRowBinding
import com.schools.nycschools.model.SchoolListModelItem

class SchoolListAdapter(val clickListener: ISchoolRowClickListener) : RecyclerView.Adapter<SchoolListAdapter.SchoolAdapterViewHolder>() {

    private var listData: List<SchoolListModelItem>? = null

    fun setUpdatedData(listData: List<SchoolListModelItem>) {
        this.listData = listData
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SchoolAdapterViewHolder(
        DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.school_recycler_list_row, parent, false)
    )

    override fun getItemCount(): Int {
        if(listData == null)return 0
        else return listData?.size!!
    }

    override fun onBindViewHolder(holder: SchoolAdapterViewHolder, position: Int) {
        holder.bind(listData?.get(position)!!)
    }

    inner class SchoolAdapterViewHolder(val view: SchoolRecyclerListRowBinding): RecyclerView.ViewHolder(view.root) {
        fun bind(data: SchoolListModelItem) {
            view.schoolModelItem = data
            view.clickListener = clickListener
            view.executePendingBindings()
        }
    }
}