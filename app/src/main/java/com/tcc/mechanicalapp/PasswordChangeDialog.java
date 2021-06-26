package com.tcc.mechanicalapp;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;

public class PasswordChangeDialog extends AppCompatDialogFragment {
    private EditText editPassword;
    private EditText editPasswordConfirmation;
    private PasswordChangeDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        Context context = this.getContext();

        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(getResources().getColor(R.color.default_detail_color));
        SpannableStringBuilder ssBuilder = new SpannableStringBuilder("Alteração de senha");

        ssBuilder.setSpan(
                foregroundColorSpan,
                0,
                "Alteração de senha".length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        final AlertDialog builder = new AlertDialog.Builder(context).setView(view)
                .setTitle(ssBuilder)
                .setPositiveButton("Confirmar", null) //Set to null. We override the onclick
                .setNegativeButton("Cancelar", null)
                .create();

        builder.getWindow().setBackgroundDrawableResource(R.color.default_card_background_color);
        builder.setOnShowListener(new DialogInterface.OnShowListener() {

            @Override
            public void onShow(DialogInterface dialogInterface) {

                builder.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.white));
                builder.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.white));

                builder.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(getResources().getColor(R.color.default_button_color));

                Button button = ((AlertDialog) builder).getButton(AlertDialog.BUTTON_POSITIVE);
                button.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {
                        // TODO Do something
                        String password = editPassword.getText().toString();
                        String passwordConfirmation = editPasswordConfirmation.getText().toString();

                        if (password.equals("") || passwordConfirmation.equals("")) {
                            Toast toast = Toast.makeText(context, "Não permitido campos em branco", Toast.LENGTH_SHORT);
                            toast.show();
                        } else if (password.equals(passwordConfirmation)) {
                            Toast toast = Toast.makeText(context, "Nova senha definida! Clique em Salvar para aplicar a alteração", Toast.LENGTH_LONG);
                            toast.show();
                            listener.applyTexts(password);
                            builder.dismiss();
                        } else {
                            Toast toast = Toast.makeText(context, "As senhas não são compatíveis", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    }
                });
            }
        });
        builder.show();

        editPassword = view.findViewById(R.id.edit_password);
        editPasswordConfirmation = view.findViewById(R.id.edit_passwordConfirmation);

        return builder;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (PasswordChangeDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PasswordChangeDialogListener");
        }
    }

    public interface PasswordChangeDialogListener {
        void applyTexts(String newPassword);
    }
}