package com.mybigskystrong.bigskystrong;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

public class SupplementReviewsFragment extends Fragment implements View.OnClickListener{

    ImageButton orgainPicButton;
    Button orgainTextButton;
    ImageButton cnsixPicButton;
    Button cnsixTextButton;
    ImageButton hemoPicButton;
    Button hemoTextButton;
    ViewGroup reviewContainer;

    Intent blankReviewIntent;
    Bundle bundle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.supplement_reviews_fragment,container,false);

        this.reviewContainer = container;


        orgainPicButton = (ImageButton) view.findViewById(R.id.orgainPicButton);
        orgainPicButton.setOnClickListener(this);
        orgainTextButton = (Button) view.findViewById(R.id.orgainWordButton);
        orgainTextButton.setOnClickListener(this);

        cnsixPicButton = (ImageButton) view.findViewById(R.id.cnsixPicButton);
        cnsixPicButton.setOnClickListener(this);
        cnsixTextButton = (Button) view.findViewById(R.id.cnsixWordButton);
        cnsixTextButton.setOnClickListener(this);

        hemoPicButton = (ImageButton) view.findViewById(R.id.hemoShockPicButton);
        hemoPicButton.setOnClickListener(this);
        hemoTextButton = (Button) view.findViewById(R.id.hemoShockWordButton);
        hemoTextButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();

        switch (id){
            case R.id.orgainPicButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.orgain_protein_powder_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);

                break;
            case R.id.orgainWordButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.orgain_protein_powder_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);
                break;
            case R.id.cnsixPicButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.cnsix_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);
                break;
            case R.id.cnsixWordButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.cnsix_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);
                break;
            case R.id.hemoShockPicButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.hemo_shock_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);
                break;
            case R.id.hemoShockWordButton:
                blankReviewIntent = new Intent(getContext(),BlankReviewActivity.class);
                bundle = new Bundle();
                bundle.putInt("id",R.layout.hemo_shock_review_activity);
                blankReviewIntent.putExtras(bundle);
                startActivity(blankReviewIntent);
                break;
        }

    }
}
