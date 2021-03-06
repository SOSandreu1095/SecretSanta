package com.secretsanta.secretsanta;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Andreu on 06/02/2018.
 */

public class ParticipantLab {

    private static ParticipantLab sParticipantLab;
    private List<Person> lParticipants;

    /**
     * Este metodo es lo que llamaremos para crear la clase, si no existe la lista de participantes
     * nos la crea y nos la devuelve, si ya existia nos la devolvera tambien
     * @param context
     * @return
     */
    public static ParticipantLab get(Context context) {
        if (sParticipantLab == null) {
            sParticipantLab = new ParticipantLab(context);
        }
        return sParticipantLab;
    }
    private ParticipantLab(Context context) {
        this.lParticipants = new ArrayList<>();
    }

    public Person getParticipant(UUID id) {
        for (Person p : lParticipants) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
    public void clear(){
        lParticipants.clear();
    }

    public Person getParticipant(int i){
        return this.lParticipants.get(i);
    }


    public List getParticipants() {
        return this.lParticipants;
    }


    public int getNumberParticipants(){
        return this.lParticipants.size();
    }

    public void addParticipant(Person p){
        this.lParticipants.add(p);
    }

    public void removeParticipant(Person p){
        this.lParticipants.remove(p);
    }


}
