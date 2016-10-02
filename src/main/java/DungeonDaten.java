import java.io.InputStream;
import java.util.Scanner;

class DungeonDaten {

    String[][] alleLevelDaten = new String[Frame.ANZAHL_LEVEL][100];
    int breite, hoehe;
    int[] anzahlMonsterProLevel = new int[Frame.ANZAHL_LEVEL];

    DungeonDaten() {
        for (int i = 0; i < Frame.ANZAHL_LEVEL; i++) {
            alleLevelDaten[i] = getDaten(i + 1);
            anzahlMonsterProLevel[i] = getAnzahlMonster(i);
        }
        hoehe = alleLevelDaten[0].length;
        breite = getBreite();
    }

    static int getAnzahlLevel() {
        int i = 0;
        while (Frame.class.getResourceAsStream("karten/" + (i + 1) + ".txt") != null) {
            i++;
        }
        return i;
    }

    private static String[] getDaten(int level) {
        String temp;
        String[] tempArray;
        int laengeDerLaengstenZeile = 0;

        InputStream inputStream = Frame.class.getResourceAsStream("karten/" + level + ".txt");

        Scanner scanner = new Scanner(inputStream);
        temp = scanner.useDelimiter("\\A").next();
        scanner.close();

        tempArray = temp.split("\\r?\\n");

        for (String s : tempArray) {
            if (s.length() > laengeDerLaengstenZeile) {
                laengeDerLaengstenZeile = s.length();
            }
        }

        for (int i = 0; i < tempArray.length; i++) {
            while (tempArray[i].length() < laengeDerLaengstenZeile) {
                tempArray[i] = tempArray[i] + " ";
            }
        }

        return tempArray;
    }

    private int getBreite() {
        int breite = 0;
        for (String s : alleLevelDaten[0]) {
            if (s.length() > breite) {
                breite = s.length();
            }
        }
        return breite;
    }


    private int getAnzahlMonster(int level) {
        String[] temp = alleLevelDaten[level];
        int tmp = 0;
        for (String aTemp : temp) {
            for (int j = 0; j < aTemp.length(); j++) {
                if (aTemp.charAt(j) == 'M') {
                    tmp++;
                }
            }
        }
        return tmp;
    }
}
