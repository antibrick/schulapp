package com.example.myapplication.app;

import android.app.Activity;
import android.view.View;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.GregorianCalendar;

abstract class DayDrawer {
    static void DrawDay(GregorianCalendar day, View fragmentView, Activity activity) {
        //zeilen den stunden zuordnen 1,2,4,5,7,8,10,12,13
        int[] alleZeilen = new int[]{
                R.id.row1, R.id.row2, R.id.row3, R.id.row4,
                R.id.row5, R.id.row6, R.id.row7, R.id.row8,
                R.id.row9, R.id.row10, R.id.row11, R.id.row12,
                R.id.row13
        };
        int[] stundenZeilen = new int[]{
                R.id.row1, R.id.row2, R.id.row4,
                R.id.row5, R.id.row7, R.id.row8,
                R.id.row10, R.id.row12, R.id.row13
        };
        int[] stunden = new int[]{
                R.id.stunde1, R.id.stunde2, R.id.stunde3,
                R.id.stunde4, R.id.stunde5, R.id.stunde6,
                R.id.stunde7, R.id.stunde8, R.id.stunde9
        };

/*        int[] countdowns = new int[]{
                R.id.countdown1, R.id.countdown2, R.id.countdown3,
                R.id.countdown4, R.id.countdown5, R.id.countdown6,
                R.id.countdown7, R.id.countdown8, R.id.countdown9 }; */

        String[] lessons = StundenplannDatabaseHandler.getLessonsForWeekday(activity, day);

        for (int i = 0; i < 9; i++) {
            if (!lessons[i].equals("gap")) {
                ((TextView) fragmentView.findViewById(stunden[i])).setText(lessons[i]);

            }
        }

        //hintergrundfarbe
        for (int i = 0; i < 13; i++) {
            if (i % 2 == 0) {
                ((TableRow) fragmentView.findViewById(alleZeilen[i])).setBackgroundColor(0xFFF);
            }
            else {
                ((TableRow) fragmentView.findViewById(alleZeilen[i])).setBackgroundColor(0xCCC);
            }
        }

        //RÜCKWERTS letzte stunde suchen
        for (int i = 9; i > 0; i--) {
            if (!lessons[i-1].equals("gap")) {
                int lastLesson = i;
                break;
            }
        }

    }
}
//fragmentView.findViewById(stundenZeilen[i]).setBackgroundColor(0xFFF);
        //fragmentView.findViewById(stundenZeilen[i]).setVisibility(View.GONE);