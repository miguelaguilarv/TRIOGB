package com.vocesdelolimpo.triogb;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.vocesdelolimpo.triogb.Juegos.BreakOutFragment;
import com.vocesdelolimpo.triogb.Juegos.GatoFragment;
import com.vocesdelolimpo.triogb.Juegos.OrdenFragment;

public class ViewPageAdapter extends FragmentStateAdapter {

    public ViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position){
        switch (position){

            case 0:
                return new OrdenFragment();
            case 1:
                return new GatoFragment();
            case 2:
                return new BreakOutFragment();
            default:
                return new GatoFragment();
        }

    }

    @Override
    public int getItemCount(){
        return 3;

    }
}
