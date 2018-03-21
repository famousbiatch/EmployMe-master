package com.employme.employme;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLClientInfoException;
import java.util.List;

public class JobPageActivity extends AppCompatActivity {

    private boolean fav;
    private int job_id;
    private ImageView ivFavorite;
    private ImageView ivLogoFull;
    private TextView tvBusinessNameFull;
    private TextView tvJobCategoryFull;
    private TextView tvJobDescription;
    private TextView tvAgeRange;
    private TextView tvPhoneNumber;
    private TextView tvBusinessLocation;
    private Button btnApplyForJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.actionbarJob);
        setSupportActionBar(toolbar);

        ivFavorite = (ImageView) findViewById(R.id.ivFavorite);
        ivLogoFull = (ImageView) findViewById(R.id.imgLogoFull);
        tvBusinessNameFull = (TextView) findViewById(R.id.tvBusinessNameFull);
        tvJobCategoryFull = (TextView) findViewById(R.id.tvJobCategoryFull);
        tvJobDescription = (TextView) findViewById(R.id.tvJobDescription);
        tvAgeRange = (TextView) findViewById(R.id.tvAgeRange);
        tvPhoneNumber = (TextView) findViewById(R.id.tvPhoneNumber);
        tvBusinessLocation = (TextView) findViewById(R.id.tvBusinessLocation);
        btnApplyForJob = (Button) findViewById(R.id.btnApplyForJob);

        this.job_id = getIntent().getIntExtra("job_id", 0);
        List<String> info = SQLiteDB.getInstance().getJobListing(job_id);

        fav = false;
        if (SQLiteDB.getInstance().isFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id)) {
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        }
        //ivLogoFull.setImageResource();
        tvBusinessNameFull.setText(info.get(2));
        tvJobCategoryFull.setText(info.get(7));
        tvJobDescription.setText(info.get(4));
        tvAgeRange.setText(info.get(8) + "-" + info.get(9));
        tvPhoneNumber.setText(info.get(5));
        tvBusinessLocation.setText(info.get(6));
        //set data in location view
    }


    public void favoriteClick(View view) {
        if (!fav) {
            SQLiteDB.getInstance().addFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_golden_star);
            fav = true;
        } else {
            SQLiteDB.getInstance().deleteFavorite(Integer.valueOf(SQLiteDB.getInstance().getSessionUser()), job_id);
            ivFavorite.setImageResource(R.drawable.ic_star_empty);
            fav = false;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (getIntent().getStringExtra("intentName").equals("JobList"))
                startActivity(new Intent(this, JobList.class));
            else if (getIntent().getStringExtra("intentName").equals("EmployerDashboard"))
                startActivity(new Intent(this, EmployerDashboardActivity.class));
            else if (getIntent().getStringExtra("intentName").equals("Favorites"))
                startActivity(new Intent(this, FavoritesActivity.class));
        } catch (Exception x) {}

        super.onBackPressed();
    }

    public void deleteJob(View view) {
        SQLiteDB.getInstance().deleteJob(this.job_id);
        startActivity(new Intent(this, JobList.class));
    }
}
