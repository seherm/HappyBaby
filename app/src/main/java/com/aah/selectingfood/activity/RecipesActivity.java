package com.aah.selectingfood.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.DataManagement;
import com.aah.selectingfood.helper.LocaleHelper;
import com.github.barteksc.pdfviewer.PDFView;

/** This Activity is for showing some suggestions of recipes for good meals**/
public class RecipesActivity extends BaseActivity {

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
        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);

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
                    .enableSwipe(true)
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    .enableAntialiasing(true)
                    .load();
        }

    }
}
