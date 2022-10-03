package com.needforspeed.settings.vibration;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings;
import androidx.preference.Preference;
import androidx.preference.Preference.OnPreferenceChangeListener;
import androidx.preference.PreferenceManager;

import com.needforspeed.settings.vibration.VibrationFragment;
import com.needforspeed.settings.vibration.VibrationUtils;

public class Vibration implements OnPreferenceChangeListener {

    private Context mContext;

    public Vibration(Context context) {
        mContext = context;
    }

    public static String getFile() {
        if (VibrationUtils.fileWritable(VibrationFragment.PREF_VIBRATION_PATH)) {
            return VibrationFragment.PREF_VIBRATION_PATH;
        }
        return null;
    }

    public static boolean isSupported() {
        return VibrationUtils.fileWritable(getFile());
    }

    public static boolean isCurrentlyEnabled(Context context) {
        return VibrationUtils.getFileValueAsBoolean(getFile(), false);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {
            case VibrationFragment.PREF_VIBRATION_OVERRIDE:
                VibrationUtils.setValue(VibrationFragment.PREF_VIBRATION_PATH, (boolean) value);
                break;

            default:
                break;
        }
        return true;
    }
}
