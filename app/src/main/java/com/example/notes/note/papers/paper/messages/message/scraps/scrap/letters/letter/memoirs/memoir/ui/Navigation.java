package com.example.notes.note.papers.paper.messages.message.scraps.scrap.letters.letter.memoirs.memoir.ui;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Navigation {
    private final FragmentManager fragmentManager;

    public Navigation(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction
                .replace(containerId, fragment)
                .commit();
    }

    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, String backStackTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackTag);
        }
        fragmentTransaction
                .replace(containerId, fragment)
                .commit();
    }

    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, boolean popBackStackBeforeAdd) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(popBackStackBeforeAdd){
            fragmentManager.popBackStack();
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack("");
        }
        fragmentTransaction
                .replace(containerId, fragment)
                .commit();
    }

    public void addFragment(int containerId, Fragment fragment, boolean addToBackStack, boolean popBackStackBeforeAdd, String backStackTag) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        if(popBackStackBeforeAdd){
            fragmentManager.popBackStack();
        }
        if (addToBackStack) {
            fragmentTransaction.addToBackStack(backStackTag);
        }
        fragmentTransaction
                .replace(containerId, fragment)
                .commit();
    }


}
