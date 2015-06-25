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

package com.eduardopereira.support;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * A fragment that displays a recycler view of items by binding to a data source such as
 * an array, exposing an event handler when the user clicks an item.
 * <p>
 * RecyclerViewFragment hosts a {@link android.support.v7.widget.RecyclerView RecyclerView} object that can
 * be bound to different data sources, typically an array. Binding and screen layout are discussed
 * in the following sections.
 * <p>
 * <strong>Screen Layout</strong>
 * </p>
 * <p>
 * RecyclerViewFragment has a default layout that consists of a single recycler view.
 * However, if you desire, you can customize the fragment layout by returning
 * your own view hierarchy from {@link #onCreateView}.
 * To do this, your view hierarchy <em>must</em> contain a RecyclerView object with the
 * id {@link R.id#recycler R.id.recycler}.
 * <p>
 * The following code demonstrates a simple custom recycler layout. It has a recycler view and a text view.
 * </p>
 *
 * <pre>
 * &lt;?xml version=&quot;1.0&quot; encoding=&quot;utf-8&quot;?&gt;
 * &lt;RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
 *         android:layout_width=&quot;match_parent&quot;
 *         android:layout_height=&quot;match_parent&quot;
 *         android:paddingLeft=&quot;8dp&quot;
 *         android:paddingRight=&quot;8dp&quot;&gt;
 *
 *     &lt;TextView android:id=&quot;@+id/textview&quot;
 *               android:layout_width=&quot;match_parent&quot;
 *               android:layout_height=&quot;wrap_content&quot;
 *               android:text=&quot;My custom layout&quot;/&gt;
 *
 *     &lt;android.support.v7.widget.RecyclerView android:id=&quot;@id/recycler&quot;
 *               android:layout_width=&quot;match_parent&quot;
 *               android:layout_height=&quot;match_parent&quot;
 *               android:layout_alignParentTop=&quot;true&quot;
 *               android:layout_below=&quot;@+id/textview&quot;/&gt;
 *
 * &lt;/RelativeLayout&gt;
 * </pre>
 *
 * <p>
 * <strong>Binding to Data</strong>
 * </p>
 * <p>
 * You bind the RecyclerViewFragment's RecyclerView object to data using a class that
 * extends the {@link android.support.v7.widget.RecyclerView.Adapter RecyclerView.Adapter} abstract class.
 * </p>
 * <p>
 * You <b>must</b> use
 * {@link #setRecyclerAdapter(android.support.v7.widget.RecyclerView.Adapter) RecyclerViewFragment.setRecyclerAdapter()} to
 * associate the recycler view with an adapter. Do not directly call
 * {@link RecyclerView#setAdapter(android.support.v7.widget.RecyclerView.Adapter) RecyclerView.setAdapter()} or else
 * important initialization will be skipped.
 * </p>
 *
 * @see #setLayoutManager(RecyclerView.LayoutManager)
 * @see #setRecyclerAdapter(RecyclerView.Adapter)
 * @see android.support.v7.widget.RecyclerView
 */
public abstract class RecyclerViewFragment extends Fragment {

    final private Handler mHandler = new Handler();

    final private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mRecycler.focusableViewAvailable(mRecycler);
        }
    };

    private RecyclerView mRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.OnItemTouchListener mDefaultOnItemTouchListener = new RecyclerItemClickListener(getActivity());

    /**
     * Provide default implementation to return a simple recycler view.
     *
     * Subclasses can override to replace with their own layout. If doing so, the
     * returned view hierarchy <em>must</em> have a RecyclerView whose id
     * is {@link R.id#recycler R.id.recycler}.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.recycler_content, container, false);
    }

    /**
     * Attach to recycler view once the view hierarchy has been created.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ensureRecycler();
    }

    /**
     * Detach from recycler view.
     */
    @Override
    public void onDestroyView() {
        mHandler.removeCallbacks(mRequestFocus);
        mRecycler.removeOnItemTouchListener(mDefaultOnItemTouchListener);
        mRecycler = null;
        mLayoutManager = null;
        mAdapter = null;
        super.onDestroyView();
    }

    /**
     * Get the fragment's recycler view widget.
     */
    public RecyclerView getRecyclerView() {
        ensureRecycler();
        return mRecycler;
    }

    /**
     * Provide the RecyclerView.Adapter for the recycler view.
     */
    public void setRecyclerAdapter(RecyclerView.Adapter adapter) {
        mAdapter = adapter;

        if (mRecycler != null) {
            mRecycler.setAdapter(adapter);
        }
    }

    /**
     * Get the RecyclerView.Adapter associated with this fragment's RecyclerView.
     */
    public RecyclerView.Adapter getRecyclerAdapter() {
        return mAdapter;
    }

    /**
     * Provide the RecyclerView.LayoutManager for the recycler view.
     */
    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mLayoutManager = layoutManager;

        if (mRecycler != null) {
            mRecycler.setLayoutManager(layoutManager);
        }
    }

    /**
     * Get the layout manager associated with this fragment's RecyclerView.
     * <p>
     *     To provide a layout manager for the fragment's RecyclerView use {@link #setLayoutManager(RecyclerView.LayoutManager)}.
     *     If none has been defined na instance of a vertical {@link LinearLayoutManager} will be returned.
     * <p/>
     *
     * @see LinearLayoutManager
     */
    public RecyclerView.LayoutManager getLayoutManager() {
        if(mLayoutManager == null) {
            mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        }

        return mLayoutManager;
    }

    /**
     * This method will be called when an item in the recycler view is selected.
     *
     * Subclasses should override.
     *
     * @param recyclerView The RecyclerView where the click happened.
     * @param view The view that was clicked within the RecyclerView.
     * @param position The position of the view in the recycler view.
     */
    public void onRecyclerItemClick(RecyclerView recyclerView, View view, int position) { }

    private void ensureRecycler() {
        if (mRecycler != null) {
            return;
        }

        View root = getView();
        if (root == null) {
            throw new IllegalStateException("Content view not yet created");
        }

        if (root instanceof RecyclerView) {
            mRecycler = (RecyclerView)root;
        } else {
            View rawRecyclerView = root.findViewById(R.id.recycler);
            if (rawRecyclerView != null && !(rawRecyclerView instanceof RecyclerView)) {
                throw new RuntimeException(
                        "Content has view with id attribute 'R.id.recycler' that is not a RecyclerView class");
            }

            mRecycler = (RecyclerView)rawRecyclerView;
            if (mRecycler == null) {
                throw new RuntimeException(
                        "Your content must have a RecyclerView whose id attribute is 'R.id.recycler'");
            }
        }

        mRecycler.setLayoutManager(getLayoutManager());
        mRecycler.setHasFixedSize(true);
        mRecycler.addOnItemTouchListener(mDefaultOnItemTouchListener);
        if (mAdapter != null) {
            RecyclerView.Adapter adapter = mAdapter;
            mAdapter = null;
            setRecyclerAdapter(adapter);
        }

        mHandler.post(mRequestFocus);
    }

    private class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector mGestureDetector;

        public RecyclerItemClickListener(Context context) {

            mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }
            });
        }

        @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mGestureDetector.onTouchEvent(e)) {
                onRecyclerItemClick(view, childView, view.getChildAdapterPosition(childView));
                return true;
            }

            return false;
        }

        @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) { /** Do nothing! */ }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) { /** Do nothing! */ }
    }
}