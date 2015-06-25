/*
 * Copyright (C) 2015 Eduardo Pereira
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
package com.eduardopereira.rvfragsample.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.eduardopereira.rvfragsample.R;
import com.eduardopereira.rvfragsample.fragment.FragmentRVSample;

/**
 * Launcher Activity that will add a simple fragment to demonstrate how RecyclerViewFragment can be used.
 */
public class ActivityMain extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, FragmentRVSample.newInstance(), FragmentRVSample.FRAG_TAG).commit();
        }
    }
}