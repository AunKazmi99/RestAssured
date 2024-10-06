package graphQL;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GraphQLTest {

    @Test
    public void graphQlQueryTest() {

        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"query($characterId: Int!) {\\n  character(characterId: $characterId) {\\n    id\\n    name\\n    gender\\n    status\\n  }\\n  location(locationId: 650) {\\n    name\\n    dimension\\n  }\\n  episode(episodeId: 8) {\\n    name\\n    air_date\\n    episode\\n  }\\n  characters(filters: {name: \\\"Rahul\\\"}) {\\n    info {\\n      count\\n    }\\n    result {\\n      name\\n      type\\n      id\\n    }\\n  }\\n  episodes(filters: {episode: \\\"hulu\\\"}) {\\n    result {\\n      id\\n      name\\n      air_date\\n      episode\\n    }\\n  }\\n}\",\"variables\":{\"characterId\":9561}}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
    }

    @Test
    public void graphQlMutationTest() {
        String response = given().log().all().header("Content-Type", "application/json")
                .body("{\"query\":\"mutation {\\n  createLocation(location: {name: \\\"Aun\\\", type: \\\"PK\\\", dimension: \\\"1\\\"}) {\\n    id\\n  }\\n  createCharacter(character: {name: \\\"Aun\\\", type: \\\"type\\\", status: \\\"Alive\\\", \\n  species: \\\"Human\\\", gender: \\\"Male\\\", image: \\\"Image\\\", originId: 14319, \\n    locationId: 14319}) {\\n    id\\n  }\\n  \\n  createEpisode(episode: {name: \\\"Episode Name\\\", air_date: \\\"2024 October\\\", \\n  episode: \\\"Netflix\\\"}) {\\n    id\\n  }\\n  deleteLocations(locationIds: [20,50]) {\\n    locationsDeleted\\n  }\\n}\",\"variables\":null}")
                .when().post("https://rahulshettyacademy.com/gq/graphql")
                .then().extract().response().asString();

        System.out.println(response);
    }
}
