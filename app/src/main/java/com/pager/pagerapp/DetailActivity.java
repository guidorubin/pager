package com.pager.pagerapp;

import android.annotation.TargetApi;
import android.icu.text.LocaleDisplayNames;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pager.pagerapp.model.Member;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtPosition;
    private TextView txtNickname;
    private TextView txtStatus;

    private TextView txtLanguage;
    private TextView txtSkills;
    private TextView txtLocation;

    private ImageView imageView;
    private Member member;
    private CardView detailCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (this.getIntent().hasExtra("member")) {
            member = Parcels.unwrap(getIntent().getParcelableExtra("member"));
        }
        init();
        fillData();
    }

    private void fillData() {
        txtName.setText(member.getName());
        txtNickname.setText(member.getGithub());
        txtPosition.setText(member.getRoleName());

        if(member.getStatus()!= null) {
            txtStatus.setText(member.getStatus());
        }

        txtLanguage.setText(getLanguage(member.getLanguages()));
        txtSkills.setText(replaceList(member.getTags()));
        txtLocation.setText(getLocation(member.getLocation()));

        Picasso.with(this).load(member.getAvatar()).into(imageView);
        setRoundedCorner(imageView);
    }

    private String getLocation(String location) {
        location = location.toUpperCase();
        int firstLetter = Character.codePointAt(location, 0) - 0x41 + 0x1F1E6;
        int secondLetter = Character.codePointAt(location, 1) - 0x41 + 0x1F1E6;
        return new String(Character.toChars(firstLetter)) + new String(Character.toChars(secondLetter));
    }

    private void init() {
        txtName = (TextView) this.findViewById(R.id.txtName);
        txtPosition = (TextView) this.findViewById(R.id.txtPosition);
        txtNickname = (TextView) this.findViewById(R.id.txtNickname);
        txtStatus = (TextView) this.findViewById(R.id.txtStatus);
        imageView = (ImageView)this.findViewById(R.id.imageView);

        txtSkills = (TextView)this.findViewById(R.id.skillsTxt);
        txtLocation = (TextView)this.findViewById(R.id.locationTxt);
        txtLanguage = (TextView)this.findViewById(R.id.languageTxt);

        detailCardView = (CardView)this.findViewById(R.id.cardDetail);
        detailCardView.setVisibility(View.VISIBLE);

    }

    @TargetApi(21)
    private void setRoundedCorner(ImageView imageView) {
        imageView.setClipToOutline(true);
    }


    private String getLanguage(List<String> languages) {
        StringBuffer sb = new StringBuffer();
        if (Build.VERSION.SDK_INT < 24) {
            sb.append(replaceList(languages));
        } else {
            for (String lang : languages) {
                sb.append(Locale.forLanguageTag(lang).getDisplayLanguage() + ", ");
            }
        }

        return sb.toString();

    }

    private String replaceList(List<String> list) {
        if (list!=null && list.size()>0) {
            return list.toString().replace("[", "").replace("]", "");
        }
        return "";
    }

}
