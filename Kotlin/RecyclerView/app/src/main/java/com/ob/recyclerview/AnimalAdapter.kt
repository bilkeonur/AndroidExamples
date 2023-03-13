package com.ob.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ob.recyclerview.databinding.RowAnimalBinding

class AnimalAdapter(private val animals: ArrayList<Animal>): RecyclerView.Adapter<AnimalAdapter.AnimalHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimalHolder
    {
        val binding = RowAnimalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  AnimalHolder(binding)
    }

    override fun getItemCount(): Int
    {
        return animals.size
    }

    override fun onBindViewHolder(holder: AnimalHolder, position: Int)
    {
        holder.binding.txtAnimal.text = animals[position].name
        holder.binding.imgAnimal.setImageResource(animals[position].image)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "OK", Toast.LENGTH_LONG).show()
        }
    }

    class AnimalHolder(val binding: RowAnimalBinding): RecyclerView.ViewHolder(binding.root)
}