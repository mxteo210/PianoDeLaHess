package com.example.pianodelahess;

import android.graphics.RectF;

public class Key {

    public int sound; // Identifiant de la note associée à cette touche
    public RectF rect; // Rectangle représentant la position et les dimensions graphiques de la touche
    public boolean down; // État de la touche : true si elle est pressée, false sinon
    public boolean isPlaying; // Indique si la note est en cours de lecture

    // Constructeur pour initialiser une touche avec ses dimensions (rect) et son son associé (sound)
    public Key(RectF rect, int sound) {
        this.sound = sound; // Définit la note associée
        this.rect = rect; // Définit la zone graphique de la touche
        this.down = false; // La touche est initialement non pressée
        this.isPlaying = false; // La note n'est pas en lecture au début
    }
}
