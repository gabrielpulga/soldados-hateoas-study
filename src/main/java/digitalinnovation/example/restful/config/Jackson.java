package digitalinnovation.example.restful.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.PackageVersion;
import digitalinnovation.example.restful.enums.Raca;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class Jackson {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        //Falha em propriedades não mapeadas
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //Falha se alguma propriedade estiver vazia
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //Para compatibilidade de arrays com um único valor
        objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        //Serialize datas (importante para não dar erro com calendários)
        objectMapper.registerModule(new JavaTimeModule());
        //objectMapper.registerModule(racaModuleMapper());
        return objectMapper;
    }

    public SimpleModule racaModuleMapper() {
        SimpleModule dataModule = new SimpleModule("JSONRacaModule", PackageVersion.VERSION);
        dataModule.addSerializer(Raca.class, new RacaSerialize());
        dataModule.addDeserializer(Raca.class, new RacaDeserialize());
        return dataModule;
    }

    private class RacaSerialize extends StdSerializer<Raca> {
        public RacaSerialize() {
            super(Raca.class);
        }

        @Override
        public void serialize(Raca raca, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(raca.getValue());
        }
    }

    private class RacaDeserialize extends StdDeserializer<Raca> {
        public RacaDeserialize() {
            super(Raca.class);
        }

        @Override
        public Raca deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            return Raca.of(jsonParser.getText());
        }
    }
}