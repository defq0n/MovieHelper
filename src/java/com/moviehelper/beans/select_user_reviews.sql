SELECT * FROM "review" WHERE

"review"."user" = (SELECT "users"."id" FROM "users" WHERE "users"."username" = ?)