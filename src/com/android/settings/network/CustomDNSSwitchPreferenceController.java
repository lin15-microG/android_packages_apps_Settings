/*
 * Copyright (C) 2019 The LineageOS Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.android.settings.network;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.SystemProperties;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

public class CustomDNSSwitchPreferenceController extends AbstractPreferenceController
        implements Preference.OnPreferenceChangeListener {

    private static final String TAG = "CustomDNSSwitch";
    private static final String CUSTOM_DNS_SWITCH_KEY = "network_custom_dns_switch";
    private Activity mActivity;

    public CustomDNSSwitchPreferenceController(Context context) {
        super(context);
        mActivity = (Activity) context;
    }

    @Override
    public void updateState(Preference preference) {
        boolean valDNSx = SystemProperties.getBoolean("persist.privacy.iptab_dns_switch", false);
        ((SwitchPreference) preference).setChecked(valDNSx);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        boolean valDNSx = (Boolean) newValue;
        String ip4srv = SystemProperties.get("persist.privacy.iptab_dns_srvip4");
        String ip6srv = SystemProperties.get("persist.privacy.iptab_dns_srvip6");
        if (valDNSx ) {
            if (ip4srv.equals("") && ip6srv.equals("")) {
                new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.network_custom_dns_placeholder)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.network_custom_dns_error)
                    .setNegativeButton(android.R.string.cancel, null)
                    .create().show();
                return false;
            } else if (ip4srv.equals("") || ip6srv.equals("")) {
                new AlertDialog.Builder(mActivity)
                    .setTitle(R.string.network_custom_dns_placeholder)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.network_custom_dns_warning)
                    .setNeutralButton(android.R.string.ok, null)
                    .create().show();
            }
        }
        SystemProperties.set("persist.privacy.iptab_dns_switch", valDNSx ? "1" : "0" );
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return CUSTOM_DNS_SWITCH_KEY;
    }
}
