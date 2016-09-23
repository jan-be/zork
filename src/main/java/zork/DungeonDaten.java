package zork;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 *  Pfad vor dem Ausführen anpassen!
 */

public class DungeonDaten {
    public String[] daten;
    public int breite, hoehe;

    public DungeonDaten() throws FileNotFoundException {
        getDaten();
    }

    public void getDaten() throws FileNotFoundException {
        String temp;
        Scanner scanner;
        int laengeDerLaengstenZeile = 0;
        scanner = new Scanner(new File("E:/Info Projects/ZORK/src/main/resources/karte.txt"));
        temp = scanner.useDelimiter("\\A").next();
        scanner.close();

        daten = temp.split("\\r?\\n");

        for (String s : daten) {
            if (s.length() > laengeDerLaengstenZeile) {
                laengeDerLaengstenZeile = s.length();
            }
        }
        breite = laengeDerLaengstenZeile;
        hoehe = daten.length;

        for (int i = 0; i < daten.length; i++) {
            while (daten[i].length() < laengeDerLaengstenZeile) {
                daten[i] = daten[i] + " ";
            }
        }

    }

    public int getAnzahlMonster() {
        int tmp = 0;
        for (int i = 0; i < daten.length; i++) {
            for (int j = 0; j < daten[i].length(); j++) {
                if(daten[i].charAt(j)=='M') {
                    tmp++;
                }
            }
        }
        return tmp;
    }
}
