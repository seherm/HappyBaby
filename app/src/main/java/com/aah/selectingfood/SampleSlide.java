package com.aah.selectingfood;

/**
 * Created by Manuel on 05.05.2017.
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SampleSlide extends Fragment {

    private static final String ARG_LAYOUT_RES_ID = "layoutResId";
    private static final String ARG_FEEDBACK_TEXT = "feedbackText";
    private static final String ARG_FEEDBACK_TEXT_COLOR = "feedbackTextColor";
    private static final String ARG_FEEDBACK_BACKGROUND_COLOR = "feedbackBackgroundColor";
    private static final String ARG_FEEDBACK_IMAGE = "feedbackImage";
    private int layoutResId;
    private String feedbackText;
    private String feedbackTextColor;
    private String feedbackBackgroundColor;
    private int feedbackImage;

    public static SampleSlide newInstance(int layoutResId, String feedbackText, String feedbackTextColor, String feedbackBackgroundColor, int feedbackImage) {
        SampleSlide sampleSlide = new SampleSlide();

        Bundle args = new Bundle();
        args.putInt(ARG_LAYOUT_RES_ID, layoutResId);
        args.putString(ARG_FEEDBACK_TEXT, feedbackText);
        args.putString(ARG_FEEDBACK_TEXT_COLOR, feedbackTextColor);
        args.putString(ARG_FEEDBACK_BACKGROUND_COLOR, feedbackBackgroundColor);
        args.putInt(ARG_FEEDBACK_IMAGE, feedbackImage);
        sampleSlide.setArguments(args);

        return sampleSlide;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(ARG_LAYOUT_RES_ID)) {
            layoutResId = getArguments().getInt(ARG_LAYOUT_RES_ID);
            feedbackText = getArguments().getString(ARG_FEEDBACK_TEXT);
            feedbackTextColor = getArguments().getString(ARG_FEEDBACK_TEXT_COLOR);
            feedbackBackgroundColor = getArguments().getString(ARG_FEEDBACK_BACKGROUND_COLOR);
            feedbackImage = getArguments().getInt(ARG_FEEDBACK_IMAGE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(layoutResId, container, false);

        // Change the contents
        TextView textView = (TextView) view.findViewById(R.id.Text);
        textView.setText(feedbackText);
        textView.setTextColor(Color.parseColor(feedbackTextColor));
        ImageView imageView = (ImageView) view.findViewById(R.id.imageView2);
        imageView.setImageResource(feedbackImage);

        ConstraintLayout constraintLayout = (ConstraintLayout) view.findViewById(R.id.feedbackConstraintLayout);
        constraintLayout.setBackgroundColor(Color.parseColor(feedbackBackgroundColor));

        return view;
    }
}
