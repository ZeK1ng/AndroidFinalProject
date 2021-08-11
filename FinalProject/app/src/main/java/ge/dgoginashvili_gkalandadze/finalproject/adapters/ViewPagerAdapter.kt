package ge.dgoginashvili_gkalandadze.finalproject.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import ge.dgoginashvili_gkalandadze.finalproject.fragment.MainPageFragment
import ge.dgoginashvili_gkalandadze.finalproject.fragment.ProfilePageFragment


class ViewPagerAdapter(activity: FragmentActivity): FragmentStateAdapter(activity) {

    var fragmentsList = arrayListOf(MainPageFragment(),ProfilePageFragment())

    override fun getItemCount(): Int {
        return fragmentsList.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentsList[position] as Fragment
    }

}