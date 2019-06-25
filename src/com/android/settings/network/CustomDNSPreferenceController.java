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
import android.os.UserManager;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceScreen;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.logging.nano.MetricsProto.MetricsEvent;
import com.android.settings.core.PreferenceControllerMixin;
import com.android.settings.R;
import com.android.settings.Utils;
import com.android.settingslib.core.AbstractPreferenceController;


public class CustomDNSPreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin {

    private static final String KEY_NETWORK_CUSTM_DNS = "network_custom_dns";
    private static final String TAG = "NetworkCustomDNS";

    private final UserManager mUm;
    private Context mContext;

    public CustomDNSPreferenceController(Context context) {
        super(context);
        mContext = context;
        mUm = UserManager.get(context);
    }

    @Override
    public boolean isAvailable() {
        return mUm.isAdminUser();
    }

    @Override
    public String getPreferenceKey() {
        return KEY_NETWORK_CUSTM_DNS;
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
        if (!TextUtils.equals(preference.getKey(), KEY_NETWORK_CUSTM_DNS)) {
            return false;
        }
        Utils.startWithFragment(mContext, NetworkCustomDNSFragment.class.getName(),
                null, null, 0, KEY_NETWORK_CUSTM_DNS,
                0, null, MetricsEvent.TYPE_UNKNOWN);
        return true;
    }

    private void updateSummary(Preference preference) {
        final String infoCustomDNS;
        if (SystemProperties.getBoolean("persist.privacy.iptab_dns_switch", false)) {
            infoCustomDNS = "IPv4 - " +SystemProperties.get("persist.privacy.iptab_dns_srvip4")
                            + "\nIPv6 - " + SystemProperties.get("persist.privacy.iptab_dns_srvip6");
            preference.setSummary(infoCustomDNS);
        } else {
            preference.setSummary(R.string.network_custom_dns_placeholder);
        }
    }
}
