package src;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FirebaseGateway {
    public static ArrayList<Map<String, Object>> MapArray = new ArrayList();
    private static Object a1;
    private UserAccount userAccount = new UserAccount();
    static FirebaseFirestore db = FirebaseFirestore.getInstance();
    static CollectionReference docRef = db.collection("TCC");

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static <V> void WriteToDB(String collection, Map<String, V> map, String document){
        for(Map.Entry<String, V> mapElement : map.entrySet()){
            if(mapElement.getValue() instanceof Event){
                if(((Event) mapElement.getValue()).getSpeaker() instanceof String[]) {
                    String value = ((Event) mapElement.getValue()).getSpeaker()[0];
                    db.collection(collection)
                            .document(document).update(mapElement.getKey(), value.toString());
                }
                db.collection(collection)
                        .document(document).update(mapElement.getKey(), mapElement.getValue());
            }
            else {
                db.collection(collection)
                        .document(document).update(mapElement.getKey(), mapElement.getValue());
            }

        }

    }

    public static void DeleteFromDB(String item, String document){
        Map<String,Object> updates = new HashMap<>();
        updates.put(item, FieldValue.delete());
        docRef.document(document).update(updates);
    }

    public static void GetMaps(TechConferenceController tc) {
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
                        FirebaseGateway.FillMap(document, tc, document.getId());
                        System.out.println("test1");
                    }
                }
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void FillMap(QueryDocumentSnapshot document, TechConferenceController tc, String type){
        final ObjectMapper mapper = new ObjectMapper();
        if (type.equals("Attendee")){
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                Object messages_recieved = new_map.get("messages_recieved2");
                if(messages_recieved == null){
                    messages_recieved = Collections.<String, ArrayList<String>>emptyMap();
                }
                Object messenger_list = new_map.get("messengerList");
                if(messenger_list == null){
                    messenger_list = Collections.<String>emptyList();
                }
                Object eventsAttending = new_map.get("eventsAttending");
                if(eventsAttending == null){
                    eventsAttending = Collections.<String>emptyList();
                }
                System.out.println(new_map);
                tc.getAc().attendeesAccount.makeAttendee((String) new_map.get("username"),(String) new_map.get("password"),(Map<String, ArrayList<String>>) messages_recieved, (Map<String, ArrayList<String>>) new_map.get("allMessagesRecieved"),(ArrayList<String>) eventsAttending, (ArrayList<String>) messenger_list);

            });
        }
        else if (type.equals("Organizer")){
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
               Object messages_recieved = new_map.get("messages_recieved2");
                if(messages_recieved == null){
                   messages_recieved = Collections.<String, ArrayList<String>>emptyMap();
                }
                Object messenger_list = new_map.get("messengerList");
                if(messenger_list == null){
                    messenger_list = Collections.<String>emptyList();
                }
                Object eventsAttending = new_map.get("eventsAttending");
                if(eventsAttending == null){
                    eventsAttending = Collections.<String>emptyList();
                }
                tc.getOC().oa.makeOrganizer((String) new_map.get("username"),(String) new_map.get("password"), (Map<String, ArrayList<String>>) messages_recieved, (Map<String, ArrayList<String>>) new_map.get("allMessagesRecieved"),(ArrayList<String>) eventsAttending, (ArrayList<String>) messenger_list);
            });
        }
        else if (type.equals("Speaker")){
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                Object messages_recieved = new_map.get("messages_recieved2");
                if(messages_recieved == null){
                    messages_recieved = Collections.<String, ArrayList<String>>emptyMap();
                }
                Object messenger_list = new_map.get("messengerList");
                if(messenger_list == null){
                    messenger_list = Collections.<String>emptyList();
                }
                Object eventsAttending = new_map.get("eventsAttending");
                if(eventsAttending == null){
                    eventsAttending = Collections.<String>emptyList();
                }
                tc.getScon().speakeraccount.makeSpeaker((String) new_map.get("username"),(String) new_map.get("password"),(Map<String, ArrayList<String>>) messages_recieved, (Map<String, ArrayList<String>>) new_map.get("allMessagesRecieved"),(ArrayList<String>) eventsAttending, (ArrayList<String>) messenger_list, (ArrayList<String>) new_map.get("speakingAt") );
            });
        }
        else if (type.equals("VIP")){
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                Object messages_recieved = new_map.get("messages_recieved2");
                if(messages_recieved == null){
                    messages_recieved = Collections.<String, ArrayList<String>>emptyMap();
                }
                Object messenger_list = new_map.get("messengerList");
                if(messenger_list == null){
                    messenger_list = Collections.<String>emptyList();
                }
                Object eventsAttending = new_map.get("eventsAttending");
                if(eventsAttending == null){
                    eventsAttending = Collections.<String>emptyList();
                }
                tc.getVIP().vip.makeVIP((String) new_map.get("username"),(String) new_map.get("password"),(Map<String, ArrayList<String>>) messages_recieved, (Map<String, ArrayList<String>>) new_map.get("allMessagesRecieved"),(ArrayList<String>) eventsAttending, (ArrayList<String>) messenger_list);
            });
        }
        else if (type.equals("Events")){
            System.out.println(document.getData());
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                Object attendee_list = new_map.get("attendee_list");
                if(attendee_list == null){
                    attendee_list= Collections.<String>emptyList();
                }
                tc.getEm().makeEvent((String) new_map.get("name"),(String) new_map.get("place"),(Calendar) new_map.get("start_time"), (Calendar) new_map.get("end_time"),(String[]) new_map.get("speaker"), (String) new_map.get("description"), (int) new_map.get("limit"), (String) new_map.get("type"),(ArrayList<String>) attendee_list );
            });
        }
        else if (type.equals("Room")){
            System.out.println("catch");
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                tc.getRm().makeRoom((long) new_map.get("capacity"),(ArrayList<String>) new_map.get("eventsInRoom"), (String) new_map.get("name") );
                System.out.println(new_map);
            });
        }
        else{
            document.getData().forEach((k, v)->{
                Map<String, Object> new_map = mapper.convertValue(v, Map.class);
                Message message = new Message((String) new_map.get("text"));
                UserAccount.idToMessage.put(message.getId(), message);
            });
        }

    }
}


