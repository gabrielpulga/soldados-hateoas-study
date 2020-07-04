package digitalinnovation.example.restful.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import digitalinnovation.example.restful.controller.SoldadoController;
import digitalinnovation.example.restful.controller.response.SoldadoListResponse;
import digitalinnovation.example.restful.controller.response.SoldadoResponse;
import digitalinnovation.example.restful.entity.SoldadoEntity;
import digitalinnovation.example.restful.repository.SoldadoRepository;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class ResourceSoldado {
    private ObjectMapper objectMapper;

    public ResourceSoldado(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public SoldadoResponse criarLink(SoldadoEntity soldadoEntity) {
        SoldadoResponse soldadoResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);
        Link link = linkTo(methodOn(SoldadoController.class).buscarSoldado(soldadoEntity.getId())).withSelfRel();
        soldadoResponse.add(link);
        return soldadoResponse;
    }

    public SoldadoResponse criarLinkDetalhe(SoldadoEntity soldadoEntity) {
        SoldadoResponse soldadoResponse = objectMapper.convertValue(soldadoEntity, SoldadoResponse.class);
        if (soldadoEntity.getStatus().equals("morto")) {
            Link link = linkTo(methodOn(SoldadoController.class).deletarSoldado(soldadoEntity.getId()))
                    .withRel("remover")
                    .withTitle("Deletar soldado")
                    .withType("delete");
            soldadoResponse.add(link);
        } else if (soldadoEntity.getStatus().equals("vivo")) {
            Link link = linkTo(methodOn(SoldadoController.class).frenteCastelo(soldadoEntity.getId()))
                    .withRel("batalhar")
                    .withTitle("Ir para frente do castelo")
                    .withType("put");
            soldadoResponse.add(link);
        }
        return soldadoResponse;
    }
}
