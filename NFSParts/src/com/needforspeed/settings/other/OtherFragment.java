package com.needforspeed.settings.other;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.Context;
import android.os.Bundle;
import android.content.SharedPreferences;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceManager;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;

import com.needforspeed.settings.preferences.CustomSeekBarPreference;
import com.needforspeed.settings.preferences.SecureSettingListPreference;
import com.needforspeed.settings.preferences.SecureSettingSwitchPreference;
import com.needforspeed.settings.preferences.VibrationSeekBarPreference;
import com.needforspeed.settings.fps.FPSInfoService;
import com.needforspeed.settings.utils.FileUtils;

import com.needforspeed.settings.R;

import java.lang.Math.*;

public class OtherFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public static final String PREF_USB_FASTCHARGE = "fastcharge";
    public static final String USB_FASTCHARGE_PATH = "/sys/kernel/fast_charge/force_fast_charge";

    public static final String PREF_KEY_FPS_INFO = "fps_info";

    private static Context mContext;
    

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.other_settings, rootKey);
        mContext = this.getContext();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

        SecureSettingSwitchPreference charge = (SecureSettingSwitchPreference) findPreference(PREF_USB_FASTCHARGE);
        charge.setEnabled(Fastcharge.isSupported());
        charge.setChecked(Fastcharge.isCurrentlyEnabled(this.getContext()));
        charge.setOnPreferenceChangeListener(new Fastcharge(getContext()));

        SecureSettingSwitchPreference fpsInfo = (SecureSettingSwitchPreference) findPreference(PREF_KEY_FPS_INFO);
        fpsInfo.setChecked(prefs.getBoolean(PREF_KEY_FPS_INFO, false));
        fpsInfo.setOnPreferenceChangeListener(this);

    }
    
    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {

            case PREF_KEY_FPS_INFO:
                boolean enabled = (Boolean) value;
                Intent fpsinfo = new Intent(this.getContext(), FPSInfoService.class);
                if (enabled) {
                    this.getContext().startService(fpsinfo);
                } else {
                    this.getContext().stopService(fpsinfo);
                }
                break;
        }
        return true;
    }
    
    private boolean isAppNotInstalled(String uri) {
        PackageManager packageManager = getContext().getPackageManager();
        try {
            packageManager.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return false;
        } catch (PackageManager.NameNotFoundException e) {
            return true;
        }
    }
}
