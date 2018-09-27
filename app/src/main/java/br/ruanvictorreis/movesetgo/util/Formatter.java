package br.ruanvictorreis.movesetgo.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Class to format values.
 * Created by Ruan on 16/09/2017.
 */

public class Formatter {

    public static String doubleWithoutDecimals(Double value) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#", otherSymbols).format(value);
    }

    public static String doubleWithDecimals(Double value) {
        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        return new DecimalFormat("#.#", otherSymbols).format(value);
    }
}
