INSERT INTO "review"("title", "user", "text", "rating")
    VALUES ((SELECT "movie"."id" FROM "movie" WHERE "movie"."title" = ?), --title
            (SELECT "users"."id" FROM "users" WHERE "users"."username" = ?), --user
            ?, --text
            ?) --rating
