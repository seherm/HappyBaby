package com.aah.selectingfood.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.LocaleHelper;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;

/** This Activity is for showing some suggestions of recipes for good meals**/
public class RecipesActivity extends BaseActivity implements OnLoadCompleteListener{

    private ProgressBar progressBar;
    private PDFView pdfView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_recipes));

        String pdfFileEnglish = "recipes/recipes_en.pdf";
        String pdfFileKhmer = "recipes/recipes_km.pdf";

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        pdfView = (PDFView) findViewById(R.id.pdfView);

        String languageCode = LocaleHelper.getLanguage(this);

        if(languageCode.equals("km")){
            pdfView.fromAsset(pdfFileKhmer)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAntialiasing(true)
                    .load();
        }else{
            pdfView.fromAsset(pdfFileEnglish)
                    .defaultPage(0)
                    .onLoad(this)
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .enableAntialiasing(true)
                    .load();
        }
    }

    @Override
    public void loadComplete(int nbPages){
        pdfView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}
