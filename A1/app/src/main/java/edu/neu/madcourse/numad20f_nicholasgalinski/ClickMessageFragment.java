package edu.neu.madcourse.numad20f_nicholasgalinski;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import static edu.neu.madcourse.numad20f_nicholasgalinski.MainActivity.*;

public class ClickMessageFragment extends Fragment {

    private TextView pressedText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_third, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pressedText = view.findViewById(R.id.textview_pressed);
        pressedText.setText(getPressedText());

        view.findViewById(R.id.button_a).setOnClickListener(messageOnClickListener);
        view.findViewById(R.id.button_b).setOnClickListener(messageOnClickListener);
        view.findViewById(R.id.button_c).setOnClickListener(messageOnClickListener);
        view.findViewById(R.id.button_d).setOnClickListener(messageOnClickListener);
        view.findViewById(R.id.button_e).setOnClickListener(messageOnClickListener);
        view.findViewById(R.id.button_f).setOnClickListener(messageOnClickListener);

        view.findViewById(R.id.click_previous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ClickMessageFragment.this)
                        .navigate(R.id.action_MessageFragment_to_FirstFragment);
            }
        });
    }

    private View.OnClickListener messageOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_a:
                    setPressedText("Pressed: A");
                    pressedText.setText(getPressedText());
                    break;
                case R.id.button_b:
                    setPressedText("Pressed: B");
                    pressedText.setText(getPressedText());
                    break;
                case R.id.button_c:
                    setPressedText("Pressed: C");
                    pressedText.setText(getPressedText());
                    break;
                case R.id.button_d:
                    setPressedText("Pressed: D");
                    pressedText.setText(getPressedText());
                    break;
                case R.id.button_e:
                    setPressedText("Pressed: E");
                    pressedText.setText(getPressedText());
                    break;
                case R.id.button_f:
                    setPressedText("Pressed: F");
                    pressedText.setText(getPressedText());
                    break;
            }
        }
    };
}