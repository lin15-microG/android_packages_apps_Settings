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

import android.app.Fragment;
import android.content.Context;
import android.os.SystemProperties;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.R;
import com.android.settingslib.core.AbstractPreferenceController;

public class CustomDNSip6PreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {

    private static final String TAG = "CustomDNSip6";
    private static final String CUSTOM_DNS_IP6 = "network_custom_dns_ip6";

    private final Fragment mFragment;
    private Preference mPreference;

    public CustomDNSip6PreferenceController(Context context, Fragment hostFragment) {
        super(context);
        mFragment = hostFragment;
    }

    @Override
    public void updateState(Preference preference) {
        updateSummary(preference);
    }

    @Override
    public void displayPreference(PreferenceScreen screen) {
        super.displayPreference(screen);
    }

    @Override
    public boolean handlePreferenceTreeClick(Preference preference) {
        if (!TextUtils.equals(preference.getKey(), CUSTOM_DNS_IP6)) {
            return false;
        }
        mPreference = preference;
        CustomDNSip6Dialog.show(mFragment);
        return true;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public String getPreferenceKey() {
        return CUSTOM_DNS_IP6;
    }

    private void updateSummary(Preference preference) {
        final String propIP6 = SystemProperties.get("persist.privacy.iptab_dns_srvip6");
        if (propIP6.equals("")) {
            preference.setSummary(R.string.network_custom_dns_ip_placeholder);
        } else {
            preference.setSummary(propIP6);
        }
    }

    public void onCustomDNSip6DialogConfirmed() {
       SystemProperties.set("persist.privacy.iptab_dns_srvip6",
               CustomDNSip6Dialog.getResult());
       updateSummary(mPreference);
    }
}
