package com.suveybesena.shoppingapp.presentation.productfeed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.suveybesena.shoppingapp.databinding.FragmentProductFeedBinding
import com.suveybesena.shoppingapp.presentation.ProductsViewPagerAdapter
import com.suveybesena.shoppingapp.presentation.productfeed.eyeliner.EyelinerFragment
import com.suveybesena.shoppingapp.presentation.productfeed.eyeshadow.EyeshadowFragment
import com.suveybesena.shoppingapp.presentation.productfeed.lipstick.LipstickFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_product_feed.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProductFeedFragment : Fragment() {

    private val viewModel: ProductFeedViewModel by viewModels()
    private lateinit var binding: FragmentProductFeedBinding
    lateinit var productAdapter: ProductFeedAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductFeedBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       //setupRecyclerView()
       //observeData()

        //ViewPager için adapter nesnesi oluşturuyoruz ve kullanacağımız fragment, title'ları ekliyoruz.
        val adapter = ProductsViewPagerAdapter(this)
        //Adapter'ımızdaki verileri viewPager adapter'a veriyoruz
        binding.vpProductFeed.adapter = adapter
        //Tablar arasında yani viewPager'lar arasında geçisi sağlıyoruz
        TabLayoutMediator(binding.tabs,binding.vpProductFeed
        ) { tab, position -> if (position==0){tab.text="Lipstick"}
        else if(position ==1) {tab.text ="Eyeshadow"}
        else {tab.text ="Eyeliner"}

        }.attach()


    }


   //private fun observeData() {
   //    viewModel._uiState.observe(viewLifecycleOwner) { state ->
   //        if (state.productItems!=null){
   //            productAdapter.differ.submitList(state.productItems)
   //        }

   //    }
   //}

   //private fun setupRecyclerView() {
   //    productAdapter = ProductFeedAdapter()
   //    binding.rvShopping.adapter = productAdapter
   //    binding.rvShopping.layoutManager = GridLayoutManager(requireContext(), 2)
   //}
}