package digitalinnovation.example.restful.enums;

import java.util.stream.Stream;

public enum Raca {
    HUMANO("humano"),
    ELFO("elfo"),
    ORC("orc"),
    ANAO("anao");

    public String getValue() {
        return value;
    }

    private String value;

    Raca(String value) {
        this.value = value;
    }

    public static Raca of(String value) {
        return Stream.of(Raca.values())
                .filter(it -> it.getValue().equals(value))
                .findFirst()
                .orElseThrow();
    }
}
