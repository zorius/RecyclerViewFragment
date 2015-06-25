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
package com.eduardopereira.rvfragsample.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eduardopereira.support.RecyclerViewFragment;

import java.util.Random;

/**
 * Fragment sample that simply uses a RecyclerView widget with a vertical layout manager.
 */
public class FragmentRVSample extends RecyclerViewFragment {

    public static final String FRAG_TAG = "FragmentRVSample";

    public static FragmentRVSample newInstance() {
        return new FragmentRVSample();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setRecyclerAdapter(new RVSampleAdapter());
    }

    @Override
    public void onRecyclerItemClick(RecyclerView recyclerView, View view, int position) {
        super.onRecyclerItemClick(recyclerView, view, position);

        Snackbar.make(view, "Text: " + ((TextView) view.findViewById(android.R.id.text1)).getText() + " | position: " + position, Snackbar.LENGTH_SHORT).show();
    }

    /** Sample adapter class to bind RecyclerView with dummy data. */
    private class RVSampleAdapter extends RecyclerView.Adapter<RVSampleAdapter.VHSample> {

        public class VHSample extends RecyclerView.ViewHolder {

            TextView mText;

            public VHSample(View view) {
                super(view);
                mText = (TextView) view.findViewById(android.R.id.text1);
            }
        }

        private static final int MAX_ITEMS_TO_SHOW = 100;
        private Random mRdm;

        public RVSampleAdapter() {
            mRdm = new Random();
        }

        @Override
        public int getItemCount() {
            return MAX_ITEMS_TO_SHOW;
        }

        @Override
        public VHSample onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(android.R.layout.simple_list_item_1, parent, false);
            return new VHSample(view);
        }

        @Override
        public void onBindViewHolder(VHSample holder, int position) {
            holder.mText.setText("Item: " + position);
            holder.mText.setBackgroundColor(Color.rgb(mRdm.nextInt(256), mRdm.nextInt(256), mRdm.nextInt(256)));
        }
    }
}