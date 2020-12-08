package src;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TechConferenceController tcc = new TechConferenceController();
        tcc.mainMenu();
    }

}
