// referenced https://guides.codepath.com/android/using-dialogfragment
// and https://developer.android.com/reference/android/app/DialogFragment

package edu.neu.madcourse.numad20f_nicholasgalinski.link_collector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.neu.madcourse.numad20f_nicholasgalinski.R;

public class DialogueFragment extends DialogFragment {
    private AddLinkDialogListener listener;
    private EditText linkName;
    private EditText linkContent;
    private static final  String SAVED_NAME = "saved_name";
    private static final String SAVED_LINK = "saved_link";

    public static final DialogueFragment newInstance(String name, String link) {

        DialogueFragment fragment = new DialogueFragment();
        Bundle args = new Bundle(2);
        args.putString(SAVED_NAME, name);
        args.putString(SAVED_LINK, link);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_link, null);
        builder.setView(view)
                .setTitle(R.string.add_link)
                .setPositiveButton(R.string.link_add_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String name = linkName.getText().toString().trim();
                        String content = linkContent.getText().toString().trim();
                        listener.onDialogAddClick(name, content);
                    }
                })
                .setNegativeButton(R.string.cancel_btn, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DialogueFragment.this.getDialog().cancel();
                    }
                });
        linkContent = view.findViewById(R.id.edit_link);
        linkName = view.findViewById(R.id.edit_name);

        Bundle prevArgs = getArguments();
        if (prevArgs != null) {
            linkName.setText(prevArgs.getString(SAVED_NAME));
            linkContent.setText(prevArgs.getString(SAVED_LINK));
        }
        return builder.create();
    }

    public interface AddLinkDialogListener {
        void onDialogAddClick(String name, String url);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (AddLinkDialogListener) context;
    }
}