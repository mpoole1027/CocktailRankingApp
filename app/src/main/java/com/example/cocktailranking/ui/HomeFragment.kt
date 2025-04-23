package com.example.cocktailranking.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.core.os.bundleOf
import coil.load
import com.example.cocktailranking.R
import com.example.cocktailranking.viewmodel.HomeViewModel
import coil.request.ImageRequest
import coil.ImageLoader
import coil.Coil

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

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
        if (viewModel.cocktails.value == null || viewModel.cocktails.value?.size != 2) {
            viewModel.fetchInitialQueue()
        }
        viewModel.cocktails.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.size == 2) {
                // Hide images until both are ready
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
                    .target(
                        onSuccess = {
                            image1.setImageDrawable(it)
                            imagesLoaded++
                            tryDisplayBoth()
                        }
                    )
                    .build()

                val request2 = ImageRequest.Builder(context)
                    .data(cocktails[1].strDrinkThumb)
                    .target(
                        onSuccess = {
                            image2.setImageDrawable(it)
                            imagesLoaded++
                            tryDisplayBoth()
                        }
                    )
                    .build()

                Coil.imageLoader(context).enqueue(request1)
                Coil.imageLoader(context).enqueue(request2)

                // Show names immediately or delay them too if you want
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
                    // TODO: Save ELO result
                    viewModel.resetForVoting()
                }

                buttonSelect2.setOnClickListener {
                    // TODO: Save ELO result
                    viewModel.resetForVoting()
                }
            }
        }


    }
}
