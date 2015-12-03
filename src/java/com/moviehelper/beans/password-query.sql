-- retrieves a users password
SELECT
    "users"."password"
FROM
    "ADMIN"."users"
WHERE
    "users"."username"=?
