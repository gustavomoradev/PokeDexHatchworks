package com.moradev.pokedexhatchworks.ui.main

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.palette.graphics.Palette
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.chip.Chip
import com.moradev.pokedexhatchworks.R
import com.moradev.pokedexhatchworks.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private val mainViewModel:MainViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pokemonSelected = arguments?.getString("pokemonSelected")
        init(pokemonSelected!!)


    }

    private fun init(pokemonSelected: String) {
        setUpView(pokemonSelected)
        setUpEvents()
    }

    private fun setUpEvents() {

    }


    private fun setUpView(pokemonSelected: String) {
        binding.txvName.text = pokemonSelected.replaceFirstChar { pokemonSelected.substring(0,1).uppercase() }

        mainViewModel.pokemonDetailsResponse.observe(viewLifecycleOwner){pokeDetails ->
            if(pokeDetails!=null) {
                "${pokeDetails.height}m".also { binding.txvHeight.text = it }
                "${pokeDetails.weight}kg".also { binding.txvWeight.text = it }

                var chip:Chip
                pokeDetails.types.forEach {slot ->
                    chip = Chip(requireContext())
                        .also {
                            it.text = slot.type.name
                            it.chipBackgroundColor =  ColorStateList.valueOf( ContextCompat.getColor(requireContext(), getColorResId(slot.type.name)))
                            it.chipStrokeColor = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.grayLt))
                            it.setTextColor( ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)))
                            it.shapeAppearanceModel.toBuilder().build().withCornerSize(27f)
                        }
                    binding.chipTypes.addView(chip)
                }

                binding.ivPhoto.apply {
                    val image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${pokeDetails.id}.png"

                    Glide.with(requireContext())
                        .asBitmap()
                        .load(Uri.parse(image))
                        .error(AppCompatResources.getDrawable(requireContext(), R.drawable.ic_block))

                        .listener(object : RequestListener<Bitmap> {
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Bitmap>,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }



                            override fun onResourceReady(
                                resource: Bitmap,
                                model: Any,
                                target: Target<Bitmap>?,
                                dataSource: DataSource,
                                isFirstResource: Boolean
                            ): Boolean {

                                runCatching {
                                    val palette = Palette.from(resource).generate()
                                    val vibrant = palette.lightVibrantSwatch
                                    binding.topContainer.backgroundTintList = ColorStateList.valueOf(vibrant!!.rgb)
                                }

                                return false
                            }

                        })

                        .into(binding.ivPhoto)


                    setOnClickListener {
                        val mediaPlayer = MediaPlayer()
                        runCatching {
                            mediaPlayer.apply {
                                setDataSource(pokeDetails.cries.latest)
                                prepare()
                                start()
                            }
                        }.onFailure {
                            Toast.makeText(requireContext(), "Sorry the pokemon doesn't want to talk", Toast.LENGTH_SHORT).show()
                        }

                    }
                }

            }
        }



        mainViewModel.getPokemonSelectedDetails(pokemonSelected)

    }

    private fun getColorResId(name: String): Int {
        return when(name){
            "fire" -> R.color.red
            "poison" -> R.color.purple_500
            "grass" -> R.color.teal_200
            "flying" -> R.color.teal_700
            "water" -> R.color.light_blue
            "normal" -> R.color.lime
            "bug" -> R.color.greenLt
            else -> R.color.black
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}