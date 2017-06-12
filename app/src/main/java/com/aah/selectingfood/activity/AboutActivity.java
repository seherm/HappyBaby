package com.aah.selectingfood.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.aah.selectingfood.BuildConfig;
import com.aah.selectingfood.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/**
 * This Activity is for showing static content in an about page
 **/
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_about));

        String versionName = "Version " + BuildConfig.VERSION_NAME;

        String[] array = getResources().getStringArray(R.array.about_items_array);
        List<String> list = Arrays.asList(array);
        ArrayList<String> arrayList = new ArrayList<>(list);
        arrayList.add(0, versionName);

        ListView aboutListView = (ListView) findViewById(R.id.aboutListView);
        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        aboutListView.setAdapter(listViewAdapter);
        aboutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 1) {
                    showLicenses();
                } else if (position == 2) {
                    showCredits();
                }
            }
        });


    }

    public void showLicenses() {

        final Notices notices = new Notices();

        notices.addNotice(new Notice("CircleImageView", "https://github.com/hdodenhof/CircleImageView", "Copyright 2014 - 2017 Henning Dodenhof", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("AppIntro", "https://github.com/apl-devs/AppIntro", "Copyright 2015 Paolo Rotolo\n" + "Copyright 2016 Maximilian Narr", new ApacheSoftwareLicense20()));
        notices.addNotice(new Notice("Android PdfViewer", "https://github.com/barteksc/AndroidPdfViewer", "Copyright 2016 Bartosz Schiller", new ApacheSoftwareLicense20()));

        new LicensesDialog.Builder(this)
                .setTitle("Remarks")
                .setNotices(notices)
                .setIncludeOwnLicense(true)
                .build()
                .show();
    }

    public void showCredits() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Credits");
        String creditFreepickTitle = "Some of the images in this applications are used courtesy of Freepik";
        String creditFreepick1 = "<a href=http://www.freepik.com>Designed by Terdpongvector / Freepik</a>";
        String creditFreepick2 = "<a href=http://www.freepik.com>Designed by Kostolom3000 / Freepik</a>";
        String creditFreepick3 = "<a href=http://www.freepik.com>Designed by Terdpongvector / Freepik</a>";
        String creditFreepick4 = "<a href=http://www.freepik.com>Designed by Kostolom3000 / Freepik</a>";
        String creditFreepick5 = "<a href=http://www.freepik.com>Designed by Freepik</a>";
        String creditFreepick6 = "<a href=http://www.freepik.com>Designed by Elsystudio / Freepik</a>";
        String creditFreepick7 = "<a href=http://www.freepik.com>Designed by Asier_Relampagoestudio / Freepik</a>";

        String creditFlaticon1 = "Icons made by <a href=http://www.freepik.com>Freepik</a> from <a href=http://www.flaticon.com>www.flaticon.com</a>";
        String creditFlaticon2 = "Icons made by <a href=http://www.flaticon.com/authors/madebyoliver>Madebyoliver</a> from <a href=http://www.flaticon.com>www.flaticon.com</a>";

        builder.setMessage(Html.fromHtml(creditFreepickTitle + "<br><br>" + creditFreepick1 + "<br><br>" + creditFreepick2 + "<br><br>" + creditFreepick3 + "<br><br>" + creditFreepick4 + "<br><br>" + creditFreepick5 + "<br><br>" + creditFreepick6 + "<br><br>" + creditFreepick7 + "<br><br>" + creditFlaticon1 + "<br><br>" + creditFlaticon2));
        builder.setNegativeButton(R.string.about_alert_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView messageText = (TextView) dialog.findViewById(android.R.id.message);
        messageText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
