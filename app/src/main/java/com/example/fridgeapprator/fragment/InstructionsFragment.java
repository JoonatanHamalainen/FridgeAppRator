package com.example.fridgeapprator.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.example.fridgeapprator.R;

public class InstructionsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        super.onCreate(savedInstanceState);

        View view = inflater.inflate(R.layout.instructions_fragment, container, false);

        WebView web = (WebView)view.findViewById(R.id.webViewInstructions);
        web.loadUrl("file:///android_asset/instructions.html");

        TextView t2 = (TextView) view.findViewById(R.id.instructionFridge);
        t2.setMovementMethod(LinkMovementMethod.getInstance());

        return view;

    }
}
