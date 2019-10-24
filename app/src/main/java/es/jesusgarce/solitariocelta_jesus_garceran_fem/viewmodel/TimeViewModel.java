package es.jesusgarce.solitariocelta_jesus_garceran_fem.viewmodel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

public class TimeViewModel extends ViewModel {

    private MutableLiveData<Integer> minutes;
    private MutableLiveData<Integer> seconds;

    public TimeViewModel() {
        minutes = new MutableLiveData<>();
        seconds = new MutableLiveData<>();
        restartTime();
    }

    public String getTime() {
        if (minutes.getValue() == null || seconds.getValue() == null) {
            minutes = new MutableLiveData<>();
            seconds = new MutableLiveData<>();
            restartTime();
        }

        String minutesString = String.valueOf(minutes.getValue());
        String secondsString = String.valueOf(seconds.getValue());
        if (minutes.getValue() < 10)
            minutesString = "0" + minutes.getValue();
        if (seconds.getValue() < 10)
            secondsString = "0" + seconds.getValue();

        return minutesString + ":" + secondsString;
    }

    public void restartTime() {
        seconds.setValue(0);
        minutes.setValue(0);
    }

    public MutableLiveData<Integer> getSeconds() {
        if (seconds == null) {
            seconds = new MutableLiveData<>();
            seconds.setValue(0);
        }

        return seconds;
    }

    public void setSeconds(int seconds) {
        if (this.seconds == null)
            this.seconds = new MutableLiveData<>();
        this.seconds.setValue(seconds);
    }

    public MutableLiveData<Integer> getMinutes() {
        if (minutes == null) {
            minutes = new MutableLiveData<>();
            minutes.setValue(0);
        }
        return minutes;
    }

    public void setMinutes(int minutes) {
        if (this.minutes == null)
            this.minutes = new MutableLiveData<>();
        this.minutes.setValue(minutes);
    }
}
