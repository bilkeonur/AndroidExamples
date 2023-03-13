package com.ob.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.ob.recyclerview.databinding.RowAnimalBinding;
import java.util.ArrayList;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.AnimalHolder>
{
    ArrayList<Animal> animals;

    public AnimalAdapter(ArrayList<Animal> animals)
    {
        this.animals = animals;
    }

    @NonNull
    @Override
    public AnimalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        RowAnimalBinding rowAnimalBinding = RowAnimalBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AnimalHolder(rowAnimalBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AnimalHolder holder, int position)
    {
        holder.binding.imgAnimal.setImageResource(animals.get(position).getImage());
        holder.binding.txtAnimal.setText(animals.get(position).getName());

        holder.itemView.setOnClickListener(view -> {
            Toast.makeText(holder.itemView.getContext(),"OK",Toast.LENGTH_LONG).show();
        });
    }

    @Override
    public int getItemCount()
    {
        return animals.size();
    }

    static class AnimalHolder extends RecyclerView.ViewHolder
    {
        RowAnimalBinding binding;

        public AnimalHolder(RowAnimalBinding binding)
        {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
