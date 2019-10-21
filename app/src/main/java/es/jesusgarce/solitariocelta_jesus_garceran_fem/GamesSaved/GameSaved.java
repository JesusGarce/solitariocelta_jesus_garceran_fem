package es.jesusgarce.solitariocelta_jesus_garceran_fem.GamesSaved;

public class GameSaved {

    String name;
    String consumedTime;
    String userName;
    int fichasRestantes;
    int minutes;
    int seconds;
    String tablero;

    public GameSaved(){
        this.name="";
        this.consumedTime="";
        this.userName="";
        this.fichasRestantes = 0;
        this.minutes = 0;
        this.seconds = 0;
        this.tablero="";
    }

    public GameSaved(String name, int minutes, int seconds, String userName, String tablero, int fichasRestantes) {
        this.name = name;
        this.minutes = minutes;
        this.seconds = seconds;
        this.consumedTime = minutes + ":" + seconds;
        this.userName = userName;
        this.tablero = tablero;
        this.fichasRestantes = fichasRestantes;
    }

    public String getName() {
        return name;
    }

    public String getConsumedTime() {
        return consumedTime;
    }

    public String getUserName() {
        return userName;
    }

    public int getFichasRestantes() {
        return fichasRestantes;
    }

    public String getTablero() {
        return tablero;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getSeconds() {
        return seconds;
    }

    @Override
    public String toString() {
        return "GameSaved{" +
                "name='" + name + '\'' +
                ", consumedTime='" + consumedTime + '\'' +
                ", userName='" + userName + '\'' +
                ", tablero='" + tablero + '\'' +
                ", fichasRestantes = '"+ fichasRestantes +'\''+
                '}';
    }
}
