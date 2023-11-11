package pjh5365.linuxserviceweb.log;

import lombok.Getter;

@Getter
public class NginxAccessLog implements Log {
    private final String ip;
    private final String time;
    private final String http;
    private final String status;
    private final String client;

    public NginxAccessLog(String ip, String time, String http, String status, String client) {
        this.ip = ip;
        this.time = time;
        this.http = http;
        this.status = status;
        this.client = client;
    }
}