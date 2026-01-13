package si.healthapp.symptoms;

public class LogEvent {

    private String logMessage;

    public LogEvent(String logMessage) {
        this.logMessage = logMessage;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }
}
