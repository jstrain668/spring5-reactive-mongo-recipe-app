package guru.springframework.controllers;

import guru.springframework.domain.Recipe;
import guru.springframework.services.RecipeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;

import java.util.Objects;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@WebFluxTest(IndexController.class)
@Import({ThymeleafAutoConfiguration.class})

public class IndexControllerTest2 {

    @MockBean
    RecipeService recipeService;

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void testWebClient() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setDescription("Fajita");
        when(recipeService.getRecipes()).thenReturn(Flux.just(recipe));

        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class)
                .consumeWith(response -> {
//                    System.out.println(response);
                    String responseBody = Objects.requireNonNull(response.getResponseBody());
                    Assert.assertTrue(responseBody.contains("Fajita"));
                });
    }
}
