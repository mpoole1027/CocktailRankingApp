package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.cocktailranking.R

// HomeFragment displays the home screen layout
class HomeFragment : Fragment()
{

    // Inflate the fragment_home layout for this fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
