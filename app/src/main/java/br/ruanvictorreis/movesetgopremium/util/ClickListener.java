package br.ruanvictorreis.movesetgopremium.util;

import android.view.View;

/**
 * Click Util Listener
 * Created by Ruan on 29/05/2016.
 */
public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}