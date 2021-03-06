/*
 * Copyright 2018 Pranav Pandey
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

package com.pranavpandey.android.dynamic.support.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.pranavpandey.android.dynamic.support.R;
import com.pranavpandey.android.dynamic.support.activity.DynamicActivity;
import com.pranavpandey.android.dynamic.support.adapter.DynamicFragmentsAdapter;

import java.util.List;

/**
 * An abstract {@link ViewPager} fragment to display multiple fragments inside the view pager
 * along with the tabs. Just extend this fragment and implement the necessary functions to use
 * it inside an activity.
 *
 * @see #getTitles()
 * @see #getPages()
 */
public abstract class DynamicViewPagerFragment extends DynamicFragment {

    /**
     * Fragment argument key to set the initial view pager page.
     */
    public static String ADS_ARGS_VIEW_PAGER_PAGE = "ads_args_view_pager_page";

    /**
     * View pager used by this fragment.
     */
    private ViewPager mViewPager;

    /**
     * Tab layout used by this fragment.
     */
    private TabLayout mTabLayout;

    /**
     * List of tab titles to use with the tab layout.
     */
    private List<String> mTitles;

    /**
     * List of fragments to show in the view pager.
     */
    private List<Fragment> mPages;

    /**
     * View pager adapter used by this fragment.
     */
    private ViewPagerAdapter mAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitles = getTitles();
        mPages = getPages();
        mAdapter = new ViewPagerAdapter(getChildFragmentManager(), mTitles, mPages);
    }

    /**
     * Abstract method to initialize a list of tab titles to be used with the tab layout.
     *
     * @return A list containing the tab titles.
     */
    protected abstract List<String> getTitles();

    /**
     * Abstract method to initialize a list fragments to be shown in the view pager.
     *
     * @return A list containing the fragments.
     */
    protected abstract List<Fragment> getPages();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.ads_fragment_view_pager, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewPager = view.findViewById(R.id.ads_view_pager);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ((DynamicActivity) getActivity()).addHeader(R.layout.ads_tabs, true);
        mTabLayout = getActivity().findViewById(R.id.ads_tab_layout);

        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    /**
     * Get the tab layout used by this fragment.
     *
     * @return The tab layout used by this fragment.
     */
    public TabLayout getTabLayout() {
        return mTabLayout;
    }

    /**
     * Get the view pager used by this fragment.
     *
     * @return The view pager used by this fragment.
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * Returns the currently selected view pager page or position.
     *
     * @return The currently selected view pager page or position.
     */
    public int getCurrentPage() {
        return mViewPager.getCurrentItem();
    }

    /**
     * Set the current page or position for the view pager.
     *
     * @param page The current position for the view pager.
     */
    public void setPage(final int page) {
        mViewPager.setCurrentItem(page);
    }

    /**
     * View pager adapter to display the supplied fragments with tab titles.
     */
    static class ViewPagerAdapter extends DynamicFragmentsAdapter {

        /**
         * Tab titles list for this adapter.
         */
        private final List<String> titles;

        /**
         * Fragments list for this adapter.
         */
        private final List<Fragment> pages;

        /**
         * Constructor to initialize an object of this class.
         *
         * @param fragmentManager The fragment manager for this adapter.
         * @param titles The tab titles list for this adapter.
         * @param pages The fragments list for this adapter.
         */
        ViewPagerAdapter(@NonNull FragmentManager fragmentManager,
                @NonNull List<String> titles, @NonNull List<Fragment> pages) {
            super(fragmentManager);

            this.titles = titles;
            this.pages = pages;
        }

        @Override
        public Fragment createItem(int position) {
            return pages.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }

        @Override
        public int getCount() {
            return pages.size();
        }
    }
}
