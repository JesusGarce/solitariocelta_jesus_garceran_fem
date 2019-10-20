package es.jesusgarce.solitariocelta_jesus_garceran_fem;

public class GameSaved {

    String name;
    String consumedTime;
    String userName;
    int fichasRestantes;
    String tablero;

    public GameSaved(String name, String consumedTime, String userName, String tablero, int fichasRestantes) {
        this.name = name;
        this.consumedTime = consumedTime;
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
