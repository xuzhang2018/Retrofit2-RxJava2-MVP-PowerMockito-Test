package com.jenway.cuvvadogshowapplication.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.jenway.cuvvadogshowapplication.R;

public abstract class BaseActivity extends AppCompatActivity {

    private AlertDialog dialog;

    //get the first fragment
    protected abstract BaseFragment getFirstFragment();

    //obtain the Intent
    protected void handleIntent(Intent intent) {

    }

    //get the layout ID
    protected abstract int getContentViewId();

    //Fragment container ID
    protected abstract int getFragmentContentId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getContentViewId());
        if (null != getIntent()) {
            handleIntent(getIntent());
        }
        //add fragment
        if (null == savedInstanceState) {
            if (null != getSupportFragmentManager().getFragments()) {
                BaseFragment firstFragment = getFirstFragment();
                if (null != firstFragment) {//avoid to add same fragment
                    addFragment(firstFragment);
                }
            }
        }

        iniDialog();

    }

    private void iniDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setView(R.layout.layout_message_dialog);
        dialog = builder.create();
    }


    public void showDialog(String message) {
        if (null == dialog) {
            iniDialog();
        } else {
            dialog.dismiss();
            dialog.setMessage(message);
            dialog.show();
        }
    }

    public void dismissDialog() {
        if (null != dialog) {
            dialog.dismiss();
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    //add fragment
    public void addFragment(BaseFragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(getFragmentContentId(), fragment, fragment.getClass().getSimpleName())
                    .addToBackStack(fragment.getClass().getSimpleName())
                    .commitAllowingStateLoss();
        }
    }

    //remove fragment
    public void removeFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    //on press back, find fragment from stack
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
            }
            //removeFragment();

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //MyApplication.getInstance().mustDie(this);
    }
}
