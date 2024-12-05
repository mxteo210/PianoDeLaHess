package com.example.pianodelahess;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.SparseArray;

public class AudioSoundPlayer {

    private SparseArray<MediaPlayer> playerMap = new SparseArray<>();
    private Context context;

    // Map des noms de fichiers associés aux notes
    private static final SparseArray<String> SOUND_MAP = new SparseArray<>();

    public void setInstrument(String instrument) {
        SOUND_MAP.clear(); // Efface les sons actuels

        switch (instrument) {
            case "Piano":
                SOUND_MAP.put(1, "note_do.mp3");
                SOUND_MAP.put(2, "note_re.mp3");
                SOUND_MAP.put(3, "note_mi.mp3");
                SOUND_MAP.put(4, "note_fa.mp3");
                SOUND_MAP.put(5, "note_sol.mp3");
                SOUND_MAP.put(6, "note_la.mp3");
                SOUND_MAP.put(7, "note_si.mp3");
                SOUND_MAP.put(8, "second_do.mp3");
                SOUND_MAP.put(9, "second_re.mp3");
                SOUND_MAP.put(10, "second_mi.mp3");
                SOUND_MAP.put(11, "second_fa.mp3");
                SOUND_MAP.put(12, "second_sol.mp3");
                SOUND_MAP.put(13, "second_la.mp3");
                SOUND_MAP.put(14, "second_si.mp3");
                SOUND_MAP.put(15, "do_dies.mp3");
                SOUND_MAP.put(16, "re_dies.mp3");
                SOUND_MAP.put(17, "fa_dies.mp3");
                SOUND_MAP.put(18, "sol_dies.mp3");
                SOUND_MAP.put(19, "la_dies.mp3");
                SOUND_MAP.put(20, "second_dies_do.mp3");
                SOUND_MAP.put(21, "second_dies_re.mp3");
                SOUND_MAP.put(22, "second_dies_fa.mp3");
                SOUND_MAP.put(23, "second_dies_sol.mp3");
                SOUND_MAP.put(24, "second_dies_la.mp3");
                break;

            case "Guitare":
                SOUND_MAP.put(1, "guitar_do.mp3");
                SOUND_MAP.put(2, "guitar_re.mp3");
                SOUND_MAP.put(3, "guitar_mi.mp3");
                SOUND_MAP.put(4, "guitar_fa.mp3");
                SOUND_MAP.put(5, "guitar_sol.mp3");
                SOUND_MAP.put(6, "guitar_la.mp3");
                SOUND_MAP.put(7, "guitar_si.mp3");
                SOUND_MAP.put(8, "guitar_second_do.mp3");
                SOUND_MAP.put(9, "guitar_second_re.mp3");
                SOUND_MAP.put(10, "guitar_second_mi.mp3");
                SOUND_MAP.put(11, "guitar_second_fa.mp3");
                SOUND_MAP.put(12, "guitar_second_sol.mp3");
                SOUND_MAP.put(13, "guitar_second_la.mp3");
                SOUND_MAP.put(14, "guitar_second_si.mp3");
                SOUND_MAP.put(15, "guitar_do_dies.mp3");
                SOUND_MAP.put(16, "guitar_re_dies.mp3");
                SOUND_MAP.put(17, "guitar_fa_dies.mp3");
                SOUND_MAP.put(18, "guitar_sol_dies.mp3");
                SOUND_MAP.put(19, "guitar_la_dies.mp3");
                SOUND_MAP.put(20, "guitar_second_do_dies.mp3");
                SOUND_MAP.put(21, "guitar_second_re_dies.mp3");
                SOUND_MAP.put(22, "guitar_second_fa_dies.mp3");
                SOUND_MAP.put(23, "guitar_second_sol_dies.mp3");
                SOUND_MAP.put(24, "guitar_second_la_dies.mp3");
                // Ajouter les autres sons de guitare
                break;

            case "Violons":
                SOUND_MAP.put(1, "string_do.mp3");
                SOUND_MAP.put(2, "string_re.mp3");
                SOUND_MAP.put(3, "string_mi.mp3");
                SOUND_MAP.put(4, "string_fa.mp3");
                SOUND_MAP.put(5, "string_sol.mp3");
                SOUND_MAP.put(6, "string_la.mp3");
                SOUND_MAP.put(7, "string_si.mp3");
                SOUND_MAP.put(8, "string_second_do.mp3");
                SOUND_MAP.put(9, "string_second_re.mp3");
                SOUND_MAP.put(10, "string_second_mi.mp3");
                SOUND_MAP.put(11, "string_second_fa.mp3");
                SOUND_MAP.put(12, "string_second_sol.mp3");
                SOUND_MAP.put(13, "string_second_la.mp3");
                SOUND_MAP.put(14, "string_second_si.mp3");
                SOUND_MAP.put(15, "string_do_dies.mp3");
                SOUND_MAP.put(16, "string_re_dies.mp3");
                SOUND_MAP.put(17, "string_fa_dies.mp3");
                SOUND_MAP.put(18, "string_sol_dies.mp3");
                SOUND_MAP.put(19, "string_la_dies.mp3");
                SOUND_MAP.put(20, "string_second_do_dies.mp3");
                SOUND_MAP.put(21, "string_second_re_dies.mp3");
                SOUND_MAP.put(22, "string_second_fa_dies.mp3");
                SOUND_MAP.put(23, "string_second_sol_dies.mp3");
                SOUND_MAP.put(24, "string_second_la_dies.mp3");
                // Ajouter les autres sons de violoncelle
                break;

            case "Bass":
                SOUND_MAP.put(1, "bass_do.mp3");
                SOUND_MAP.put(2, "bass_re.mp3");
                // Ajouter les autres sons de flûte
                SOUND_MAP.put(3, "bass_mi.mp3");
                SOUND_MAP.put(4, "bass_fa.mp3");
                SOUND_MAP.put(5, "bass_sol.mp3");
                SOUND_MAP.put(6, "bass_la.mp3");
                SOUND_MAP.put(7, "bass_si.mp3");
                SOUND_MAP.put(8, "bass_second_do.mp3");
                SOUND_MAP.put(9, "bass_second_re.mp3");
                SOUND_MAP.put(10, "bass_second_mi.mp3");
                SOUND_MAP.put(11, "bass_second_fa.mp3");
                SOUND_MAP.put(12, "bass_second_sol.mp3");
                SOUND_MAP.put(13, "bass_second_la.mp3");
                SOUND_MAP.put(14, "bass_second_si.mp3");
                SOUND_MAP.put(15, "bass_do_dies.mp3");
                SOUND_MAP.put(16, "bass_re_dies.mp3");
                SOUND_MAP.put(17, "bass_fa_dies.mp3");
                SOUND_MAP.put(18, "bass_sol_dies.mp3");
                SOUND_MAP.put(19, "bass_la_dies.mp3");
                SOUND_MAP.put(20, "bass_second_do_dies.mp3");
                SOUND_MAP.put(21, "bass_second_re_dies.mp3");
                SOUND_MAP.put(22, "bass_second_fa_dies.mp3");
                SOUND_MAP.put(23, "bass_second_sol_dies.mp3");
                SOUND_MAP.put(24, "bass_second_la_dies.mp3");
                break;
        }
    }

    public AudioSoundPlayer(Context context) {
        this.context = context;
    }

    // Joue une note
    public void playNote(int note) {
        // Vérifie si une instance MediaPlayer pour cette note existe déjà
        if (playerMap.get(note) == null) {
            String fileName = SOUND_MAP.get(note);
            if (fileName != null) {
                try {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    // Prépare le MediaPlayer avec le fichier dans le dossier assets
                    mediaPlayer.setDataSource(context.getAssets().openFd(fileName).getFileDescriptor(),
                            context.getAssets().openFd(fileName).getStartOffset(),
                            context.getAssets().openFd(fileName).getLength());
                    mediaPlayer.prepare();
                    mediaPlayer.start();

                    // Libère le MediaPlayer après la lecture complète
                    mediaPlayer.setOnCompletionListener(MediaPlayer::release);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }}

    // Supprime l'option d'arrêt manuel (inutile si le fichier joue toujours entièrement)
    // Fonction disponible si nécessaire mais non utilisée par défaut
    public void stopNote (int note){
        MediaPlayer mediaPlayer = playerMap.get(note);
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            playerMap.remove(note);
        }
    }
}