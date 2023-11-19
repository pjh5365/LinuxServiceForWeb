package pjh5365.linuxserviceweb.domain.log;

import lombok.Getter;

@Getter
public class NginxAccessLog implements Log {
    private String ip = "";
    private String time = "";
    private String http = "";
    private String status = "";
    private String client = "";

    public NginxAccessLog() {
    }

    public NginxAccessLog(String ip, String time, String http, String status, String client) {
        this.ip = ip;
        this.time = time;
        this.http = http;
        this.status = status;
        this.client = client;
    }
}
