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

import android.content.Context;
import android.os.SystemProperties;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class CustomDNSSwitchPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String TAG = "CustomDNSSwitch";
    private static final String CUSTOM_DNS_SWITCH_KEY = "network_custom_dns_switch";

    public CustomDNSSwitchPreferenceController(Context context) {
        super(context);
    }

    @Override
    public void updateState(Preference preference) {
        boolean valDNSx = SystemProperties.getBoolean("persist.privacy.iptab_dns_switch", false);
        ((SwitchPreference) preference).setChecked(valDNSx);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SystemProperties.set("persist.privacy.iptab_dns_switch",
              ((Boolean) newValue) ? "1" : "0" );

      // TEST-ONLY CODE - TO BE REMOVED !!!
        SystemProperties.set("persist.privacy.iptab_dns_srvip4", "1.1.1.1");
        SystemProperties.set("persist.privacy.iptab_dns_srvip6", "2606:4700:4700::1111");
      // TEST-ONLY CODE - TO BE REMOVED !!!
        
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
