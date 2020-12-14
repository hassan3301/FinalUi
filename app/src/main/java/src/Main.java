package src;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Main {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        TechConferenceController tcc = new TechConferenceController();
    }

}
