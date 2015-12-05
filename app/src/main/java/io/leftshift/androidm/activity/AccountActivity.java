package io.leftshift.androidm.activity;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.leftshift.androidm.fragment.AccountFragment;
import io.leftshift.androidm.utils.Utils;
import io.leftshift.sample.R;

/**
 * @author Akshay Mukadam
 *         LeftShift Technologies Private Limited
 * @since 5/12/2015
 */
public class AccountActivity extends BaseActivity {

    // Never exceed the request code more that 250
    private final int REQUEST_CODE_ACCOUNT_PERMISSION = 101;

    // Tag name for fragment
    private final String ACCOUNT_TAG = "Account_fragment";
    @Bind(R.id.content_layout)
    FrameLayout frameLayout;
    private List<String> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        ButterKnife.bind(this);
        mList = new ArrayList<>();
        if (checkAccountPermission()) {
            loadAccountsFragment();
        }
    }

    public List<String> getAccounts() {
        mList.clear();
        String emailStr = "";
        Account[] accounts = AccountManager.get(this).getAccountsByType("com.google");
        for (Account account : accounts) {
            emailStr = account.name;
            if (!TextUtils.isEmpty(emailStr))
                mList.add(emailStr);
        }
        return mList;
    }

    /**
     * Check Account permission is granted or not. If permission is not granted request permission
     * from user
     *
     * @return
     */
    private boolean checkAccountPermission() {
        // check if permission is granted use ContextCompat for backward compatibility/
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {
            /**
             * Check if permission is denied previously by user. If permission is denied educate the
             * user why a particular permission is necessary. If app has ask permission for first time
             * show permission directly to user. If the user has clicked on never show me this it will
             * automatically show the message permission denied
             */
            if (ActivityCompat.shouldShowRequestPermissionRationale(AccountActivity.this, Manifest.permission.GET_ACCOUNTS)) {
                showPermissionDialog(AccountActivity.this, getString(R.string.contact_permission
                ), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermission(AccountActivity.this);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Utils.showSnackBar(AccountActivity.this, getString(R.string.permission_denied), findViewById(android.R.id.content));
                    }
                });
                return false;
            }

            // If app has asked permission for first time request permission directly
            requestPermission(AccountActivity.this);
            return false;
        } else {
            return true;
        }

    }

    /**
     * Request permission from user
     *
     * @param activity
     */
    private void requestPermission(AppCompatActivity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.GET_ACCOUNTS}, REQUEST_CODE_ACCOUNT_PERMISSION);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_account, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ACCOUNT_PERMISSION:
                // grant result will always  be either PERMISSION_GRANTED/PERMISSION_DENIED it will never be @null
                if ((grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    loadAccountsFragment();
                } else {
                    loadAccountsFragment();
                    Utils.showSnackBar(AccountActivity.this, getString(R.string.permission_denied), findViewById(android.R.id.content));
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    private void loadAccountsFragment() {
        /**
         * Use TAG while commit Fragment which provides avoid the fragment to call
         * onCreateView twice when permission is denied externally by user.By using tag we avoid
         * creating new instance of fragment by using findFragmentByTag. A good practice we should
         * following while using fragments in app
         */
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(ACCOUNT_TAG);
        if (fragment == null) {
            fragment = new AccountFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.content_layout, fragment,
                    ACCOUNT_TAG).commitAllowingStateLoss();
        } else {
            ((AccountFragment) fragment).initUi();
        }
    }

}
