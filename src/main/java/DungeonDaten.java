import java.io.InputStream;
import java.util.Scanner;

class DungeonDaten {

    String[][] alleLevelDaten = new String[3][100];
    int breite, hoehe, anzahlMonster;

    DungeonDaten() {
        for (int i = 0; i < 2; i++) {
            alleLevelDaten[i] = getDaten(i+1);
        }
        hoehe = alleLevelDaten[0].length;
        breite = getBreite();
        anzahlMonster = getAnzahlMonster();
    }

    private static String[] getDaten(int level) {
        String temp;
        String[] tempArray;
        int laengeDerLaengstenZeile = 0;

        InputStream inputStream = Frame.class.getResourceAsStream("karten/" +level+".txt");

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


    private int getAnzahlMonster() {
        String[] temp = alleLevelDaten[0];
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
