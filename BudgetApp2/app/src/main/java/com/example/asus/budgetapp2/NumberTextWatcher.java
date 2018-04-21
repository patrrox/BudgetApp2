package com.example.asus.budgetapp2;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

/**
 * Created by Asus on 25.03.2018.
 */

public class NumberTextWatcher implements TextWatcher {

    private EditText et;

    public NumberTextWatcher(EditText et) {

        this.et = et;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        if (s.equals(""))
        {
            Log.d("aktywnosc","pusty");
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable edt) {

        String temp = edt.toString();
        int posDot = temp.indexOf(".");

        if (posDot <= 0) {
            return;
        }
        if (temp.length() - posDot - 1 > 2) {
            edt.delete(posDot + 3, posDot + 4);
        }


    }
}