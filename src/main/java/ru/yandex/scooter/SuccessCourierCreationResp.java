package ru.yandex.scooter;

public class SuccessCourierCreationResp {

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    private boolean ok;

    public SuccessCourierCreationResp(boolean ok) {
        this.ok = ok;
    }

    public SuccessCourierCreationResp() {
    }
}
