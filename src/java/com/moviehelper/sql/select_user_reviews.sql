SELECT * FROM "review"
WHERE "user" = (SELECT "users"."id" FROM "users" WHERE "users"."username" = ?)
