Vällkommen till mitt micro tjänst! Min microtjänst körs lokalt mot min kollegas repo Adrian https://github.com/AdrianK2001/Filmrecensioner
Så ni kommer behöva clona ner båda våra repon.
Ni kommer behöva ändra lite grejer i hans repo för att få det att funka.
server.port=8082 på Filmrecesioner
Lägga till den här funktionen i ReviewService
  public Review addReview(Review review) {
        return reviewRepository.save(review);
    }
    Sen måste ni ändra i ReviewController så hans creasteReview länkar till den nya funktionen istället för den gamla.
    Sen för postman så funkar det såhär:
    POST localhost:8081/movies
    {
    "title":"Ny cool film",
    "genre":"Cool genre"
    }
    För att se alla filmer som finns
    GET localhost:8081/movies
    För att se en film med en review
    GET localhost:8081/movies/{id}
    För att uppdatera filmer
    PUT localhost:8081/movies
     {
    "title":"Ny cool film",
    "genre":"Cool genre"
    }
    För att ta bort filmer
    DELETE GET localhost:8081/movies/{id}
    För att posta en review
    POST localhost:8082/reviews
    {
    "movieId":{id},
    "comment":"Best movie ever",
    "rating":10
    }
    Inom id så måste du ange vilken movie id för att koppla dem. Tex om du har skappat en film med id 6 så behöver du skriva 6 i kolumnen id.
    Sedan kan du köra  GET localhost:8081/movies/{id} för att se recensionen för den filmen.
