package com.aah.selectingfood.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.TextView;

import com.aah.selectingfood.R;
import com.aah.selectingfood.helper.LocaleHelper;

/**
 * This Activity is for showing static content in an frequently asked questions page
 **/
public class FAQActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_faq));

        if(LocaleHelper.getLanguage(this).equals("en")){
            TextView textView = (TextView) findViewById(R.id.textViewFAQ);
            textView.setMovementMethod(LinkMovementMethod.getInstance());
            Spannable spans = (Spannable) textView.getText();

            ClickableSpan clickSpan = new ClickableSpan() {

                @Override
                public void onClick(View widget)
                {
                    Intent intent = new Intent(FAQActivity.this, RecipesActivity.class);
                    startActivity(intent);
                }
            };
            String text = textView.getText().toString();
            String linkText = "(link to recipe)";
            int linkOccurence = text.indexOf(linkText);
            spans.setSpan(clickSpan, linkOccurence, linkOccurence+linkText.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }
}
