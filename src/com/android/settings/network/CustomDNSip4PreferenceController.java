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
import android.support.v7.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

public class CustomDNSip4PreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String TAG = "CustomDNSip4";
    private static final String CUSTOM_DNS_IP4 = "network_custom_dns_ip4";

    public CustomDNSip4PreferenceController(Context context) {
        super(context);
    }

    @Override
    public void updateState(Preference preference) {
        final String propIP4 = SystemProperties.get("persist.privacy.iptab_dns_srvip4");
        if (propIP4.equals("")) {
            preference.setSummary(R.string.network_custom_dns_ip_placeholder);
        } else {
            preference.setSummary(propIP4);
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return CUSTOM_DNS_IP4;
    }
}
