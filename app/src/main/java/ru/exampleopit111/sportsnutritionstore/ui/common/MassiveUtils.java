package ru.exampleopit111.sportsnutritionstore.ui.common;

import java.util.List;

/**
 * Created Максим on 11.07.2018.
 * Copyright © Max
 */
public class MassiveUtils {
    public static String[] createMass(List<String> list) {
        String[] mass = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mass[i] = list.get(i);
        }
        return mass;
    }

}
