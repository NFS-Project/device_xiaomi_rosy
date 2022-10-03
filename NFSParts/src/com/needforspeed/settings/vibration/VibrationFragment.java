package com.needforspeed.settings.vibration;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.preference.PreferenceFragment;
import androidx.preference.Preference;
import androidx.preference.PreferenceScreen;
import androidx.preference.PreferenceCategory;

import com.needforspeed.settings.preferences.CustomSeekBarPreference;
import com.needforspeed.settings.preferences.SecureSettingListPreference;
import com.needforspeed.settings.preferences.SecureSettingSwitchPreference;
import com.needforspeed.settings.preferences.VibrationSeekBarPreference;

import com.needforspeed.settings.R;

import java.lang.Math.*;

public class VibrationFragment extends PreferenceFragment implements
        Preference.OnPreferenceChangeListener {

    public static final String PREF_VIBRATION_OVERRIDE = "vmax_override";
    public static final String PREF_VIBRATION_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8940@3:qcom,haptics@c000/leds/vibrator/vmax_override";
    public static final String PREF_SYSTEM_VIBRATION_STRENGTH = "vibration_system";
    public static final String SYSTEM_VIBRATION_STRENGTH_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8940@3:qcom,haptics@c000/leds/vibrator/vmax_mv_user";
    public static final String PREF_VIBRATION_NOTIFICATION_STRENGTH = "vibration_notification";
    public static final String VIBRATION_NOTIFICATION_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8940@3:qcom,haptics@c000/leds/vibrator/vmax_mv_strong";
    public static final String PREF_VIBRATION_CALL_STRENGTH = "vibration_call";
    public static final String VIBRATION_CALL_PATH = "/sys/devices/platform/soc/200f000.qcom,spmi/spmi-0/spmi0-03/200f000.qcom,spmi:qcom,pmi8940@3:qcom,haptics@c000/leds/vibrator/vmax_mv_call";

    // value of vtg_min and vtg_max
    public static final int MIN_VIBRATION = 116;
    public static final int MAX_VIBRATION = 3596;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.vibration_settings, rootKey);

        SecureSettingSwitchPreference vib = (SecureSettingSwitchPreference) findPreference(PREF_VIBRATION_OVERRIDE);
        vib.setEnabled(Vibration.isSupported());
        vib.setChecked(Vibration.isCurrentlyEnabled(this.getContext()));
        vib.setOnPreferenceChangeListener(new Vibration(getContext()));

        VibrationSeekBarPreference vibrationSystemStrength = (VibrationSeekBarPreference) findPreference(PREF_SYSTEM_VIBRATION_STRENGTH);
        vibrationSystemStrength.setEnabled(VibrationUtils.fileWritable(SYSTEM_VIBRATION_STRENGTH_PATH));
        vibrationSystemStrength.setOnPreferenceChangeListener(this);

        VibrationSeekBarPreference vibrationNotificationStrength = (VibrationSeekBarPreference) findPreference(PREF_VIBRATION_NOTIFICATION_STRENGTH);
        vibrationNotificationStrength.setEnabled(VibrationUtils.fileWritable(VIBRATION_NOTIFICATION_PATH));
        vibrationNotificationStrength.setOnPreferenceChangeListener(this);

        VibrationSeekBarPreference vibrationCallStrength = (VibrationSeekBarPreference) findPreference(PREF_VIBRATION_CALL_STRENGTH);
        vibrationCallStrength.setEnabled(VibrationUtils.fileWritable(VIBRATION_CALL_PATH));
        vibrationCallStrength.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        final String key = preference.getKey();
        switch (key) {

            case PREF_SYSTEM_VIBRATION_STRENGTH:
                double VibrationSystemValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(SYSTEM_VIBRATION_STRENGTH_PATH, VibrationSystemValue);
                break;

            case PREF_VIBRATION_NOTIFICATION_STRENGTH:
                double VibrationNotificationValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(VIBRATION_NOTIFICATION_PATH, VibrationNotificationValue);
                break;

            case PREF_VIBRATION_CALL_STRENGTH:
                double VibrationCallValue = (int) value / 100.0 * (MAX_VIBRATION - MIN_VIBRATION) + MIN_VIBRATION;
                VibrationUtils.setValue(VIBRATION_CALL_PATH, VibrationCallValue);
                break;

            default:
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
