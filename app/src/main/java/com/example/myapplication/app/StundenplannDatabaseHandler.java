package com.example.myapplication.app;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

abstract class StundenplannDatabaseHandler {
    private static String[] daynames = new String[]{"montag", "dienstag", "mittwoch", "donnerstag", "freitag"};

    protected static String[] getLessonsForWeekday(Activity activity, GregorianCalendar day) {
        SQLiteDatabase databaseConnection = new StundenplanDatabaseHelper(activity) {
        }.getReadableDatabase();
        Cursor result = databaseConnection.rawQuery("select subject from " + daynames[day.get(Calendar.DAY_OF_WEEK) - 2], null);
        result.moveToFirst();
        ArrayList<String> lessons = new ArrayList<String>();
        //TODO: Pausen
        for (int i = 0; i < result.getCount(); i++) {
            if (!result.getString(0).equals("gap")) {
                lessons.add(result.getString(0));
            } else {
                lessons.add("gap");
            }
            if (i == 1 || i == 3 || i == 6 || i == 7 ){

            }
            result.moveToNext();
        }

        databaseConnection.close();
        return lessons.toArray(new String[lessons.size()]);
    }

    protected static GregorianCalendar getNextDayWithLesson(Activity activity) {
        //TODO: einbauen: freitag nach der schule

        int lastLessonsId = -1;
        GregorianCalendar gerade = new GregorianCalendar();
        //sonn oder samstag?
        if ((gerade.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) || (gerade.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)) {
            //wenn ja: gibt montag zurück
            return new GregorianCalendar() {
                {
                    this.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                }
            };
        }
        //wenn nein: heute schon zu ende?
        else {
            //schauen, was heute die letzte stunde ist
            SQLiteDatabase databaseConnection = new StundenplanDatabaseHelper(activity) {
            }.getReadableDatabase();
            Cursor result = databaseConnection.rawQuery("select subject from " + daynames[gerade.get(Calendar.DAY_OF_WEEK) - 1], null);
            result.moveToLast();
            for (int i = result.getCount(); i > 0; i--) {
                if (result.getString(0).equals("gap")) {
                    lastLessonsId = i;
                    break;
                }
                result.moveToPrevious();
            }
            System.out.println(lastLessonsId);
        }
        //letzte stunde in Date umrechnen für vergleich
        class endTime extends GregorianCalendar {
            protected endTime(int hour, int minute, GregorianCalendar gerade) {
                super();
                this.setTime(gerade.getTime());
                this.set(Calendar.HOUR_OF_DAY, hour);
                this.set(Calendar.MINUTE, minute);
            }
        }

        GregorianCalendar[] lessonends = new GregorianCalendar[]{
                new endTime(9, 0, gerade), new endTime(9, 45, gerade),
                new endTime(10, 45, gerade), new endTime(11, 30, gerade),
                new endTime(12, 25, gerade), new endTime(13, 10, gerade),
                new endTime(14, 5, gerade),  new endTime(14, 55, gerade),
                new endTime(14, 40, gerade)
        };
        //TODO:Pausen mit einrechnen:
        //Pausenenden: new endTime(10, 0, gerade), new endTime(11, 45, gerade),new endTime(13, 20, gerade), new endTime(14, 10, gerade),

        GregorianCalendar schoolEnd = lessonends[lastLessonsId - 1];

        System.out.println(schoolEnd);
        System.out.println(gerade);

        //stunde vorbei?
        GregorianCalendar nextDayWithSchool = new GregorianCalendar();
        if (schoolEnd.before(gerade)) {
            //wenn ja: morgen oder montag, wenn freitag
            nextDayWithSchool.set(Calendar.DAY_OF_WEEK, gerade.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY ? Calendar.MONDAY : gerade.get(Calendar.DAY_OF_WEEK) + 1);
            return nextDayWithSchool;
        }
        //wenn nein: heute
        else {
            return nextDayWithSchool;
        }
    }
}
