package com.example.proyectob_pmdm_t2_kaiscervasquez;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class DialogFilter extends DialogFragment {

    OnFiltersListener onFilter;
    Spinner spDistrict;
    String district;

    @NonNull
    @Override
    public Dialog onCreateDialog(@NonNull Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_filter, null);
        spDistrict = view.findViewById(R.id.spnFilter);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        builder.setTitle(R.string.spnFilter)
        .setPositiveButton(R.string.btn_accept, null)
        .setNegativeButton(R.string.btn_cancel, (dialog, which) -> {
            dismiss();
        });

        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);

        dialog.setOnShowListener(dialogInterface -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(btn -> {
                district = spDistrict.getSelectedItem().toString();
                onFilter.onFilterDistrict(district);
                dismiss();
            });
        });


        return dialog;

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFiltersListener){
            onFilter = (OnFiltersListener) context;
        }else {
            throw new RuntimeException(context.toString() + getActivity().getString(R.string.errorOnAttach));
        }
    }

    @Override
    public void onDetach() {
        if (onFilter != null){
            onFilter = null;
        }
        super.onDetach();
    }
}
