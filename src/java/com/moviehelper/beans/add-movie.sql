
INSERT INTO "ADMIN"."movie" (title, poster_link, trailer_link, rating, actors, genre, description, release_year)
    VALUES (?, --title,
            ?, --poster_link,
            ?, --trailer_link,
            ?, --rating,
            ?, --actors,
            (SELECT "genre"."id" FROM "genre" WHERE "genre"."title"=?), --genre
            ?, --description,
            ?) --release_year)