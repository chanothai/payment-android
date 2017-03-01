package com.company.zicure.payment.util;

import java.text.DecimalFormat;

/**
 * Created by 4GRYZ52 on 2/1/2017.
 */

public class FormatCash {
    private static FormatCash me = null;
    private DecimalFormat formatter = null;

    public FormatCash(){
        formatter = new DecimalFormat("#,###.00");
    }
    public static FormatCash newInstance(){
        if (me == null){
            me = new FormatCash();
        }
        return me;
    }

    public String setFormatCash(double number){
        return formatter.format(number);
    }
}
