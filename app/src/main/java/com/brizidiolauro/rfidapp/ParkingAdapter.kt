package com.brizidiolauro.rfidapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.brizidiolauro.rfidapp.databinding.ItemRegisterBinding

class ParkingAdapter : ListAdapter<VehicleDTO, ParkingAdapter.VehicleListViewHolder>(
    object : DiffUtil.ItemCallback<VehicleDTO>(){
        override fun areItemsTheSame(oldItem: VehicleDTO, newItem: VehicleDTO): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: VehicleDTO, newItem: VehicleDTO): Boolean = oldItem.code.equals(newItem.code)
    }
) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleListViewHolder {
        return VehicleListViewHolder(ItemRegisterBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: VehicleListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class VehicleListViewHolder(private val item : ItemRegisterBinding)
        : RecyclerView.ViewHolder(item.root){

        fun bind(vehicle : VehicleDTO){
            item.vehicle = vehicle
            if(vehicle.isAuthorized)
                item.textIsAuthorized.setText("Autorizado")
            else
                item.textIsAuthorized.setText("Acesso Negado")
        }
    }


}