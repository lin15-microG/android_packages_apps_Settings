/*
 * Copyright (C) 2018 The LineageOS Project
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
import android.provider.Settings;
import android.support.v14.preference.SwitchPreference;
import android.support.v7.preference.Preference;

import com.android.settings.core.PreferenceControllerMixin;
import com.android.settingslib.core.AbstractPreferenceController;

public class IptabBlockScriptModePreferenceController extends AbstractPreferenceController
        implements PreferenceControllerMixin, Preference.OnPreferenceChangeListener {

    private static final String TAG = "IptabBlockScriptPreferenceController";
    private static final String IPTAB_BLOCKSCRIPT_SWITCH_KEY = "ziptables_block_switch";

    private SwitchPreference mCaptivePortalMode;
    private final UserManager mUm;

    public IptabBlockScriptModePreferenceController(Context context) {
        super(context);
        mUm = UserManager.get(context);
    }

    @Override
    public void updateState(Preference preference) {
        boolean value = SystemProperties.getBoolean("persist.privacy.iptab_blk", false);
        ((SwitchPreference) preference).setChecked(value);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        SystemProperties.set("persist.privacy.iptab_blk",
                ((Boolean) newValue) ? "1" : "0" );
        return true;
    }

    @Override
    public boolean isAvailable() {
        return mUm.isAdminUser();
    }

    @Override
    public String getPreferenceKey() {
        return IPTAB_BLOCKSCRIPT_SWITCH_KEY;
    }
}
