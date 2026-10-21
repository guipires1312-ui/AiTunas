public final class Time {

    private final int totalSeconds;

    public Time(int minutes, int seconds) {
        this.totalSeconds = minutes * 60 + seconds;
    }

    public Time(String time) {
        String[] parts = time.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);

        this.totalSeconds = minutes * 60 + seconds;

    }

    public int getMinutes() {
        return totalSeconds / 60;
    }

    public int getSeconds() {
        return totalSeconds % 60;
    }

    public int totalSeconds() {
        return totalSeconds;
    }

    public Time add(Time novo) {
        int totalSeconds = this.totalSeconds() + novo.totalSeconds();
        return new Time(totalSeconds / 60, totalSeconds % 60);
    }

    public Time sub(Time novo) {
        int totalSeconds = this.totalSeconds() - novo.totalSeconds();
        if (totalSeconds > 0) {
            return new Time(totalSeconds / 60, totalSeconds % 60);
        }
        return new Time(0, 0);
    }

    public boolean isSmaller(Time time) {
        return time.totalSeconds() < this.totalSeconds();

    }

    public boolean isBigger(Time time) {
        return time.totalSeconds() > this.totalSeconds();
    }

    public String toString() {
        return String.format("%02d:%02d", this.getMinutes(), this.getSeconds());
    }
}