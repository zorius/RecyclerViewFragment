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

package com.eduardopereira.support.utils;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import java.util.Arrays;

/**
 * Created by eduardo on 31/07/15.
 */
public class LayoutManagerHelper {

    public static int findFirstVisibleItemPosition(RecyclerView.LayoutManager lm) {
        if (lm == null)
            throw new IllegalArgumentException("The RecyclerView.layoutManager instance must not be null.");

        if (lm instanceof LinearLayoutManager) {
            /** This also includes GridLayoutManager which is a subclass of LinearLayoutManager. */
            return ((LinearLayoutManager) lm).findFirstVisibleItemPosition();
        } else if (lm instanceof StaggeredGridLayoutManager) {
            int[] into = {};
            int[] out = ((StaggeredGridLayoutManager) lm).findFirstCompletelyVisibleItemPositions(into);
            Arrays.sort(out);

            return out[0];
        }

        return RecyclerView.NO_POSITION;
    }
}