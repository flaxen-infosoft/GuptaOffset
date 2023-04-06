package com.flaxeninfosoft.guptaoffset.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentImageViewBinding;

public class ImageViewFragment extends Fragment {

    private FragmentImageViewBinding binding;

    public ImageViewFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_image_view, container, false);

        String imageLink = getArguments().getString("IMAGE", null);

        if (imageLink == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {

            Glide.with(getContext()).load(imageLink).placeholder(R.drawable.loading_image).into(binding.imageViewImage);
        }

        return binding.getRoot();
    }
}