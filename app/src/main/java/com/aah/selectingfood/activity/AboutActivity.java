package com.aah.selectingfood.activity;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.aah.selectingfood.R;

import de.psdev.licensesdialog.LicensesDialog;
import de.psdev.licensesdialog.licenses.ApacheSoftwareLicense20;
import de.psdev.licensesdialog.model.Notice;
import de.psdev.licensesdialog.model.Notices;

/** This Activity is for showing static content in an about page**/
public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        setTitle(getString(R.string.title_activity_about));

        String versionName = "";

        try {
            versionName = "Version " + getPackageManager().getPackageInfo(getPackageName(),0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        TextView aboutTextView = (TextView) findViewById(R.id.textViewAbout);
        aboutTextView.setText(versionName);

        ListView aboutListView = (ListView) findViewById(R.id.aboutListView);
        aboutListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0){
                    showLicenses();
                }else if(position ==1){
                    showCredits();
                }

            }
        });



    }

    public void showLicenses(){

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

    public void showCredits(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Credits");
        String creditFreepick = "Icons made by <a href=http://www.freepik.com>Freepik</a> from <a href=http://www.flaticon.com>www.flaticon.com</a>";
        String creditFlaticon = "Icons made by <a href=http://www.flaticon.com/authors/madebyoliver>Madebyoliver</a> from <a href=http://www.flaticon.com>www.flaticon.com</a>";
        builder.setMessage(Html.fromHtml(creditFreepick + "<br><br>" + creditFlaticon));
        builder.setNegativeButton(R.string.about_alert_close, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setMovementMethod(LinkMovementMethod.getInstance());
    }
}
