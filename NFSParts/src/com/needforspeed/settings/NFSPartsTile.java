package com.needforspeed.settings;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.service.quicksettings.TileService;

import com.needforspeed.settings.NfsDeviceFragment;
import com.needforspeed.settings.NfsDeviceActivity;

public class NFSPartsTile extends TileService {

    @Override
    public void onClick() {
        try {
            Intent intent = new Intent(this, NfsDeviceActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivityAndCollapse(intent);
        } catch (ActivityNotFoundException ignored) {
            // At this point, the app is most likely hidden and set to only open from Settings
            Intent intent = new Intent(this, NfsDeviceFragment.class);
            startActivityAndCollapse(intent);
        }
    }
}
