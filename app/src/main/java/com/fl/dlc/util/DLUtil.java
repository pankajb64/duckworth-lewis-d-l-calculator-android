package com.fl.dlc.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class DLUtil {

    public static Double getValidOvers(String text, int team) {

        System.out.println("Overs - " + text + " Format " + DLModel.getFormat());
        Double overs = 0.0;

        overs = getMaxOvers(team);

        if (text == null || text.trim().equals("")) {
            return overs;
        }

        try {
            Double d = Double.parseDouble(text);

            if (d > overs) {
                return null;
            }

            String[] a = text.split(".");

            if (a.length > 2) {
                String s = a[1];

                if (s.length() > 1) {
                    return null;
                }

                int frac = Integer.parseInt(a[1]);

                if (frac > 6) {
                    return null;
                }

                if (frac == 6) {
                    overs = Integer.parseInt(a[0]) + 1.0;
                }
            }

            overs = d;

            if (overs < 0) {
                return null;
            }

        } catch (Exception e) {
            return null;
        }

        return overs;

    }

    public static Integer getValidScore(String text) {

        Integer score = 0;

        try {

            score = Integer.parseInt(text);

            if (score < 0) {
                return null;
            }
        } catch (Exception e) {
            return null;
        }

        return score;
    }

    public static Integer getValidWickets(String text) {

        Integer wickets = getValidScore(text);

        if (wickets == null) {
            return null;
        } else {
            if (wickets > 10) {
                return null;
            }
        }

        return wickets;
    }

    public static int getG(int format, int type) {

        if (type == 0) return DLConstants.G50_ODI_TEST_NATIONS;
        else return DLConstants.G50_ODI_REST;
    }

    public static String calculateResult() {

        double r1 = getTeam1Resource();
        double r2 = getTeam2Resource();
        double s = DLModel.getT1FinalScore();
        double final_score = (s * r2 / r1) + 1;

        return "Team 2 needs " + (int) final_score + " runs to win from " + DLModel.getT2StartOvers() + " overs with 10 wickets remaining (D/L Method)";
    }

    private static double getTeam2Resource() {
        return 100.0;
    }

    private static double getTeam1Resource() {
        return 100.0;
    }

    public static void showAlertDialog(Context ctx, String title, String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }

    public static double getMaxOvers(int team) {

        double overs = 0;

        if (DLModel.getFormat() <= DLConstants.ODI || DLModel.getFormat() == DLConstants.ODD) {
            overs = DLConstants.MAX_ODI_OVERS;
        } else {
            overs = DLConstants.MAX_T20_OVERS;
        }

        if (team == DLConstants.TEAM_2) {
            overs = DLModel.getT1StartOvers();
        }

        return overs;
    }

    public static double getStartOvers(int team) {

        Double overs;

        if (team == DLConstants.TEAM_1) {
            overs = DLModel.getT1StartOvers();

            if (overs == null) {
                overs = getMaxOvers(team);
            }
        } else {
            overs = DLModel.getT2StartOvers();

            if (overs == null) {
                overs = getStartOvers(DLConstants.TEAM_1);
            }
        }

        return overs;
    }

    public static double getOverDifference(Double startOvers, Double endOvers) {

        /*if(startOvers == Math.floor(startOvers)) {
            startOvers = startOvers - 0.4; // for eg 12 becomes 11.6
        }*/

        Double startOvers1 = Math.floor(startOvers);
        Double startOvers2 = startOvers - startOvers1;

        Double endOvers1 = Math.floor(endOvers);
        Double endOvers2 = endOvers - endOvers1;

        Double diff1 = startOvers1 - endOvers1;
        Double diff2 = startOvers2 - endOvers2;

        if (diff2 < 0) {
            diff1 -= 0.4; // for eg 12 becomes 11.6
        }

        diff1 += diff2;

        return diff1;
    }
}
