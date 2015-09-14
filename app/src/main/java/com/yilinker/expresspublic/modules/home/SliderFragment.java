package com.yilinker.expresspublic.modules.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.yilinker.expresspublic.R;

import java.util.logging.Logger;

/**
 * Created by Jeico.
 */
public class SliderFragment extends Fragment
{
    private static final Logger logger = Logger.getLogger(SliderFragment.class.getSimpleName());

    private static final String ARG_TITLE               = "title";
    private static final String ARG_CONTENT             = "content";

    private String title;
    private String content;

    public SliderFragment() {
    }

    public static SliderFragment newInstance(String title, String content)
    {
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_CONTENT, content);

        SliderFragment sliderFragment = new SliderFragment();
        sliderFragment.setArguments(args);

        return sliderFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null)
        {
            title = getArguments().getString(ARG_TITLE);
            content = getArguments().getString(ARG_CONTENT);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_slider, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((TextView) view.findViewById(R.id.tv_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_content)).setText(content);
    }
}
