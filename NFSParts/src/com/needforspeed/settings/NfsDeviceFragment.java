package com.needforspeed.settings;

import android.os.Bundle;
import android.provider.Settings;
import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;

import com.needforspeed.settings.R;

public class NfsDeviceFragment extends PreferenceFragment  {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.nfs_main_activity, rootKey);

        
    }

}
