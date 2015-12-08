CREATE TABLE "review" (
    "id" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title" INTEGER NOT NULL REFERENCES "movie" ("id"),
    "user" INTEGER NOT NULL REFERENCES "users" ("id"),
    "text" VARCHAR(10000),
    "rating" DOUBLE
);
