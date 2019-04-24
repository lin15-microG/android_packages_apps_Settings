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
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.dashboard.DashboardFragment;
import com.android.settingslib.core.AbstractPreferenceController;

import java.util.ArrayList;
import java.util.List;

/*
 *  Custom DNS Fragment
 */
public class NetworkCustomDNSFragment extends DashboardFragment implements
        CustomDNSip4DialogHost, CustomDNSip6DialogHost {

    private static final String TAG = "NetworkCustomDNS";

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.TYPE_UNKNOWN;
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }

    @Override
    protected int getPreferenceScreenResId() {
        return R.xml.network_custom_dns;
    }

    @Override
    protected List<AbstractPreferenceController> getPreferenceControllers(Context context) {
        return buildPreferenceControllers(context, this);
    }

    private static List<AbstractPreferenceController> buildPreferenceControllers(Context context,
            Fragment fragment) {
        final List<AbstractPreferenceController> controllers = new ArrayList<>();
        final CustomDNSSwitchPreferenceController customDNSSwitchPreferenceController =
                new CustomDNSSwitchPreferenceController(context);
        final CustomDNSip4PreferenceController customDNSip4PreferenceController =
                new CustomDNSip4PreferenceController(context, fragment);
        final CustomDNSip6PreferenceController customDNSip6PreferenceController =
                new CustomDNSip6PreferenceController(context, fragment);
        controllers.add(customDNSSwitchPreferenceController);
        controllers.add(customDNSip4PreferenceController);
        controllers.add(customDNSip6PreferenceController);
        return controllers;
    }

    public void onCustomDNSip4DialogConfirmed() {
        final CustomDNSip4PreferenceController controller =
                getPreferenceController(CustomDNSip4PreferenceController.class);
        controller.onCustomDNSip4DialogConfirmed();
    }

    public void onCustomDNSip6DialogConfirmed() {
        final CustomDNSip6PreferenceController controller =
                getPreferenceController(CustomDNSip6PreferenceController.class);
        controller.onCustomDNSip6DialogConfirmed();
    }
}
