package com.testt.productlist.ui;

import android.view.View;

import androidx.test.rule.ActivityTestRule;

import com.testt.productlist.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class ListActivityTest {

    @Rule
    public ActivityTestRule<ListActivity> mListActivityActivityTestRule = new ActivityTestRule<ListActivity>(ListActivity.class);

    private ListActivity mListActivity = null;

    @Before
    public void setUp() throws Exception {

        mListActivity = mListActivityActivityTestRule.getActivity();

    }

    @Test
    public void testLaunch(){

        View recyclerView = mListActivity.findViewById(R.id.recyclerView);
        assertNotNull(recyclerView);

    }


    @After
    public void tearDown() throws Exception {
        mListActivity = null;
    }
}