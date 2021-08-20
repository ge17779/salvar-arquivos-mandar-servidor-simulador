package br.com.gonzales.misionhispana;

public class JustificationAttachment {

    String type;
    String body;

    public JustificationAttachment(String type, String body) {
        this.type = type;
        this.body = body;
    }

    @Override
    public String toString() {
        return "JustificationAttachment{" +
                "type='" + type + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
