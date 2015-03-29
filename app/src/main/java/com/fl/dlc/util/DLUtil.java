package com.fl.dlc.util;

public class DLUtil {

    public static Double getValidOvers(String text) {

        System.out.println("Overs - " + text + " Format " + DLModel.getFormat());
        Double overs = 0.0;

        if (DLModel.getFormat() == DLConstants.ONE_DAY) {
            overs = 50.0;
        } else {
            overs = 20.0;
        }
        if (text == null || text.trim().equals("")) {
            return overs;
        }

        try {
            Double d = Double.parseDouble(text);

            if (d > 50.0) {
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

        } catch (Exception e) {
            return null;
        }

        return overs;

    }

    public static Integer getValidScore(String text) {

        Integer score = 0;

        try {

            score = Integer.parseInt(text);

        } catch (Exception e) {
            return null;
        }

        return score;
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

    public static int getFormat(int selectedItemPosition) {

        if (selectedItemPosition < 2) return DLConstants.ONE_DAY;
        else return DLConstants.T20;
    }
}
