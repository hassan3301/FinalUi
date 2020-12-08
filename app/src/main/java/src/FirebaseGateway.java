package src;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class FirebaseGateway {
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static void WriteToDB(String collection, Map<String, Attendee> map, String User){
        final Boolean[] success = {null};
        db.collection(collection)
                .document(User)
                .set(map);
    }
    public static Map ReadFromDB(String collection, String document) {
        final Map[] map = new Map[]{null};
        DocumentReference docRef = db.collection(collection).document(document);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        map[0] = document.getData();
                    }
                }
            }
        });
        return map[0];
    }

}
