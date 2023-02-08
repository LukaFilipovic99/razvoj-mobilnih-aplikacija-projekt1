package filipovic.youtube_api_demo_app.dto;

import java.io.Serializable;

public class Channel implements Serializable {

    private String name;
    private String icon;

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
