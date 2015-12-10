--1 genre
--2 minReleaseYear
--3 maxReleastYear
--4 minRating
--5,6,7 keyword
SELECT *
FROM "movie"
WHERE "movie"."genre" IN (SELECT "genre"."id" FROM "genre" WHERE "genre"."title" LIKE ?)
    AND "movie"."release_year" BETWEEN ? AND ?
    AND "movie"."rating" BETWEEN ? AND 10
    AND ("movie"."actors" LIKE ?
        OR UPPER("movie"."description") LIKE ?
        OR UPPER("movie"."title") LIKE ?)