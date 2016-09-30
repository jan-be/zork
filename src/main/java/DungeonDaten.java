import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  Pfad vor dem Ausführen anpassen!
 */

public class DungeonDaten {

    String[][] alleLevelDaten = new String[3][100];
    int breite, hoehe, anzahlMonster;

    public DungeonDaten() {
        for (int i = 0; i < 2; i++) {
            alleLevelDaten[i] = getDaten(i+1);
        }
        hoehe = getHoehe();
        breite = getBreite();
        anzahlMonster = getAnzahlMonster();
    }

    public static String[] getDaten(int level) {
        String temp;
        String[] tempArray;
        Scanner scanner = null;
        int laengeDerLaengstenZeile = 0;

        String pathOhneBuchstabe = ":/Info Projects/ZORK/src/main/resources/karte" + level + ".txt";
        String pathMitBuchstabe = "";
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            if (new File(letter + pathOhneBuchstabe).exists()) {
                pathMitBuchstabe = letter + pathOhneBuchstabe;
            }
        }

        try {
            scanner = new Scanner(new File(pathMitBuchstabe));
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Karte"+level+".txt existiert nicht", "ALARM", 0);
            System.exit(0);
        }
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

    public int getBreite() {
        int breite = 0;
        for (String s : alleLevelDaten[0]) {
            if (s.length() > breite) {
                breite = s.length();
            }
        }
        return breite;
    }

    public  int getHoehe() {
        return alleLevelDaten[0].length;
    }


    public int getAnzahlMonster() {
        String[] temp = alleLevelDaten[0];
        int tmp = 0;
        for (int i = 0; i < temp.length; i++) {
            for (int j = 0; j < temp[i].length(); j++) {
                if (temp[i].charAt(j) == 'M') {
                    tmp++;
                }
            }
        }
        return tmp;
    }
}
