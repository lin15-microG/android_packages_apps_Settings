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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.net.NetworkUtils;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.internal.logging.nano.MetricsProto;
import com.android.settings.R;
import com.android.settings.core.instrumentation.InstrumentedDialogFragment;

import java.net.Inet6Address;


public class CustomDNSip6Dialog extends InstrumentedDialogFragment implements
        DialogInterface.OnClickListener, DialogInterface.OnShowListener {

    public static final String TAG = "CustomDNSip6Dialog";

    private static EditText mIp6Input;

    public static void show(Fragment host) {
        final FragmentManager manager = host.getActivity().getFragmentManager();
        if (manager.findFragmentByTag(TAG) == null) {
            final CustomDNSip6Dialog dialog = new CustomDNSip6Dialog();
            dialog.setTargetFragment(host, 0 /* requestCode */);
            dialog.show(manager, TAG);
        }
    }

    @Override
    public int getMetricsCategory() {
        return MetricsProto.MetricsEvent.TYPE_UNKNOWN;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mIp6Input = new EditText(getActivity());
        AlertDialog alertDialog =
            new AlertDialog.Builder(getActivity())
                .setTitle(R.string.network_custom_dns_title)
                .setMessage(R.string.network_custom_dns_ip6_title)
                .setView(mIp6Input)
                .setPositiveButton(android.R.string.ok, null)
                .setNeutralButton(R.string.network_custom_dns_reset, null)
                .setNegativeButton(android.R.string.cancel, this)
                .create();
            alertDialog.setOnShowListener(this);
            return alertDialog;
    }

    @Override
    public void onShow(DialogInterface dialog) {
        final CustomDNSip6DialogHost host = (CustomDNSip6DialogHost) getTargetFragment();
        if (host == null) {
            dialog.dismiss();
            return;
        }

        Button buttonPositive = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_POSITIVE);
        buttonPositive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isValidIp6(mIp6Input.getText().toString())) {
                    host.onCustomDNSip6DialogConfirmed();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(),
                        R.string.wifi_ip_settings_invalid_ip_address,
                        Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button buttonReset = ((AlertDialog) dialog).getButton(DialogInterface.BUTTON_NEUTRAL);
        buttonReset.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mIp6Input.setText("");
                host.onCustomDNSip6DialogConfirmed();
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        mIp6Input.setText("");
    }

    private Inet6Address getIPv6Address(String text) {
        try {
            return (Inet6Address) NetworkUtils.numericToInetAddress(text);
        } catch (IllegalArgumentException | ClassCastException e) {
            return null;
        }
    }

    private boolean isValidIp6(String ipAddr) {
        if (ipAddr.equals("")) return false;

        Inet6Address inetAddr = getIPv6Address(ipAddr);
        if (inetAddr == null || inetAddr.equals(Inet6Address.LOOPBACK) ||
                inetAddr.equals(Inet6Address.ANY)) {
            return false;
        }

        return true;
    }

    public static String getResult() {
        return mIp6Input.getText().toString();
    }
}
