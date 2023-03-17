package com.ob.navigation;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.ob.navigation.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment
{
    FragmentFirstBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        Button btnGoToSecond = binding.btnGoToSecond;

        btnGoToSecond.setOnClickListener(view1 -> {
            NavDirections action = FirstFragmentDirections.actionFirstFragmentToSecondFragment();
            Navigation.findNavController(view1).navigate(action);
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        binding = null;
    }
}