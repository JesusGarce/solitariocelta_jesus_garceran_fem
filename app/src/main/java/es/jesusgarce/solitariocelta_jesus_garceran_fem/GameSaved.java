package es.jesusgarce.solitariocelta_jesus_garceran_fem;

public class GameSaved {

    String name;
    String consumedTime;
    int minutes;
    int seconds;
    String tablero;

    public GameSaved() {
        this.name = "";
        this.consumedTime = "";
        this.minutes = 0;
        this.seconds = 0;
        this.tablero = "";
    }

    public GameSaved(String name, int minutes, int seconds, String tablero) {
        this.name = name;
        this.minutes = minutes;
        this.seconds = seconds;
        this.consumedTime = minutes + ":" + seconds;
        this.tablero = tablero;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConsumedTime() {
        return consumedTime;
    }

    public void setConsumedTime(String consumedTime) {
        this.consumedTime = consumedTime;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public String getTablero() {
        return tablero;
    }

    public void setTablero(String tablero) {
        this.tablero = tablero;
    }
}
