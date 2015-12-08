CREATE TABLE "genre" (
    "id" INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    "title" VARCHAR(10000)
);

INSERT INTO "genre" ("title") 
VALUES ('Comedy');

INSERT INTO "genre" ("title") 
VALUES ('Action');

INSERT INTO "genre" ("title") 
VALUES ('Sci-fi');

INSERT INTO "genre" ("title") 
VALUES ('Fantasy');

INSERT INTO "genre" ("title") 
VALUES ('Romance');

INSERT INTO "genre" ("title") 
VALUES ('Horror');