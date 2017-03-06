package profilemof.zicure.company.com.profilemof.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.readystatesoftware.systembartint.SystemBarTintManager;

import de.hdodenhof.circleimageview.CircleImageView;
import profilemof.zicure.company.com.profilemof.R;
import profilemof.zicure.company.com.profilemof.adapter.ViewPagerAdapter;
import profilemof.zicure.company.com.profilemof.fragment.AddCashFragment;
import profilemof.zicure.company.com.profilemof.fragment.UserDetailFragment;
import profilemof.zicure.company.com.profilemof.utilize.ModelCartProfile;

public class ProfileActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private Toolbar toolbar = null;
    private TabLayout tabLayout = null;
    private ViewPager viewPager = null;
    private CircleImageView imgProfile = null;
    private TextView accountProfile = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        bindView();
        setToolbar();
        setStatusBarTint();
        setupViewPager(viewPager);
        initialTab();
        setImgProfile();

        if (savedInstanceState == null){

        }
    }

    private void bindView(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        imgProfile = (CircleImageView) findViewById(R.id.img_profile);
        accountProfile = (TextView) findViewById(R.id.account_profile);
    }

    private void setStatusBarTint(){
        if (Build.VERSION.SDK_INT < 21){
            //create our manager instance after the content view is set
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            //enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            //enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
            //set the transparent color of the status bar 20% darker
            tintManager.setTintColor(Color.parseColor("#20000000"));
        }
    }

    private void initialTab(){
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        tab.select();
        tabLayout.setOnTabSelectedListener(this);
    }

    private void setImgProfile(){
        try{
            if (ModelCartProfile.getInstance().getAccount().equals(getString(R.string.account1))){
                imgProfile.setImageResource(R.drawable.base);
                accountProfile.setText(getString(R.string.account1));
            }
            else if (ModelCartProfile.getInstance().getAccount().equals(getString(R.string.account2))){
                imgProfile.setImageResource(R.drawable.yajai);
                accountProfile.setText(getString(R.string.account2));
            }
        }catch (NullPointerException e){
            imgProfile.setImageResource(R.mipmap.ic_launcher);
            accountProfile.setText("");
        }

    }

    private void setToolbar(){
        toolbar.setTitle("");
        //Set the padding to math the status bar height
        toolbar.setPadding(0, getStatusBarHeight() - 8, 0,0);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private int getStatusBarHeight(){
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0){
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private void setupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UserDetailFragment(), getString(R.string.data_title));
        adapter.addFragment(new AddCashFragment(), getString(R.string.add_cash_title));
        viewPager.setAdapter(adapter);
    }



    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    private void finishActivity(){
        finish();
        overridePendingTransition(0, R.anim.anim_slide_out_bottom);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishActivity();
    }
}

