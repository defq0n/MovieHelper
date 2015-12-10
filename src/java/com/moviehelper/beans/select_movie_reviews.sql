SELECT * FROM "review" WHERE

"review"."title" = (SELECT "movie"."id" FROM "movie" WHERE "movie"."title" = ?)
