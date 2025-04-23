package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import coil.load
import com.example.cocktailranking.R
import com.example.cocktailranking.viewmodel.HomeViewModel
import com.example.cocktailranking.viewmodel.HomeViewModelFactory
import com.example.cocktailranking.data.database.CocktailApp
import coil.request.ImageRequest
import coil.Coil

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    private lateinit var image1: ImageView
    private lateinit var image2: ImageView
    private lateinit var name1: TextView
    private lateinit var name2: TextView
    private lateinit var moreInfo1: Button
    private lateinit var moreInfo2: Button
    private lateinit var buttonSelect1: Button
    private lateinit var buttonSelect2: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        image1 = view.findViewById(R.id.cocktailImage1)
        image2 = view.findViewById(R.id.cocktailImage2)
        name1 = view.findViewById(R.id.cocktailName1)
        name2 = view.findViewById(R.id.cocktailName2)
        moreInfo1 = view.findViewById(R.id.moreInfo1)
        moreInfo2 = view.findViewById(R.id.moreInfo2)
        buttonSelect1 = view.findViewById(R.id.buttonSelect1)
        buttonSelect2 = view.findViewById(R.id.buttonSelect2)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val repository = (requireActivity().application as CocktailApp).repository
        val factory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

        if (viewModel.cocktails.value == null || viewModel.cocktails.value?.size != 2) {
            viewModel.fetchInitialQueue()
        }

        viewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.size == 2) {
                image1.visibility = View.INVISIBLE
                image2.visibility = View.INVISIBLE
                var imagesLoaded = 0

                fun tryDisplayBoth() {
                    if (imagesLoaded == 2) {
                        image1.alpha = 0f
                        image2.alpha = 0f
                        image1.visibility = View.VISIBLE
                        image2.visibility = View.VISIBLE

                        image1.animate().alpha(1f).setDuration(250).start()
                        image2.animate().alpha(1f).setDuration(250).start()
                    }
                }

                val context = requireContext()
                val request1 = ImageRequest.Builder(context)
                    .data(cocktails[0].strDrinkThumb)
                    .target {
                        image1.setImageDrawable(it)
                        imagesLoaded++
                        tryDisplayBoth()
                    }
                    .build()

                val request2 = ImageRequest.Builder(context)
                    .data(cocktails[1].strDrinkThumb)
                    .target {
                        image2.setImageDrawable(it)
                        imagesLoaded++
                        tryDisplayBoth()
                    }
                    .build()

                Coil.imageLoader(context).enqueue(request1)
                Coil.imageLoader(context).enqueue(request2)

                name1.text = cocktails[0].strDrink
                name2.text = cocktails[1].strDrink

                moreInfo1.setOnClickListener {
                    findNavController().navigate(
                        R.id.cocktailDetailFragment,
                        bundleOf("cocktailId" to cocktails[0].idDrink)
                    )
                }

                moreInfo2.setOnClickListener {
                    findNavController().navigate(
                        R.id.cocktailDetailFragment,
                        bundleOf("cocktailId" to cocktails[1].idDrink)
                    )
                }

                buttonSelect1.setOnClickListener {
                    viewModel.resetForVoting()
                    // Later: viewModel.updateElo(winner = cocktails[0], loser = cocktails[1])
                }

                buttonSelect2.setOnClickListener {
                    viewModel.resetForVoting()
                    // Later: viewModel.updateElo(winner = cocktails[1], loser = cocktails[0])
                }
            }
        }
    }
}
