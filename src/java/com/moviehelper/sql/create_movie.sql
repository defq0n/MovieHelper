CREATE TABLE "movie" (
    "id" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title" VARCHAR(10000),
    "description" LONG VARCHAR,
    "poster_link" VARCHAR(10000),
    "trailer_link" VARCHAR(10000),
    "release_year" INTEGER,
    "rating" DOUBLE,
    "actors" VARCHAR(10000),
    "genre" INTEGER NOT NULL REFERENCES "genre" ("id")
);
