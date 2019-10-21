package es.jesusgarce.solitariocelta_jesus_garceran_fem.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Score implements Parcelable {

    private int id;
    private String nombreUsuario;
    private String tiempo;
    private String fecha;
    private int fichasRestantes;

    public Score(int id, String nombreUsuario, String tiempo, String fecha, int fichasRestantes) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.tiempo = tiempo;
        this.fecha = fecha;
        this.fichasRestantes = fichasRestantes;
    }

    public Score(String nombreUsuario, String tiempo, String fecha, int fichasRestantes) {
        this.nombreUsuario = nombreUsuario;
        this.tiempo = tiempo;
        this.fecha = fecha;
        this.fichasRestantes = fichasRestantes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getTiempo() {
        return tiempo;
    }

    public void setTiempo(String tiempo) {
        this.tiempo = tiempo;
    }

    public int getFichasRestantes() {
        return fichasRestantes;
    }

    public void setFichasRestantes(int fichasRestantes) {
        this.fichasRestantes = fichasRestantes;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", tiempo='" + tiempo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", fichasRestantes=" + fichasRestantes +
                '}';
    }


    protected Score(Parcel in) {
        id = in.readInt();
        nombreUsuario = in.readString();
        tiempo = in.readString();
        fecha = in.readString();
        fichasRestantes = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(nombreUsuario);
        dest.writeString(tiempo);
        dest.writeString(fecha);
        dest.writeInt(fichasRestantes);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Score> CREATOR = new Parcelable.Creator<Score>() {
        @Override
        public Score createFromParcel(Parcel in) {
            return new Score(in);
        }

        @Override
        public Score[] newArray(int size) {
            return new Score[size];
        }
    };
}