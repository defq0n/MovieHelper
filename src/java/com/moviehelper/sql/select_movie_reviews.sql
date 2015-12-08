SELECT * FROM "review"
WHERE "title" = (SELECT "movie"."id" FROM "movie" WHERE "movie"."title" = ?)
